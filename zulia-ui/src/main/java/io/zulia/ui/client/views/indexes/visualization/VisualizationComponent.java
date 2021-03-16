package io.zulia.ui.client.views.indexes.visualization;

import com.axellience.vuegwt.core.annotations.component.Component;
import com.axellience.vuegwt.core.annotations.component.Data;
import com.axellience.vuegwt.core.client.component.IsVueComponent;
import com.axellience.vuegwt.core.client.component.hooks.HasBeforeMount;
import com.axellience.vuegwt.core.client.tools.JsUtils;
import elemental2.core.JsArray;
import io.zulia.ui.client.dto.DropResults;
import io.zulia.ui.client.dto.Payload;
import io.zulia.ui.client.services.ServiceProvider;
import io.zulia.ui.client.util.NotifyUtil;
import jsinterop.annotations.JsMethod;
import jsinterop.base.JsPropertyMap;

@Component
public class VisualizationComponent implements IsVueComponent, HasBeforeMount {

	@Data
	String selectedIndex;

	@Data
	JsPropertyMap<Object> indexSettings = JsUtils.map();

	@Data
	JsArray<String> indexes;

	@Data
	JsArray<Payload<String>> items = new JsArray<>();

	@Data
	JsArray<Payload<String>> items2 = new JsArray<>();

	private void createStuff(JsArray<Payload<String>> items, String keyPostFix) {
		Payload<String> one = new Payload<>();
		one.setData("Drag me (one) " + keyPostFix);
		one.setId("one" + "_" + keyPostFix);

		Payload<String> two = new Payload<>();
		two.setData("Drag me (two) " + keyPostFix);
		two.setId("two" + "_" + keyPostFix);

		Payload<String> three = new Payload<>();
		three.setData("Drag me (three) " + keyPostFix);
		three.setId("three" + "_" + keyPostFix);

		Payload<String> four = new Payload<>();
		four.setData("Drag me (four) " + keyPostFix);
		four.setId("four" + "_" + keyPostFix);

		Payload<String> five = new Payload<>();
		five.setData("Drag me (five) " + keyPostFix);
		five.setId("five" + "_" + keyPostFix);

		Payload<String> six = new Payload<>();
		six.setData("Drag me (six) " + keyPostFix);
		six.setId("six" + "_" + keyPostFix);

		items.push(one);
		items.push(two);
		items.push(three);
		items.push(four);
		items.push(five);
		items.push(six);
	}

	@JsMethod
	public void onDrop(DropResults<Payload<String>> dropResults) {
		if (dropResults.getRemovedIndex() != null || dropResults.getAddedIndex() != null) {
			Payload<String> itemToAdd = dropResults.getPayload();

			if (dropResults.getRemovedIndex() != null) {
				itemToAdd = items.splice(dropResults.getRemovedIndex().intValue(), 1).getAt(0);
			}

			if (dropResults.getAddedIndex() != null) {
				items.splice(dropResults.getAddedIndex().intValue(), 0, itemToAdd);
			}
		}
	}

	@JsMethod
	public Payload<String> getPayload(int index) {
		return items.getAt(index);
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

		createStuff(items, "From Left");
		createStuff(items2, "From Right");
	}

	@JsMethod
	void getIndexSettings() {
		ServiceProvider.get().getService()
				.getIndexSettings(selectedIndex, indexDTO -> this.indexSettings = indexDTO.getIndexSettings(), NotifyUtil::handleError);
	}

}
