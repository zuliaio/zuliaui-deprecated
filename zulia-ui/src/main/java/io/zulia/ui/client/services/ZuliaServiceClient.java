package io.zulia.ui.client.services;

import com.intendia.gwt.autorest.client.AutoRestGwt;
import io.reactivex.Single;
import io.zulia.ui.client.dto.IndexesDTO;
import io.zulia.ui.client.dto.ResultsDTO;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@AutoRestGwt
@Path("")
public interface ZuliaServiceClient {

	@GET
	@Path("query")
	Single<ResultsDTO> search(@QueryParam(ZuliaConstants.INDEX) String index, @QueryParam(ZuliaConstants.QUERY) String query,
			@QueryParam(ZuliaConstants.ROWS) int rows);

	@GET
	@Path("indexes")
	Single<IndexesDTO> getIndexes();

}
