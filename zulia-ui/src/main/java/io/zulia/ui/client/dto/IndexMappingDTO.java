package io.zulia.ui.client.dto;

import elemental2.core.JsArray;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(namespace = JsPackage.GLOBAL, name = "Object", isNative = true)
public class IndexMappingDTO {

	private String name;
	private Double size;
	private JsArray<Double> primary;
	private JsArray<Double> replica;

	@JsOverlay
	public final String getName() {
		return name;
	}

	@JsOverlay
	public final void setName(String name) {
		this.name = name;
	}

	@JsOverlay
	public final Double getSize() {
		return size;
	}

	@JsOverlay
	public final void setSize(Double size) {
		this.size = size;
	}

	@JsOverlay
	public final JsArray<Double> getPrimary() {
		return primary;
	}

	@JsOverlay
	public final void setPrimary(JsArray<Double> primary) {
		this.primary = primary;
	}

	@JsOverlay
	public final JsArray<Double> getReplica() {
		return replica;
	}

	@JsOverlay
	public final void setReplica(JsArray<Double> replica) {
		this.replica = replica;
	}
}
