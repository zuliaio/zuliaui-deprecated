package io.zulia.ui.client.dto;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.JsPropertyMap;

@JsType(namespace = JsPackage.GLOBAL, name = "Object", isNative = true)
public class IndexDTO {

	private JsPropertyMap<Object> indexSettings;

	@JsOverlay
	public final JsPropertyMap<Object> getIndexSettings() {
		return indexSettings;
	}

	@JsOverlay
	public final void setIndexSettings(JsPropertyMap<Object> indexSettings) {
		this.indexSettings = indexSettings;
	}
}
