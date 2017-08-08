package io.zulia.ui.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;
import io.zulia.ui.shared.InstanceInfo;
import io.zulia.ui.shared.UIQueryObject;
import io.zulia.ui.shared.UIQueryResults;

public interface UIQueryServiceAsync {

	void getInstanceInfo(AsyncCallback<InstanceInfo> asyncCallback);

	void search(String queryId, AsyncCallback<UIQueryResults> asyncCallback);

	void saveQuery(UIQueryObject uiQueryObject, AsyncCallback<String> asyncCallback);
}