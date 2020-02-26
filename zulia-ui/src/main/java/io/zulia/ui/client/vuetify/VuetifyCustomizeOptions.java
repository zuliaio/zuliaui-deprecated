package io.zulia.ui.client.vuetify;

import com.axellience.vuegwt.core.client.component.options.CustomizeOptions;
import com.axellience.vuegwt.core.client.component.options.VueComponentOptions;

public class VuetifyCustomizeOptions implements CustomizeOptions {

	@Override
	public void customizeOptions(VueComponentOptions vueComponentOptions) {
		VuetifyOptions vuetifyOptions = new VuetifyOptions();

		vuetifyOptions.setTheme(new VuetifyTheme());

		VuetifyThemes themes = new VuetifyThemes();
		vuetifyOptions.getTheme().setThemes(themes);

		VuetifyColors colors = new VuetifyColors();
		colors.setPrimary("#242943");
		colors.setSecondary("#6fc3df");
		colors.setAccent("#ec8d81");
		colors.setError("#f46b56");
		colors.setWarning("#CE7C01");
		colors.setInfo("#98C1D9");
		colors.setSuccess("#64b478");

		themes.setLight(colors);

		VuetifyIcons icons = new VuetifyIcons();
		icons.setIconfont("mdi");

		vuetifyOptions.setIcons(icons);

		vueComponentOptions.set("vuetify", new Vuetify(vuetifyOptions));
	}

}
