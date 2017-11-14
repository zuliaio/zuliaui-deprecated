package io.zulia.ui.client.widgets.query;

import com.google.gwt.user.client.ui.ScrollPanel;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.MaterialBadge;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialCardContent;
import gwt.material.design.client.ui.MaterialCardTitle;
import gwt.material.design.client.ui.MaterialCollection;
import gwt.material.design.client.ui.MaterialCollectionItem;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.html.Span;

import java.util.Map;

public class FacetsResultsView extends MaterialRow {

	public FacetsResultsView(Map<String, Map<String, Long>> facetsMap) {

		for (String fieldName : facetsMap.keySet()) {

			MaterialColumn fieldColumn = new MaterialColumn(12, 6, 4);

			MaterialCard fieldCard = new MaterialCard();
			fieldCard.setBackgroundColor(Color.GREY_DARKEN_1);
			fieldColumn.add(fieldCard);

			MaterialCardTitle title = new MaterialCardTitle();
			title.setPadding(10);
			title.setTextColor(Color.WHITE);
			title.setText(fieldName);
			fieldCard.add(title);

			MaterialCardContent fieldContent = new MaterialCardContent();

			MaterialCollection facetCollection = new MaterialCollection();
			fieldContent.add(facetCollection);

			for (String facet : facetsMap.get(fieldName).keySet()) {
				MaterialCollectionItem facetCollectionItem = new MaterialCollectionItem();
				Span facetLable = new Span(facet);
				MaterialBadge facetBadge = new MaterialBadge(facetsMap.get(fieldName).get(facet) + "");
				facetCollectionItem.add(facetLable);
				facetCollectionItem.add(facetBadge);
				facetCollection.add(facetCollectionItem);
			}

			ScrollPanel sp = new ScrollPanel(fieldContent);
			sp.setHeight("300px");
			fieldCard.add(sp);

			fieldColumn.add(fieldCard);

			add(fieldColumn);
		}

	}
}
