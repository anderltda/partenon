package br.com.project.foundation.configuration;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.com.project.commons.util.ConfigurationFactory;
import br.com.project.commons.util.ConfigurationParser;
import br.com.project.commons.util.StringUtil;

public class BPConfiguration {
	private static final Log log = LogFactory.getLog(BPConfiguration.class);
	private static final String BOOT_STRAP_CONFIG_FILE_NAME = "/bpfc_config.properties";
	private static final String BOOT_STRAP_NAME = "bpfc.config";

	private static Configuration configuration;
	static {
		String fileName = System.getProperty(BOOT_STRAP_NAME);
		if (StringUtil.isEmpty(fileName)) {
			fileName = BOOT_STRAP_CONFIG_FILE_NAME;
		}
		configuration = ConfigurationFactory.getConfiguration(fileName);
		if (configuration == null) {
			log.error("Erro ao ler bpfc_config.properties. Verificar classPath ou passar o arquivo via -Dbpfc.config=nome_do_arquivo");
		}
	}

	public static final String getString(String key) {
		return ConfigurationParser.getStringValue(configuration, key);
	}

	public static final String getString(String key, String defaultValue) {
		return ConfigurationParser.getStringValue(configuration, key, defaultValue);
	}

	public static String[] getStringArray(String key) {
		return ConfigurationParser.getStringArray(configuration, key);
	}

	public static boolean getBoolean(String key) {
		return ConfigurationParser.getBooleanValue(configuration, key);
	}

	public static int getInt(String key) {
		return ConfigurationParser.getIntValue(configuration, key);
	}

}
