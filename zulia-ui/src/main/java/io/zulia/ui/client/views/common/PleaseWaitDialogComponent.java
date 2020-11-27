package io.zulia.ui.client.views.common;

import com.axellience.vuegwt.core.annotations.component.Component;
import com.axellience.vuegwt.core.annotations.component.Data;
import com.axellience.vuegwt.core.client.component.IsVueComponent;
import jsinterop.annotations.JsMethod;

@Component
public class PleaseWaitDialogComponent implements IsVueComponent {

	@Data
	boolean showLoading;

	@Data
	String pleaseWaitText = "Please wait...";

	@JsMethod
	public void showLoading() {
		showLoading = true;
	}

	@JsMethod
	public void hideLoading() {
		showLoading = false;
	}

	@JsMethod
	public void setPleaseWaitText(String pleaseWaitText) {
		this.pleaseWaitText = pleaseWaitText;
	}

	public boolean isShowing() {
		return showLoading;
	}
}
