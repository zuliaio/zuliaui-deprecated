package io.zulia.ui.client.services;

import com.intendia.gwt.autorest.client.AutoRestGwt;
import elemental2.core.JsArray;
import io.reactivex.Single;
import io.zulia.ui.client.dto.IndexDTO;
import io.zulia.ui.client.dto.IndexesDTO;
import io.zulia.ui.client.dto.MembersDTO;
import io.zulia.ui.client.dto.ResultsDTO;
import io.zulia.ui.client.dto.StatsDTO;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@AutoRestGwt
@Path("")
public interface ZuliaServiceClient {

	@GET
	@Path("query")
	Single<ResultsDTO> search(@QueryParam(ZuliaConstants.INDEX) JsArray<String> indexes, @QueryParam(ZuliaConstants.QUERY) String query,
			@QueryParam(ZuliaConstants.ROWS) int rows, @QueryParam(ZuliaConstants.SORT) JsArray<String> sortFields,
			@QueryParam(ZuliaConstants.QUERY_FIELD) JsArray<String> queryFields, @QueryParam(ZuliaConstants.FIELDS) JsArray<String> documentFields,
			@QueryParam(ZuliaConstants.TRUNCATE) boolean truncate);

	@GET
	@Path("indexes")
	Single<IndexesDTO> getIndexes();

	@GET
	@Path("index")
	Single<IndexDTO> getIndexSettings(@QueryParam(ZuliaConstants.INDEX) String index);

	@GET
	@Path("stats")
	Single<StatsDTO> getStats();

	@GET
	@Path("nodes")
	Single<MembersDTO> getMembers();
}
