package io.zulia.ui.client.dto;

import elemental2.core.JsArray;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(namespace = JsPackage.GLOBAL, name = "Object", isNative = true)
public class ResultsDTO {

	private Double totalHits;
	private JsArray<ResultDocumentDTO> results;

	@JsOverlay
	public final Double getTotalHits() {
		return totalHits;
	}

	@JsOverlay
	public final void setTotalHits(Double totalHits) {
		this.totalHits = totalHits;
	}

	@JsOverlay
	public final JsArray<ResultDocumentDTO> getResults() {
		return results;
	}

	@JsOverlay
	public final void setResults(JsArray<ResultDocumentDTO> results) {
		this.results = results;
	}
}
