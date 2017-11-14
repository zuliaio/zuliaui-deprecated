package io.zulia.ui.server;

import com.cedarsoftware.util.io.JsonWriter;
import com.google.common.base.Splitter;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.util.JSONSerializers;
import io.zulia.client.command.GetFields;
import io.zulia.client.command.GetIndexConfig;
import io.zulia.client.command.Query;
import io.zulia.client.config.ZuliaPoolConfig;
import io.zulia.client.pool.ZuliaWorkPool;
import io.zulia.client.result.GetIndexesResult;
import io.zulia.client.result.QueryResult;
import io.zulia.message.ZuliaQuery;
import io.zulia.ui.ConfigLoader;
import io.zulia.ui.client.services.UIQueryService;
import io.zulia.ui.shared.IndexInfo;
import io.zulia.ui.shared.InstanceInfo;
import io.zulia.ui.shared.UIQueryObject;
import io.zulia.ui.shared.UIQueryResults;
import io.zulia.util.ResultHelper;
import io.zulia.util.ZuliaUtil;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static io.zulia.message.ZuliaBase.Similarity;
import static io.zulia.message.ZuliaIndex.FacetAs;
import static io.zulia.message.ZuliaIndex.FieldConfig;
import static io.zulia.message.ZuliaIndex.IndexAs;
import static io.zulia.message.ZuliaQuery.FacetCount;
import static io.zulia.message.ZuliaQuery.FetchType;
import static io.zulia.message.ZuliaQuery.FieldSort;
import static io.zulia.message.ZuliaQuery.ScoredResult;

/**
 * Created by Payam Meyer on 3/9/17.
 * @author pmeyer
 */
// We're not using web.xml
@SuppressWarnings("GwtServiceNotRegistered")
public class UIQueryServiceImpl extends RemoteServiceServlet implements UIQueryService {

	private static final Logger LOG = LoggerFactory.getLogger(UIQueryServiceImpl.class);
	private static final int MB = 1024 * 1024;
	private static final String QUERY_HISTORY = "queryHistory";
	private ZuliaWorkPool zuliaWorkPool;
	private Datastore datastore;
	private Map<String, String> config = ConfigLoader.getConfig();

	@Override
	public void init() throws ServletException {
		super.init();

		try {
			ZuliaPoolConfig zuliaPoolConfig;
			String zuliaHost = config.get("zuliaHost");
			if (zuliaHost.contains(";")) {
				zuliaPoolConfig = new ZuliaPoolConfig().setNodeUpdateEnabled(true).setDefaultRetries(2);
				for (String node : Splitter.on(";").trimResults().splitToList(zuliaHost)) {
					zuliaPoolConfig.addNode(node);
				}
				zuliaWorkPool = new ZuliaWorkPool(zuliaPoolConfig);
				zuliaWorkPool.updateNodes();
			}
			else {
				zuliaPoolConfig = new ZuliaPoolConfig().addNode(zuliaHost).setNodeUpdateEnabled(false).setDefaultRetries(2);
				zuliaWorkPool = new ZuliaWorkPool(zuliaPoolConfig);
			}

			MongoClientOptions mongoClientOptions = MongoClientOptions.builder().connectionsPerHost(32).build();
			MongoClient mongoClient = new MongoClient(config.get("mongoHost"), mongoClientOptions);
			Morphia morphia = new Morphia();
			morphia.map(UIQueryObject.class);
			datastore = morphia.createDatastore(mongoClient, QUERY_HISTORY);
			datastore.ensureIndexes();
		}
		catch (Exception e) {
			LOG.error("Failed to initiate Zulia work pool.", e);
		}
	}

	@Override
	public InstanceInfo getInstanceInfo() throws Exception {
		try {
			InstanceInfo instanceInfo = new InstanceInfo();
			instanceInfo.setZuliaVersion(config.get("zuliaVersion"));
			instanceInfo.setLuceneVersion(config.get("luceneVersion"));

			List<IndexInfo> indexInfoList = getIndexInfos();

			instanceInfo.setIndexes(indexInfoList);

			Runtime runtime = Runtime.getRuntime();

			// TODO: These need to be Zulia's not this app, who cares about this app?
			instanceInfo.setJvmUsedMemory((runtime.totalMemory() - runtime.freeMemory()) / MB);
			instanceInfo.setJvmFreeMemory(runtime.freeMemory() / MB);
			instanceInfo.setJvmTotalMemoryMB(runtime.totalMemory() / MB);
			instanceInfo.setJvmMaxMemoryMB(runtime.maxMemory() / MB);

			return instanceInfo;
		}
		catch (Exception e) {
			LOG.error("Failed to get the instance info.", e);
			throw e;
		}
	}

