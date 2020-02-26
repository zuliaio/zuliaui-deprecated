package io.zulia.ui.client;

import com.axellience.vuegwt.core.client.Vue;
import com.axellience.vuegwt.core.client.VueGWT;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import io.zulia.ui.client.views.AppComponentFactory;

public class ZuliaUI implements EntryPoint {

	private static EventBus eventBus;

	public void onModuleLoad() {
		VueGWT.initWithoutVueLib();
		eventBus = new SimpleEventBus();
		Vue.attach("#app", AppComponentFactory.get());
	}

	public static EventBus getEventBus() {
		return eventBus;
	}
}
