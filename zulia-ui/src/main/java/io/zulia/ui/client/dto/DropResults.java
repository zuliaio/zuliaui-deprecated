package io.zulia.ui.client.dto;

import elemental2.dom.Element;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

@JsType(namespace = GLOBAL, name = "Object", isNative = true)
public class DropResults<T> {

	private Number addedIndex;
	private Number removedIndex;
	private T payload;
	private Element droppedElement;

	@JsOverlay
	public final Number getAddedIndex() {
		return addedIndex;
	}

	@JsOverlay
	public final void setAddedIndex(Number addedIndex) {
		this.addedIndex = addedIndex;
	}

	@JsOverlay
	public final Number getRemovedIndex() {
		return removedIndex;
	}

	@JsOverlay
	public final void setRemovedIndex(Number removedIndex) {
		this.removedIndex = removedIndex;
	}

	@JsOverlay
	public final T getPayload() {
		return payload;
	}

	@JsOverlay
	public final void setPayload(T payload) {
		this.payload = payload;
	}

	@JsOverlay
	public final Element getDroppedElement() {
		return droppedElement;
	}

	@JsOverlay
	public final void setDroppedElement(Element droppedElement) {
		this.droppedElement = droppedElement;
	}
}