	private List<IndexInfo> getIndexInfos() throws Exception {
		GetIndexesResult indexes = zuliaWorkPool.getIndexes();

		List<IndexInfo> indexInfoList = new ArrayList<>();
		for (String indexName : indexes.getIndexNames()) {
			IndexInfo indexInfo = new IndexInfo();
			indexInfo.setName(indexName);
			indexInfo.setSize(20L);
			indexInfo.setTotalDocs((int) zuliaWorkPool.getNumberOfDocs(indexName).getNumberOfDocs());

			TreeMap<String, FieldConfig> fieldConfigMap = zuliaWorkPool.getIndexConfig(new GetIndexConfig(indexName)).getIndexConfig().getFieldConfigMap();
			for (String fieldName : fieldConfigMap.keySet()) {
				indexInfo.getFlList().add(fieldName);
				FieldConfig fieldConfig = fieldConfigMap.get(fieldName);

				for (IndexAs indexAs : fieldConfig.getIndexAsList()) {
					indexInfo.getQfList().add(indexAs.getIndexFieldName());
				}

				for (FacetAs facetAs : fieldConfig.getFacetAsList()) {
					indexInfo.getFacetList().add(facetAs.getFacetName());
				}

			}

			indexInfoList.add(indexInfo);
		}
		return indexInfoList;
	}

