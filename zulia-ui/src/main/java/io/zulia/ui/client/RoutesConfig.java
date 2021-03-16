package io.zulia.ui.client;

import com.axellience.vuegwt.core.client.component.options.CustomizeOptions;
import com.axellience.vuegwt.core.client.component.options.VueComponentOptions;
import com.axellience.vueroutergwt.client.Route;
import com.axellience.vueroutergwt.client.RouteConfig;
import com.axellience.vueroutergwt.client.RouterOptions;
import com.axellience.vueroutergwt.client.VueRouter;
import io.zulia.ui.client.state.PageState;
import io.zulia.ui.client.views.home.HomeComponentFactory;
import io.zulia.ui.client.views.indexes.QueryComponentFactory;
import io.zulia.ui.client.views.indexes.SettingsComponentFactory;
import io.zulia.ui.client.views.indexes.visualization.VisualizationComponentFactory;
import io.zulia.ui.client.views.logs.LogsComponentFactory;
import jsinterop.annotations.JsFunction;
import jsinterop.base.JsPropertyMap;

public class RoutesConfig implements CustomizeOptions {

	@JsFunction
	public interface Props {
		JsPropertyMap<String> prop(Route route);
	}

	@Override
	public void customizeOptions(VueComponentOptions componentOptions) {
		RouterOptions routerOptions = new RouterOptions();

		routerOptions.addRoute(new RouteConfig().setPath("/").setName("").setComponent(HomeComponentFactory.get().getJsConstructor()));
		routerOptions.addRoute(new RouteConfig().setPath("/query").setName("query").setComponent(QueryComponentFactory.get().getJsConstructor()));
		routerOptions.addRoute(
				new RouteConfig().setPath("/visualization").setName("visualization").setComponent(VisualizationComponentFactory.get().getJsConstructor()));
		routerOptions.addRoute(new RouteConfig().setPath("/settings").setName("settings").setComponent(SettingsComponentFactory.get().getJsConstructor()));
		routerOptions.addRoute(new RouteConfig().setPath("/logs").setName("logs").setComponent(LogsComponentFactory.get().getJsConstructor()));

		VueRouter vueRouter = new VueRouter(routerOptions);

		PageState.setVueRouter(vueRouter);

		componentOptions.set("router", vueRouter);
	}
}
