package io.zulia.ui.client.widgets.base;

import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import io.zulia.ui.client.services.ServiceProvider;

/**
 * Created by Payam Meyer on 6/20/16.
 * @author pmeyer
 */
public class FieldNameSuggest extends MultiWordSuggestOracle {

	private String lastQuery;
	private String indexName;

	public FieldNameSuggest(String indexName) {
		this.indexName = indexName;
	}

	@Override
	public void requestSuggestions(Request request, Callback callback) {
		lastQuery = request.getQuery();

		ServiceProvider.get().getZuliaService().suggestFieldNames(indexName, request.getQuery(), getAsyncCallback(request, callback));
	}

	private AutoSuggestAsyncCallback getAsyncCallback(final Request request, final Callback callback) {
		return new AutoSuggestAsyncCallback(request, callback) {
			@Override
			public boolean checkQueryCurrent(String query) {
				return lastQuery.equals(query);
			}
		};
	}
}
