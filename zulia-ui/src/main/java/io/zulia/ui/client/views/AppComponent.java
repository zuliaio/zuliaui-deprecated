package io.zulia.ui.client.views;

import com.axellience.vuegwt.core.annotations.component.Component;
import com.axellience.vuegwt.core.annotations.component.Data;
import com.axellience.vuegwt.core.client.component.IsVueComponent;
import com.axellience.vuegwt.core.client.component.hooks.HasBeforeCreate;
import io.zulia.ui.client.RoutesConfig;
import io.zulia.ui.client.ZuliaUI;
import io.zulia.ui.client.dto.NotificationType;
import io.zulia.ui.client.events.NotifyEvent;
import io.zulia.ui.client.events.NotifyHandler;
import io.zulia.ui.client.state.PageState;
import io.zulia.ui.client.vuetify.VuetifyCustomizeOptions;
import jsinterop.annotations.JsMethod;
import us.ascendtech.momentjs.client.MomentJS;

@Component(customizeOptions = { RoutesConfig.class, VuetifyCustomizeOptions.class })
public class AppComponent implements IsVueComponent, NotifyHandler, HasBeforeCreate {

	@Data
	String successMessage;

	@Data
	boolean showSuccess;

	@Data
	String errorMessage;

	@Data
	boolean showError;

	@Data
	String warningMessage;

	@Data
	boolean showWarning;

	@Data
	boolean drawer;

	@Data
	boolean multiLine = true;

	@Data
	double currentYear = new MomentJS().year();

	@JsMethod
	public void goHome() {
		PageState.setNext("/");
	}

	@Override
	public void handleNotify(String message, NotificationType type) {
		if (type.equals(NotificationType.SUCCESS)) {
			successMessage = message;
			showSuccess = true;
		}
		else if (type.equals(NotificationType.ERROR)) {
			errorMessage = message;
			showError = true;
		}
		else if (type.equals(NotificationType.WARNING)) {
			warningMessage = message;
			showWarning = true;
		}
	}

	@Override
	public void beforeCreate() {
		ZuliaUI.getEventBus().addHandler(NotifyEvent.TYPE, this);
	}
}