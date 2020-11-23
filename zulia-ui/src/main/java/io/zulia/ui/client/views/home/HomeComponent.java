package io.zulia.ui.client.views.home;

import com.axellience.vuegwt.core.annotations.component.Component;
import com.axellience.vuegwt.core.annotations.component.Data;
import com.axellience.vuegwt.core.client.component.IsVueComponent;
import com.axellience.vuegwt.core.client.component.hooks.HasBeforeMount;
import elemental2.core.JsArray;
import elemental2.dom.DomGlobal;
import io.zulia.ui.client.ZuliaUI;
import io.zulia.ui.client.dto.NotificationType;
import io.zulia.ui.client.dto.ResultDocumentDTO;
import io.zulia.ui.client.events.NotifyEvent;
import io.zulia.ui.client.services.ServiceProvider;
import jsinterop.annotations.JsMethod;
import org.jboss.elemento.Elements;

@Component
public class HomeComponent implements IsVueComponent, HasBeforeMount {

	@Data
	String selectedIndex;

	@Data
	String results;

	@Data
	JsArray<String> indexes;

	@JsMethod
	public void search() {

		if (selectedIndex == null) {
			ZuliaUI.getEventBus().fireEvent(new NotifyEvent("Please select an index.", NotificationType.WARNING));
			return;
		}

		ServiceProvider.get().getService().search(selectedIndex, "*:*", 1).subscribe(jsonResults -> {
			results = "";
			for (ResultDocumentDTO rd : Elements.elements(jsonResults.getResults())) {
				rd.getDocument().forEach(key -> {
					results += "<strong>" + key + ":</strong> " + rd.getDocument().get(key) + "<br/>";
				});
			}

		}, onError -> DomGlobal.console.log(onError));

	}

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
}