	@Override
	public UIQueryResults search(String queryId) throws Exception {

		try {
			UIQueryResults results = new UIQueryResults();

			UIQueryObject uiQueryObject = datastore.createQuery(UIQueryObject.class).field("_id").equal(new ObjectId(queryId)).get();
			results.setUiQueryObject(uiQueryObject);
			results.setIndexes(getIndexInfos());

			Query query = new Query(uiQueryObject.getIndexNames(), uiQueryObject.getQuery(), uiQueryObject.getRows());
			if (query.getQuery() == null || query.getQuery().isEmpty()) {
				query.setQuery("*:*");
			}

			query.setDebug(uiQueryObject.isDebug());

			if (uiQueryObject.getStart() != 0) {
				query.setStart(uiQueryObject.getStart());
			}

			if (uiQueryObject.isDontCache()) {
				query.setDontCache(uiQueryObject.isDontCache());
			}

			if (uiQueryObject.getMm() != null) {
				query.setMinimumNumberShouldMatch(uiQueryObject.getMm());
			}

			if (uiQueryObject.isDismax() != null) {
				query.setDismax(uiQueryObject.isDismax());
				if (uiQueryObject.getDismaxTie() != null) {
					query.setDismaxTie(uiQueryObject.getDismaxTie());
				}
			}

			if (!uiQueryObject.getQueryFields().isEmpty()) {
				uiQueryObject.getQueryFields().forEach(query::addQueryField);
			}

			if (uiQueryObject.getDefaultOperator() != null) {
				String defaultOperator = uiQueryObject.getDefaultOperator();
				if (defaultOperator.equalsIgnoreCase("AND")) {
					query.setDefaultOperator(ZuliaQuery.Query.Operator.AND);
				}
				else if (defaultOperator.equalsIgnoreCase("OR")) {
					query.setDefaultOperator(ZuliaQuery.Query.Operator.OR);
				}
			}

			if (uiQueryObject.getSimilarities() != null) {
				for (String field : uiQueryObject.getSimilarities().keySet()) {
					String simType = uiQueryObject.getSimilarities().get(field);

					if (simType.equalsIgnoreCase("bm25")) {
						query.addFieldSimilarity(field, Similarity.BM25);
					}
					else if (simType.equalsIgnoreCase("constant")) {
						query.addFieldSimilarity(field, Similarity.CONSTANT);
					}
					else if (simType.equalsIgnoreCase("tf")) {
						query.addFieldSimilarity(field, Similarity.TF);
					}
					else if (simType.equalsIgnoreCase("tfidf")) {
						query.addFieldSimilarity(field, Similarity.TFIDF);
					}

				}
			}

			if (uiQueryObject.getFilterQueries() != null) {
				for (String filterQuery : uiQueryObject.getFilterQueries()) {
					query.addFilterQuery(filterQuery);
				}
			}

			// TODO: This needs to pass in a proper object
			if (uiQueryObject.getCosineSimJsonList() != null) {

			}

			// TODO: ditto
			if (uiQueryObject.getFilterJsonQueries() != null) {

			}

			// TODO: This needs to pass in an object with other parameters and not just the field name.
			if (uiQueryObject.getHighlightList() != null) {
				for (String field : uiQueryObject.getHighlightList()) {
					query.addHighlight(field);
				}
			}

			// TODO: ditto to the ditto
			if (uiQueryObject.getHighlightJsonList() != null) {

			}

			// TODO: ditto^3
			if (uiQueryObject.getAnalyzeJsonList() != null) {

			}

			if (uiQueryObject.getDisplayFields() != null) {
				for (String field : uiQueryObject.getDisplayFields()) {
					if (field.startsWith("-")) {
						query.addDocumentMaskedField(field.substring(1, field.length()));
					}
					else {
						query.addDocumentField(field);
					}
				}
			}

			query.setResultFetchType(FetchType.FULL);

			// TODO: do drill down?
			if (uiQueryObject.getDrillDowns() != null) {

			}

			for (String sortField : uiQueryObject.getSortList().keySet()) {
				String sortDir = uiQueryObject.getSortList().get(sortField);

				if ("-1".equals(sortDir) || "DESC".equalsIgnoreCase(sortDir)) {
					query.addFieldSort(sortField, FieldSort.Direction.DESCENDING);
				}
				else if ("1".equals(sortDir) || "ASC".equalsIgnoreCase(sortDir)) {
					query.addFieldSort(sortField, FieldSort.Direction.ASCENDING);
				}
			}

			for (String facetField : uiQueryObject.getFacets()) {
				query.addCountRequest(facetField, 100);
			}

			LOG.info("Query: " + query);
			QueryResult queryResult = zuliaWorkPool.query(query);

			results.setTotalResults(queryResult.getTotalHits());

			for (ScoredResult scoredResult : queryResult.getResults()) {
				Document document = ResultHelper.getDocumentFromScoredResult(scoredResult);
				if (document != null) {
					// always add index name and ID
					document.put("indexName", scoredResult.getIndexName());
					document.put("id", scoredResult.getUniqueId());
					String jsonDoc = JSONSerializers.getLegacy().serialize(document);
					results.addFormattedDocument(JsonWriter.formatJson(jsonDoc));
				}
			}

			for (String facet : uiQueryObject.getFacets()) {
				if (queryResult.getFacetCounts(facet) != null) {
					for (FacetCount fc : queryResult.getFacetCounts(facet)) {
						results.addFacetCount(facet, fc.getFacet(), fc.getCount());
					}
				}
			}

			return results;
		}
		catch (Exception e) {
			LOG.error("Failed to execute query.", e);
			throw e;
		}
	}

	@Override
	public String saveQuery(UIQueryObject uiQueryObject) throws Exception {
		try {
			return datastore.save(uiQueryObject).getId().toString();
		}
		catch (Exception e) {
			LOG.error("Failed to save the query.");
			throw e;
		}
	}

	@Override
	public List<String> suggestFieldNames(String indexName, String query) throws Exception {

		try {
			List<String> fieldNames = zuliaWorkPool.getFields(new GetFields(indexName)).getFieldNames();

			query = query.toLowerCase();

			TreeMap<String, Integer> suggestToDistance = new TreeMap<>();

			for (String fieldName : fieldNames) {
				if (fieldName.contains(query)) {
					int distance = ZuliaUtil.computeLevenshteinDistance(query, fieldName);
					suggestToDistance.put(fieldName, distance);
				}
			}

			ArrayList<Map.Entry<String, Integer>> entries = new ArrayList<>(suggestToDistance.entrySet());
			entries.sort(Map.Entry.comparingByValue());

			List<String> sortedSuggest = new ArrayList<>();
			for (Map.Entry<String, Integer> e : entries) {
				sortedSuggest.add(e.getKey());
			}

			return sortedSuggest;

		}
		catch (Exception e) {
			LOG.error("Failed to get field name suggestions.", e);
			throw e;
		}

	}

}
