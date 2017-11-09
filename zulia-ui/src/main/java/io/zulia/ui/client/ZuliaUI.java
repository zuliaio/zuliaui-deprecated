package io.zulia.ui.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.html.Main;
import io.zulia.ui.client.bundle.MainResources;
import io.zulia.ui.client.charting.HighChartsInjector;
import io.zulia.ui.client.highlighter.HighlighterInjector;
import io.zulia.ui.client.places.PlaceHandler;
import io.zulia.ui.client.widgets.base.Footer;
import io.zulia.ui.client.widgets.base.Header;

/**
 * Created by Payam Meyer on 4/10/16.
 * @author pmeyer
 */
public class ZuliaUI implements ContentPresenter, EntryPoint {

	private SimplePanel simplePanel;
	private Footer footer;
	private Header header;

	@Override
	public void onModuleLoad() {

		MainResources.INSTANCE.mainGSS().ensureInjected();
		HighlighterInjector.inject();
		HighChartsInjector highChartsInjector = new HighChartsInjector() {
			@Override
			public void onload() {

				MaterialPanel mainWrapper = new MaterialPanel();

				header = new Header();

				Main main = new Main();
				simplePanel = new SimplePanel();
				main.add(simplePanel);

				footer = new Footer();

				mainWrapper.add(header);
				mainWrapper.add(main);
				mainWrapper.add(footer);

				PlaceHandler placeHandler = new PlaceHandler(ZuliaUI.this);
				placeHandler.init();

				RootPanel.get().insert(mainWrapper, 0);
			}
		};
		highChartsInjector.inject();

	}

	@Override
	public Main createBaseView() {

		Main main = new Main();
		main.setId("main-wrapper");

		simplePanel = new SimplePanel();

		main.add(simplePanel);

		return main;
	}

	@Override
	public void setContent(Widget content) {
		simplePanel.setWidget(content);
	}

	public Header getHeader() {
		return header;
	}
}
