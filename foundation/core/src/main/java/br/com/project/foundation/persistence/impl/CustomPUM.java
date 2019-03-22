package br.com.project.foundation.persistence.impl;

import java.net.URL;
import java.util.List;
import java.util.Properties;

import javax.persistence.spi.PersistenceUnitInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo;

public class CustomPUM extends DefaultPersistenceUnitManager {
	private static final Log log = LogFactory.getLog(CustomPUM.class);
	// bug
	// http://opensource.atlassian.com/projects/hibernate/browse/HHH-4864
	@Override
	protected void postProcessPersistenceUnitInfo(MutablePersistenceUnitInfo newPU) {
		log.debug("########################################################################################");
		log.debug("Custom PUM em acao");
		super.postProcessPersistenceUnitInfo(newPU);
		newPU.addJarFileUrl(newPU.getPersistenceUnitRootUrl());
		System.out.println(newPU.getPersistenceUnitRootUrl());
		newPU.setPersistenceUnitRootUrl(null);
		final String persistenceUnitName = newPU.getPersistenceUnitName();
		final PersistenceUnitInfo oldPU = getPersistenceUnitInfo(persistenceUnitName);
		if (oldPU != null) {
			List<URL> urls = oldPU.getJarFileUrls();
			for (URL url : urls) {
				newPU.addJarFileUrl(url);
				log.debug("url => " + url);
			}
			
			List<String> managedClassNames = oldPU.getManagedClassNames();
			for (String managedClassName : managedClassNames) {
				newPU.addManagedClassName(managedClassName);
				log.debug("managed class =>" + managedClassName);
			}
			List<String> mappingFileNames = oldPU.getMappingFileNames();
			for (String mappingFileName : mappingFileNames) {
				newPU.addMappingFileName(mappingFileName);
				log.debug("mappingFileName  =>" + mappingFileName);
			}
			Properties oldProperties = oldPU.getProperties();
			Properties newProperties = newPU.getProperties();
			newProperties.putAll(oldProperties);
			newPU.setProperties(newProperties);
		}
		log.debug("########################################################################################");
	}

}
