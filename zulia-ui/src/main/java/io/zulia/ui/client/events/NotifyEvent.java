package io.zulia.ui.client.events;

import com.google.gwt.event.shared.GwtEvent;
import io.zulia.ui.client.dto.NotificationType;

public class NotifyEvent extends GwtEvent<NotifyHandler> {
	public static final Type<NotifyHandler> TYPE = new Type<NotifyHandler>();

	private String message;
	private NotificationType type;

	public NotifyEvent(String message, NotificationType type) {
		this.message = message;
		this.type = type;
	}

	@Override
	public Type<NotifyHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(NotifyHandler handler) {
		handler.handleNotify(message, type);
	}

}
