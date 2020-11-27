package io.zulia.ui.client.dto;

import elemental2.core.JsArray;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(namespace = JsPackage.GLOBAL, name = "Object", isNative = true)
public class MembersDTO {

	JsArray<MemberDTO> members;

	@JsOverlay
	public final JsArray<MemberDTO> getMembers() {
		return members;
	}

	@JsOverlay
	public final void setMembers(JsArray<MemberDTO> members) {
		this.members = members;
	}
}
