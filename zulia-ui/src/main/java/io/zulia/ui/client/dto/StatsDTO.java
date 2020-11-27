package io.zulia.ui.client.dto;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(namespace = JsPackage.GLOBAL, name = "Object", isNative = true)
public class StatsDTO {

	private String name;
	private Double y;
	private Double jvmUsedMemoryMB;
	private Double jvmFreeMemoryMB;
	private Double jvmTotalMemoryMB;
	private Double jvmMaxMemoryMB;
	private Double freeDataDirSpaceGB;
	private Double totalDataDirSpaceGB;
	private Double usedDataDirSpaceGB;

	@JsOverlay
	public final String getName() {
		return name;
	}

	@JsOverlay
	public final void setName(String name) {
		this.name = name;
	}

	@JsOverlay
	public final Double getY() {
		return y;
	}

	@JsOverlay
	public final void setY(Double y) {
		this.y = y;
	}

	@JsOverlay
	public final Double getJvmUsedMemoryMB() {
		return jvmUsedMemoryMB;
	}

	@JsOverlay
	public final void setJvmUsedMemoryMB(Double jvmUsedMemoryMB) {
		this.jvmUsedMemoryMB = jvmUsedMemoryMB;
	}

	@JsOverlay
	public final Double getJvmFreeMemoryMB() {
		return jvmFreeMemoryMB;
	}

	@JsOverlay
	public final void setJvmFreeMemoryMB(Double jvmFreeMemoryMB) {
		this.jvmFreeMemoryMB = jvmFreeMemoryMB;
	}

	@JsOverlay
	public final Double getJvmTotalMemoryMB() {
		return jvmTotalMemoryMB;
	}

	@JsOverlay
	public final void setJvmTotalMemoryMB(Double jvmTotalMemoryMB) {
		this.jvmTotalMemoryMB = jvmTotalMemoryMB;
	}

	@JsOverlay
	public final Double getJvmMaxMemoryMB() {
		return jvmMaxMemoryMB;
	}

	@JsOverlay
	public final void setJvmMaxMemoryMB(Double jvmMaxMemoryMB) {
		this.jvmMaxMemoryMB = jvmMaxMemoryMB;
	}

	@JsOverlay
	public final Double getFreeDataDirSpaceGB() {
		return freeDataDirSpaceGB;
	}

	@JsOverlay
	public final void setFreeDataDirSpaceGB(Double freeDataDirSpaceGB) {
		this.freeDataDirSpaceGB = freeDataDirSpaceGB;
	}

	@JsOverlay
	public final Double getTotalDataDirSpaceGB() {
		return totalDataDirSpaceGB;
	}

	@JsOverlay
	public final void setTotalDataDirSpaceGB(Double totalDataDirSpaceGB) {
		this.totalDataDirSpaceGB = totalDataDirSpaceGB;
	}

	@JsOverlay
	public final Double getUsedDataDirSpaceGB() {
		return usedDataDirSpaceGB;
	}

	@JsOverlay
	public final void setUsedDataDirSpaceGB(Double usedDataDirSpaceGB) {
		this.usedDataDirSpaceGB = usedDataDirSpaceGB;
	}

}
