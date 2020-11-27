package io.zulia.ui.client.views.indexes;

import com.axellience.vuegwt.core.annotations.component.Component;
import com.axellience.vuegwt.core.annotations.component.Data;
import com.axellience.vuegwt.core.annotations.component.Ref;
import com.axellience.vuegwt.core.client.component.IsVueComponent;
import com.axellience.vuegwt.core.client.component.hooks.HasBeforeMount;
import elemental2.core.Global;
import elemental2.core.JsArray;
import elemental2.dom.DomGlobal;
import io.zulia.ui.client.ZuliaUI;
import io.zulia.ui.client.dto.NotificationType;
import io.zulia.ui.client.dto.ResultDocumentDTO;
import io.zulia.ui.client.events.NotifyEvent;
import io.zulia.ui.client.services.ServiceProvider;
import io.zulia.ui.client.views.common.PleaseWaitDialogComponent;
import jsinterop.annotations.JsMethod;
import org.jboss.elemento.Elements;

@Component(components = { PleaseWaitDialogComponent.class })
public class IndexesComponent implements IsVueComponent, HasBeforeMount {

	@Ref
	PleaseWaitDialogComponent pleaseWaitDialog;

	@Data
	String selectedIndex;

	@Data
	JsArray<Object> results = new JsArray<>();

	@Data
	JsArray<String> indexes;

	@Data
	String query = "*:*";

	@Data
	Double rows = 5d;

	@JsMethod
	public void search() {

		pleaseWaitDialog.showLoading();

		if (selectedIndex == null) {
			ZuliaUI.getEventBus().fireEvent(new NotifyEvent("Please select an index.", NotificationType.WARNING));
			pleaseWaitDialog.hideLoading();
			return;
		}

		ServiceProvider.get().getService().search(selectedIndex, query, rows.intValue()).subscribe(jsonResults -> {
			results.splice(0, results.length);

			for (ResultDocumentDTO rd : Elements.elements(jsonResults.getResults())) {
				results.push(Global.JSON.parse(Global.JSON.stringify(rd)));
			}

			pleaseWaitDialog.hideLoading();
		}, onError -> {
			pleaseWaitDialog.hideLoading();
			DomGlobal.console.log(onError);
		});

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
