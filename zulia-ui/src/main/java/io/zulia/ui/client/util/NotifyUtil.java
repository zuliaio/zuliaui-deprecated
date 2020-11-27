package io.zulia.ui.client.util;

import com.intendia.gwt.autorest.client.RequestResponseException;
import elemental2.dom.DomGlobal;
import io.zulia.ui.client.ZuliaUI;
import io.zulia.ui.client.dto.NotificationType;
import io.zulia.ui.client.events.NotifyEvent;

public class NotifyUtil {

	public static void handleError(Throwable onError) {
		DomGlobal.console.log(onError);
		if (onError instanceof RequestResponseException.FailedStatusCodeException) {
			RequestResponseException.FailedStatusCodeException failedStatusCodeException = (RequestResponseException.FailedStatusCodeException) onError;
			int statusCode = failedStatusCodeException.getStatusCode();
			if (statusCode == 504 || onError.getMessage().contains("Error instantiating bean of type")) {
				ZuliaUI.getEventBus().fireEvent(
						new NotifyEvent("The corresponding service for this app is down, please report the problem to help@lexicalintelligence.com",
								NotificationType.ERROR));
			}
			else {
				ZuliaUI.getEventBus().fireEvent(new NotifyEvent(onError.getMessage(), NotificationType.ERROR));
			}
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
