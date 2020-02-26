package io.zulia.ui.client.services;

import com.intendia.gwt.autorest.client.ResourceVisitor;
import elemental2.dom.DomGlobal;

/**
 * Created by Payam Meyer on 5/13/15.
 * @author pmeyer
 */
public class ServiceProvider {

	private static ServiceProvider serviceProvider = new ServiceProvider();

	public static ServiceProvider get() {
		return serviceProvider;
	}

	private ZuliaServiceClient service;

	private static ResourceVisitor osm() {
		String baseUrl = DomGlobal.window.location.getProtocol() + "//" + DomGlobal.window.location.getHost();
		return new CustomAutoREST().path(baseUrl);
	}

	private ServiceProvider() {
		service = new ZuliaServiceClient_RestServiceModel(ServiceProvider::osm);
	}

	public ZuliaServiceClient getService() {
		return service;
	}

}
