package io.zulia.ui.client.dto;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

@JsType(namespace = GLOBAL, name = "Object", isNative = true)
public class Payload<T> {

	public String id;
	public T data;

	@JsOverlay
	public final String getId() {
		return id;
	}

	@JsOverlay
	public final void setId(String id) {
		this.id = id;
	}

	@JsOverlay
	public final T getData() {
		return data;
	}

	@JsOverlay
	public final void setData(T data) {
		this.data = data;
	}
}
