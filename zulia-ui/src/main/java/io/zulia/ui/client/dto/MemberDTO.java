package io.zulia.ui.client.dto;

import elemental2.core.JsArray;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(namespace = JsPackage.GLOBAL, name = "Object", isNative = true)
public class MemberDTO {

	private String serverAddress;
	private Double servicePort;
	private Double restPort;
	private Double heartBeat;
	private JsArray<IndexMappingDTO> indexMappings;

	@JsOverlay
	public final String getServerAddress() {
		return serverAddress;
	}

	@JsOverlay
	public final void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}

	@JsOverlay
	public final Double getServicePort() {
		return servicePort;
	}

	@JsOverlay
	public final void setServicePort(Double servicePort) {
		this.servicePort = servicePort;
	}

	@JsOverlay
	public final Double getRestPort() {
		return restPort;
	}

	@JsOverlay
	public final void setRestPort(Double restPort) {
		this.restPort = restPort;
	}

	@JsOverlay
	public final Double getHeartBeat() {
		return heartBeat;
	}

	@JsOverlay
	public final void setHeartBeat(Double heartBeat) {
		this.heartBeat = heartBeat;
	}

	@JsOverlay
	public final JsArray<IndexMappingDTO> getIndexMappings() {
		return indexMappings;
	}

	@JsOverlay
	public final void setIndexMappings(JsArray<IndexMappingDTO> indexMappings) {
		this.indexMappings = indexMappings;
	}
}
