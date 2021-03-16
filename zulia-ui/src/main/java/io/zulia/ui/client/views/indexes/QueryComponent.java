package io.zulia.ui.client.views.indexes;

import com.axellience.vuegwt.core.annotations.component.Component;
import com.axellience.vuegwt.core.annotations.component.Data;
import com.axellience.vuegwt.core.annotations.component.Ref;
import com.axellience.vuegwt.core.client.component.IsVueComponent;
import com.axellience.vuegwt.core.client.component.hooks.HasBeforeCreate;
import com.axellience.vuegwt.core.client.component.hooks.HasBeforeMount;
import com.axellience.vuegwt.core.client.component.hooks.HasMounted;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import elemental2.core.Global;
import elemental2.core.JsArray;
import elemental2.dom.DomGlobal;
import io.zulia.ui.client.ZuliaUI;
import io.zulia.ui.client.dto.NotificationType;
import io.zulia.ui.client.dto.ResultDocumentDTO;
import io.zulia.ui.client.events.NotifyEvent;
import io.zulia.ui.client.services.ServiceProvider;
import io.zulia.ui.client.util.NotifyUtil;
import io.zulia.ui.client.views.common.PleaseWaitDialogComponent;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsProperty;
import org.jboss.elemento.Elements;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component(components = { PleaseWaitDialogComponent.class })
public class QueryComponent implements IsVueComponent, HasBeforeMount, HasBeforeCreate, HasMounted, ResizeHandler {

	@Ref
	PleaseWaitDialogComponent pleaseWaitDialog;

	@Data
	String splitPanelHeight;

	@Data
	JsArray<String> selectedIndex;

	@JsProperty
	@Data
	Map<String, JsArray<Object>> resultsMap = new HashMap<>();

	@Data
	String tab;

	@Data
	JsArray<String> indexes;

	@Data
	String sortFields;

	@Data
	String queryFields;

	@Data
	String documentFields;

	@Data
	String query = "*:*";

	@Data
	Double rows = 5d;

	@JsMethod
	public void search() {

		pleaseWaitDialog.showLoading();

		if (selectedIndex == null || selectedIndex.length == 0) {
			ZuliaUI.getEventBus().fireEvent(new NotifyEvent("Please select an index.", NotificationType.WARNING));
			pleaseWaitDialog.hideLoading();
			return;
		}

		ServiceProvider.get().getService()
				.search(selectedIndex, query, rows.intValue(), parseValues(sortFields), parseValues(queryFields), parseValues(documentFields), true,
						jsonResults -> {
							resultsMap.clear();

							for (ResultDocumentDTO rd : Elements.elements(jsonResults.getResults())) {
								resultsMap.computeIfAbsent(rd.getIndexName(), v -> new JsArray<>()).push(Global.JSON.parse(Global.JSON.stringify(rd)));
							}

							pleaseWaitDialog.hideLoading();
						}, (statusCode, status, errorBody) -> {
							pleaseWaitDialog.hideLoading();
							DomGlobal.console.log(errorBody);
						});

	}

	private JsArray<String> parseValues(String values) {
		if (values != null && !values.isEmpty()) {
			if (values.contains(";") || values.contains(",") || values.contains(" ")) {
				JsArray<String> valuesArray = new JsArray<>();
				Arrays.stream(values.split(";|,|\\s")).iterator().forEachRemaining(item -> {
					if (item.trim().length() > 0) {
						valuesArray.push(item.trim());
					}
				});
				return valuesArray;
			}
			else {
				return new JsArray<>(values);
			}
		}

		return null;
	}

	@Override
	public void beforeMount() {

		if (indexes == null) {
			indexes = new JsArray<>();
		}
		indexes.splice(0, indexes.length);

		ServiceProvider.get().getService().getIndexes(indexes -> {
			this.indexes = indexes.getIndexes();
		}, NotifyUtil::handleError);
	}

	private void calculateSplitPanelHeight() {
		int height = Window.getClientHeight() - 93;
		splitPanelHeight = "height: " + height + "px; margin-top:-15px; margin-bottom:-15px";
	}

	@Override
	public void mounted() {
		calculateSplitPanelHeight();
	}

	@Override
	public void onResize(ResizeEvent event) {
		calculateSplitPanelHeight();
	}

	@Override
	public void beforeCreate() {
		Window.addResizeHandler(this);
	}

}
