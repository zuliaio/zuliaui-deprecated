package io.zulia.ui.client.controllers;

import io.zulia.ui.client.widgets.HomeView;
import io.zulia.ui.client.widgets.query.QueryView;

/**
 * Created by Payam Meyer on 6/27/16.
 * @author pmeyer
 */
public class WidgetController {

	private HomeView homeView;
	private QueryView queryView;

	public WidgetController() {
		homeView = new HomeView();
		queryView = new QueryView();
	}

	public HomeView getHomeView() {
		return homeView;
	}

	public QueryView getQueryView() {
		return queryView;
	}

}
