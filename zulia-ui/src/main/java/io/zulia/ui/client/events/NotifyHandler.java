package io.zulia.ui.client.events;

import com.google.gwt.event.shared.EventHandler;
import io.zulia.ui.client.dto.NotificationType;

public interface NotifyHandler extends EventHandler {

	void handleNotify(String message, NotificationType type);
}
