package io.zulia.ui.client.services;

import com.google.gwt.core.shared.GWT;

/**
 * Created by Payam Meyer on 5/13/15.
 * @author pmeyer
 */
public class ServiceProvider {

	private static ServiceProvider serviceProvider = new ServiceProvider();

	public static ServiceProvider get() {
		return serviceProvider;
	}

	private UIQueryServiceAsync uiQueryServiceAsync;

	private ServiceProvider() {
		uiQueryServiceAsync = GWT.create(UIQueryService.class);
	}

	public UIQueryServiceAsync getZuliaService() {
		return uiQueryServiceAsync;
	}

}
