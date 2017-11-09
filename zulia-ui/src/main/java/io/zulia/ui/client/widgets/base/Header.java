package io.zulia.ui.client.widgets.base;

import com.google.gwt.dom.client.Style;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialHeader;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialNavBar;
import gwt.material.design.client.ui.MaterialNavBrand;
import gwt.material.design.client.ui.MaterialSideNavDrawer;
import io.zulia.ui.client.bundle.MainResources;
import io.zulia.ui.client.controllers.MainController;
import io.zulia.ui.client.places.HomePlace;
import io.zulia.ui.client.places.QueryPlace;

/**
 * Created by Payam Meyer on 3/10/17.
 * @author pmeyer
 */
public class Header extends MaterialHeader {

	private final MaterialSideNavDrawer sideNav;

	public Header() {

		MaterialNavBar navBar = new MaterialNavBar();
		navBar.setLayoutPosition(Style.Position.FIXED);
		navBar.setShadow(0);
		navBar.setActivates("sideNav");
		navBar.getElement().getStyle().setZIndex(999);

		sideNav = new MaterialSideNavDrawer();

		MaterialNavBrand navBrand = new MaterialNavBrand();
		navBrand.add(new MaterialImage(MainResources.INSTANCE.logo()));
		navBrand.setHref("#");
		navBrand.addClickHandler(clickEvent -> sideNav.hide());

		//navBar.add(navBrand);

		add(navBar);

		sideNav.setWidth("60");
		sideNav.setId("sideNav");
		sideNav.setBackgroundColor(Color.GREY_LIGHTEN_3);
		sideNav.setCloseOnClick(true);

		{
			MaterialLink overView = new MaterialLink(IconType.INFO);
			overView.setTitle("Overview");
			overView.addClickHandler(clickEvent -> MainController.get().goTo(new HomePlace()));
			sideNav.add(overView);
		}

		{
			MaterialLink indexes = new MaterialLink(IconType.SEARCH);
			indexes.setTitle("Query");
			indexes.addClickHandler(clickEvent -> MainController.get().goTo(new QueryPlace(null)));
			sideNav.add(indexes);
		}

		add(navBar);
		add(sideNav);

	}

	public MaterialSideNavDrawer getSideNav() {
		return sideNav;
	}

}
