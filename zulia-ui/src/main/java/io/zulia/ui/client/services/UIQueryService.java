package io.zulia.ui.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import io.zulia.ui.shared.InstanceInfo;
import io.zulia.ui.shared.UIQueryObject;
import io.zulia.ui.shared.UIQueryResults;

@RemoteServiceRelativePath("uiqueryservice")
public interface UIQueryService extends RemoteService {

	InstanceInfo getInstanceInfo() throws Exception;

	UIQueryResults search(String queryId) throws Exception;

	String saveQuery(UIQueryObject uiQueryObject) throws Exception;
}