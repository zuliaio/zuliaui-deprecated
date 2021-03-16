package io.zulia.ui.client.util;

import elemental2.dom.DomGlobal;
import io.zulia.ui.client.ZuliaUI;
import io.zulia.ui.client.dto.NotificationType;
import io.zulia.ui.client.events.NotifyEvent;

public class NotifyUtil {

	public static void handleError(int statusCode, String statusMessage, String errorBody) {
		DomGlobal.console.log(errorBody);
		if (statusCode == 504 || statusMessage.contains("Error instantiating bean of type")) {
			ZuliaUI.getEventBus().fireEvent(
					new NotifyEvent("The corresponding service for this app is down, please report the problem to help@lexicalintelligence.com",
							NotificationType.ERROR));
		}
		else {
			ZuliaUI.getEventBus().fireEvent(new NotifyEvent(statusMessage, NotificationType.ERROR));
		}

	}

	public static void handleSuccess(String successMessage) {
		ZuliaUI.getEventBus().fireEvent(new NotifyEvent(successMessage, NotificationType.SUCCESS));
	}

	public static void handleWarning(String warningMessage) {
		ZuliaUI.getEventBus().fireEvent(new NotifyEvent(warningMessage, NotificationType.WARNING));
	}

	public static void handleError(String errorMessage) {
		ZuliaUI.getEventBus().fireEvent(new NotifyEvent(errorMessage, NotificationType.ERROR));
	}

}
