package io.zulia.ui.client.views.indexes;

import com.axellience.vuegwt.core.annotations.component.Component;
import com.axellience.vuegwt.core.annotations.component.Data;
import com.axellience.vuegwt.core.client.component.IsVueComponent;
import com.axellience.vuegwt.core.client.component.hooks.HasBeforeMount;
import com.axellience.vuegwt.core.client.tools.JsUtils;
import elemental2.core.JsArray;
import elemental2.dom.DomGlobal;
import io.zulia.ui.client.services.ServiceProvider;
import io.zulia.ui.client.util.NotifyUtil;
import jsinterop.annotations.JsMethod;
import jsinterop.base.JsPropertyMap;

@Component
public class SettingsComponent implements IsVueComponent, HasBeforeMount {

	@Data
	String selectedIndex;

	@Data
	JsPropertyMap<Object> indexSettings = JsUtils.map();

	@Data
	JsArray<String> indexes;

	@Override
	public void beforeMount() {
		if (indexes == null) {
			indexes = new JsArray<>();
		}
		indexes.splice(0, indexes.length);

		ServiceProvider.get().getService().getIndexes().subscribe(indexes -> {
			this.indexes = indexes.getIndexes();
		}, onError -> DomGlobal.console.log(onError));
	}

	@JsMethod
	void getIndexSettings() {
		ServiceProvider.get().getService().getIndexSettings(selectedIndex)
				.subscribe(indexDTO -> this.indexSettings = indexDTO.getIndexSettings(), NotifyUtil::handleError);
	}

}
