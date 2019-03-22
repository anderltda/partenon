package br.com.project.foundation.util;

import org.apache.commons.configuration.PropertiesConfiguration;

public class ConfigWebHelper {

	private static PropertiesConfiguration props;
	
	static {
		try {
			props = new PropertiesConfiguration("config-web.properties");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static int getDefaultRowsPerPage() {
		return props.getInt("default_rows_per_page");
	}
	
	public static String getBasicPackage() {
		return props.getString("basic_package");
	}
	
	public static String getBasicModulePackage() {
		return props.getString("basic_module_package");
	}
	
	public static String getEntityPackage(String module) {
		return MessageHelper.formatMessage(props.getString("entity_package", module));
	}

	public static String getServicePackage(String module) {
		return MessageHelper.formatMessage(props.getString("service_package"), module);
	}
	
	public static String getControllerPackage(String module) {
		return MessageHelper.formatMessage(props.getString("controller_package"), module);
	}
}
