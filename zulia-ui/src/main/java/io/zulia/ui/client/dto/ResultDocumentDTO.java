package io.zulia.ui.client.dto;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.JsPropertyMap;

@JsType(namespace = JsPackage.GLOBAL, name = "Object", isNative = true)
public class ResultDocumentDTO {

	private String id;
	private Double score;
	private String indexName;
	private JsPropertyMap<Object> document;

	@JsOverlay
	public final String getId() {
		return id;
	}

	@JsOverlay
	public final void setId(String id) {
		this.id = id;
	}

	@JsOverlay
	public final Double getScore() {
		return score;
	}

	@JsOverlay
	public final void setScore(Double score) {
		this.score = score;
	}

	@JsOverlay
	public final String getIndexName() {
		return indexName;
	}

	@JsOverlay
	public final void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	@JsOverlay
	public final JsPropertyMap<Object> getDocument() {
		return document;
	}

	@JsOverlay
	public final void setDocument(JsPropertyMap<Object> document) {
		this.document = document;
	}
}
