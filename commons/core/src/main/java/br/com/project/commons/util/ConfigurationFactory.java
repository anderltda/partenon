package br.com.project.commons.util;

import java.net.URL;
import java.util.Iterator;

import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ConfigurationFactory {
	private static final Log log = LogFactory
			.getLog(ConfigurationFactory.class);

	public static final Configuration getConfiguration(String fileName) {
		Configuration configuration = null;
		// try to get a -D that points to the file
		if (StringUtil.isEmpty(fileName)) {
			log.debug("Nome de arquivo inválido - vazio");
		} else {
			try {
				Character delimiter = AbstractConfiguration
						.getDefaultListDelimiter();
				AbstractConfiguration.setDefaultListDelimiter('\0');

				URL url = ConfigurationFactory.class.getResource(fileName);
				if (url == null) {
					configuration = new PropertiesConfiguration(fileName);
				} else
					configuration = new PropertiesConfiguration(url);
				if (configuration.isEmpty()) {
					log.error("Arquivo de Configuração Vazio");
				}
				log.info("Arquivo " + fileName + " lido. ");

				if (log.isDebugEnabled()) {
					Iterator<?> it = (Iterator<?>) configuration.getKeys();
					while (it.hasNext()) {
						Object o = it.next();
						log.debug(o + "="
								+ configuration.getString(o.toString()));
					}
				}
				AbstractConfiguration.setDefaultListDelimiter(delimiter);
			} catch (ConfigurationException e) {
				log.error("Erro ao ler arquivo de configuração " + fileName);
				log.error(e);
			}
		}
		return configuration;
	}

	public static final XMLConfiguration getXMLConfiguration(String fileName) {
		XMLConfiguration config = null;
		try {
			Character delimiter = AbstractConfiguration
					.getDefaultListDelimiter();
			AbstractConfiguration.setDefaultListDelimiter('\0');
			URL url = ConfigurationFactory.class.getResource(fileName);
			if (url == null) {
				config = new XMLConfiguration(fileName);
			} else
				config = new XMLConfiguration(url);
			if (config.isEmpty()) {
				if (log.isErrorEnabled()) {
					log.error("Empty Query List File : " + fileName);
				}
				AbstractConfiguration.setDefaultListDelimiter(delimiter);
				return null;
			}
			if (log.isDebugEnabled()) {
				log.debug("Parsing Query List File : " + fileName);
			}
			AbstractConfiguration.setDefaultListDelimiter(delimiter);
			return config;
		} catch (ConfigurationException e) {
			if (log.isErrorEnabled()) {
				log.error("Arquivo de Tratamento de Query List : " + fileName);
				log.error(e);
			}
			return null;
		}

	}

}
