package io.zulia.ui.client.services;

import elemental2.dom.DomGlobal;

/**
 * Created by Payam Meyer on 5/13/15.
 * @author pmeyer
 */
public class ServiceProvider {

	private final static ServiceProvider serviceProvider = new ServiceProvider();

	public static ServiceProvider get() {
		return serviceProvider;
	}

	private ZuliaServiceClient service;

	private ServiceProvider() {
		String baseUrl = DomGlobal.window.location.protocol + "//" + DomGlobal.window.location.host;
		service = new ZuliaServiceClientSimpleRest(baseUrl);
	}

	public ZuliaServiceClient getService() {
		return service;
	}

}
