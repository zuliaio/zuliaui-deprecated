package io.zulia.ui.client.widgets.base;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Payam Meyer on 11/24/15.
 * @author pmeyer
 */
public class AutoSuggestAsyncCallback implements AsyncCallback<List<String>> {

	private SuggestOracle.Request request;
	private SuggestOracle.Callback callback;

	public AutoSuggestAsyncCallback(final SuggestOracle.Request request, final SuggestOracle.Callback callback) {
		this.request = request;
		this.callback = callback;
	}

	@Override
	public void onSuccess(List<String> result) {
		SuggestOracle.Response response = new SuggestOracle.Response();
		List<SuggestOracle.Suggestion> suggestions = new ArrayList<SuggestOracle.Suggestion>();
		for (String suggestedValue : result) {
			suggestions.add(new MultiWordSuggestOracle.MultiWordSuggestion(suggestedValue, suggestedValue));
		}
		response.setSuggestions(suggestions);
		if (checkQueryCurrent(request.getQuery())) {
			callback.onSuggestionsReady(request, response);
		}
	}

	public boolean checkQueryCurrent(String query) {
		return true;
	}

	@Override
	public void onFailure(Throwable caught) {
		GWT.log("Failed to get suggestions for <" + request.getQuery() + ">: " + caught);
	}

}
