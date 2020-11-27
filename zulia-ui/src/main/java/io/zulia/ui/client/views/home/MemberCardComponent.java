package io.zulia.ui.client.views.home;

import com.axellience.vuegwt.core.annotations.component.Component;
import com.axellience.vuegwt.core.annotations.component.Computed;
import com.axellience.vuegwt.core.annotations.component.Data;
import com.axellience.vuegwt.core.annotations.component.Prop;
import com.axellience.vuegwt.core.client.component.IsVueComponent;
import elemental2.core.JsArray;
import io.zulia.ui.client.dto.IndexMappingDTO;
import io.zulia.ui.client.dto.MemberDTO;

@Component
public class MemberCardComponent implements IsVueComponent {

	@Prop
	MemberDTO member;

	@Data
	IndexMappingDTO selectedIndex;

	@Computed
	String getServerAddress() {
		return member.getServerAddress();
	}

	@Computed
	Double getServicePort() {
		return member.getServicePort();
	}

	@Computed
	Double getRestPort() {
		return member.getRestPort();
	}

	@Computed
	JsArray<IndexMappingDTO> getIndexMappings() {
		return member.getIndexMappings();
	}

}
