package io.zulia.ui.client.dto;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

@JsType(namespace = GLOBAL, name = "Object", isNative = true)
public class DragResults<T> {

	private T payload;
	private boolean isSource;
	private boolean willAcceptDrop;

	@JsOverlay
	public final T getPayload() {
		return payload;
	}

	@JsOverlay
	public final void setPayload(T payload) {
		this.payload = payload;
	}

	@JsOverlay
	public final boolean isSource() {
		return isSource;
	}

	@JsOverlay
	public final void setSource(boolean source) {
		isSource = source;
	}

	@JsOverlay
	public final boolean isWillAcceptDrop() {
		return willAcceptDrop;
	}

	@JsOverlay
	public final void setWillAcceptDrop(boolean willAcceptDrop) {
		this.willAcceptDrop = willAcceptDrop;
	}
}
