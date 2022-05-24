package io.zulia.ui.client.services;

import elemental2.core.JsArray;
import io.zulia.ui.client.dto.IndexDTO;
import io.zulia.ui.client.dto.IndexesDTO;
import io.zulia.ui.client.dto.MembersDTO;
import io.zulia.ui.client.dto.ResultsDTO;
import io.zulia.ui.client.dto.StatsDTO;
import us.ascendtech.gwt.simplerest.client.ErrorCallback;
import us.ascendtech.gwt.simplerest.client.SimpleRestGwt;
import us.ascendtech.gwt.simplerest.client.SingleCallback;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@SimpleRestGwt
@Path("/")
public interface ZuliaServiceClient {

	@GET
	@Path("query")
	void search(@QueryParam(ZuliaConstants.INDEX) JsArray<String> indexes, @QueryParam(ZuliaConstants.QUERY) String query,
			@QueryParam(ZuliaConstants.ROWS) int rows, @QueryParam(ZuliaConstants.SORT) JsArray<String> sortFields,
			@QueryParam(ZuliaConstants.QUERY_FIELD) JsArray<String> queryFields, @QueryParam(ZuliaConstants.FIELDS) JsArray<String> documentFields,
			@QueryParam(ZuliaConstants.TRUNCATE) boolean truncate, SingleCallback<ResultsDTO> callback, ErrorCallback errorCallback);

	@GET
	@Path("indexes")
	void getIndexes(SingleCallback<IndexesDTO> callback, ErrorCallback errorCallback);

	@GET
	@Path("index")
	void getIndexSettings(@QueryParam(ZuliaConstants.INDEX) String index, SingleCallback<IndexDTO> callback, ErrorCallback errorCallback);

	@GET
	@Path("stats")
	void getStats(SingleCallback<StatsDTO> callback, ErrorCallback errorCallback);

	@GET
	@Path("nodes")
	void getMembers(SingleCallback<MembersDTO> callback, ErrorCallback errorCallback);
}
