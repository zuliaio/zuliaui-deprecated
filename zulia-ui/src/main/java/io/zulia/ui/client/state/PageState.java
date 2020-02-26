package io.zulia.ui.client.state;

import com.axellience.vueroutergwt.client.Location;
import com.axellience.vueroutergwt.client.VueRouter;
import elemental2.dom.DomGlobal;

public class PageState {

	private static VueRouter vueRouter;

	public static void setVueRouter(VueRouter vueRouter) {
		PageState.vueRouter = vueRouter;
	}

	public static void setNext(String next) {
		vueRouter.push(next);
	}

	public static void goHome() {
		vueRouter.push("/");
	}

	public static void setNextNewWindow(String next) {
		String url = vueRouter.resolve(next).getHref();
		DomGlobal.window.open(url, "_blank");
	}

	public static void setNext(Location next) {
		vueRouter.push(next);
	}

	public static void goBack() {
		vueRouter.back();
	}
}
