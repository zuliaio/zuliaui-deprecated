package io.zulia.ui.client.dto;

import elemental2.core.JsArray;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(namespace = JsPackage.GLOBAL, name = "Object", isNative = true)
public class IndexesDTO {

	private JsArray<String> indexes;

	@JsOverlay
	public final JsArray<String> getIndexes() {
		return indexes;
	}

	@JsOverlay
	public final void setIndexes(JsArray<String> indexes) {
		this.indexes = indexes;
	}
}
