package io.zulia.ui.client.widgets.query;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ScrollPanel;
import gwt.material.design.addins.client.splitpanel.MaterialSplitPanel;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.TabType;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.html.Code;
import gwt.material.design.client.ui.html.Div;
import gwt.material.design.client.ui.html.Paragraph;
import gwt.material.design.client.ui.html.Pre;
import gwt.material.design.client.ui.html.Span;
import io.zulia.ui.client.bundle.MainResources;
import io.zulia.ui.client.highlighter.Highlight;
import io.zulia.ui.client.widgets.base.CustomTabPanel;
import io.zulia.ui.shared.UIQueryResults;

/**
 * Created by Payam Meyer on 3/21/17.
 * @author pmeyer
 */
public class QueryView extends Div implements ResizeHandler {

	private final MaterialPanel leftPanel;
	private final MaterialPanel rightPanel;
	private final QueryOptionsView queryOptionsView;
	private ScrollPanel leftScrollPanel;
	private ScrollPanel rightScrollPanel;
	private final MaterialSplitPanel splitPanel;

	public QueryView() {
		setPaddingTop(63);

		splitPanel = new MaterialSplitPanel();
		splitPanel.setHeight(Window.getClientHeight() - 130 + "px");
		splitPanel.setBarPosition(25);
		leftPanel = new MaterialPanel();
		leftPanel.setBackgroundColor(Color.WHITE);
		leftPanel.setGrid("s6 l3");
		leftScrollPanel = new ScrollPanel();
		leftScrollPanel.setHeight(Window.getClientHeight() - 130 + "px");
		queryOptionsView = new QueryOptionsView();
		leftScrollPanel.setWidget(queryOptionsView);

		rightPanel = new MaterialPanel();
		rightPanel.setBackgroundColor(Color.GREY_LIGHTEN_2);
		rightPanel.setGrid("s6 l9");
		rightScrollPanel = new ScrollPanel();
		rightScrollPanel.setHeight(Window.getClientHeight() - 130 + "px");

		splitPanel.add(leftPanel);
		splitPanel.add(rightPanel);

		add(splitPanel);
	}

	public void draw(UIQueryResults uiQueryResults) {
		leftPanel.clear();
		rightPanel.clear();

		leftPanel.add(leftScrollPanel);

		queryOptionsView.drawOptions(uiQueryResults);

		if (!uiQueryResults.getJsonDocs().isEmpty()) {
			if (!uiQueryResults.getFacetCountsMap().isEmpty()) {
				Pre recordsDiv = new Pre();
				recordsDiv.addStyleName(MainResources.GSS.selectable());

				for (String jsonDoc : uiQueryResults.getJsonDocs()) {
					Code code = new Code(jsonDoc);
					code.addStyleName(MainResources.GSS.borderBottom());
					Highlight.highlightBlock(code.getElement());
					recordsDiv.add(code);
				}

				CustomTabPanel tabPanel = new CustomTabPanel(TabType.DEFAULT);
				tabPanel.createAndAddTabListItem("Records", "#records");
				tabPanel.createAndAddTabPane("records", recordsDiv);

				tabPanel.createAndAddTabListItem("Facets", "#facets");
				tabPanel.createAndAddTabPane("facets", new Span(uiQueryResults.getFacetCountsMap().toString()));

				rightScrollPanel.setWidget(tabPanel);
				rightPanel.add(rightScrollPanel);
			}
			else {
				Pre recordsDiv = new Pre();
				recordsDiv.addStyleName(MainResources.GSS.selectable());

				for (String jsonDoc : uiQueryResults.getJsonDocs()) {
					Code code = new Code(jsonDoc);
					code.addStyleName(MainResources.GSS.borderBottom());
					Highlight.highlightBlock(code.getElement());
					recordsDiv.add(code);
				}

				rightScrollPanel.setWidget(recordsDiv);
				rightPanel.add(rightScrollPanel);
			}
		}
		else {
			Paragraph noResultsPara = new Paragraph("No results.");
			noResultsPara.setMargin(25);
			rightPanel.add(noResultsPara);
		}

	}

	@Override
	public void onResize(ResizeEvent event) {
		splitPanel.setHeight(Math.max(600, Window.getClientHeight() - 102) + "px");
		leftScrollPanel.setHeight(Math.max(600, Window.getClientHeight() - 130) + "px");
		rightScrollPanel.setHeight(Math.max(600, Window.getClientHeight() - 130) + "px");
	}
}
