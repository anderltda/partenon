package br.com.project.commons.util;

import java.util.Properties;

import org.apache.commons.configuration.Configuration;

public class ConfigurationParser {
	public static final String getStringValue(Configuration configuration, String propertyName) {
		return getStringValue(configuration, propertyName, null);
	}

	public static final String getStringValue(Configuration configuration, String propertyName, String defaultValue) {
		if (configuration == null) return defaultValue;
		else return StringUtil.fullTrim(configuration.getString(propertyName));
	}

	public static final int getIntValue(Configuration configuration, String propertyName) {
		if (configuration == null) return 0;
		else return configuration.getInt(propertyName);
	}

	public static final long getLongValue(Configuration configuration, String propertyName) {
		if (configuration == null) return 0;
		else return configuration.getLong(propertyName);
	}

	public static final boolean getBooleanValue(Configuration configuration, String propertyName) {
		if (configuration == null) return false;
		else return configuration.getBoolean(propertyName);
	}

	public static final Properties getProperties(Configuration configuration, String propertyName) {
		if (configuration == null) return new Properties();
		else return configuration.getProperties(propertyName);
	}

	public static final String[] getStringArray(Configuration configuration, String propertyName) {
		return getStringArray(configuration, propertyName, new String[0]);
	}

	public static final String[] getStringArray(Configuration configuration, String propertyName, String[] defaultValue) {
		if (configuration == null) return defaultValue;
		else {
			String[] returnValue = configuration.getStringArray(propertyName);
			return (returnValue == null) ? defaultValue : returnValue;
		}
	}

}
