package io.zulia.ui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Payam Meyer on 11/14/17.
 * @author pmeyer
 */
public class ConfigLoader {

	private static final Gson GSON_BUILDER = new GsonBuilder().create();

	private static final String CONFIG_FILE = System.getProperty("user.home") + File.separator + "zulia" + File.separator + "zulia_ui_config.json";

	private static final Logger LOG = Logger.getLogger(ConfigLoader.class);

	private static Map<String, String> configFileMap;

	static {
		try {
			LOG.info("Trying to load: " + CONFIG_FILE);
			InputStream configFileStream = new FileInputStream(CONFIG_FILE);
			try (BufferedReader configBuffer = new BufferedReader(new InputStreamReader(configFileStream))) {
				String json = configBuffer.lines().collect(Collectors.joining("\n"));
				Type type = new TypeToken<Map<String, String>>() {
				}.getType();
				configFileMap = GSON_BUILDER.fromJson(json, type);
			}
			LOG.info("Loaded config file.");

		}
		catch (Exception e) {
			LOG.error("Could not load the config file, please make sure zulia/zulia_ui_config.json exists in your home directory.", e);
			throw new RuntimeException(e);
		}
	}

	public static Map<String, String> getConfig() {
		return configFileMap;
	}

}
