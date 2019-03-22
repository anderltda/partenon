package br.com.project.foundation.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.com.project.foundation.validator.ValidatorService;

public class ServiceLocator implements ApplicationContextAware {

	private static ServiceLocator instance = new ServiceLocator();

	protected ApplicationContext applicationContext;

	private ServiceLocator() {
	}

	public static ServiceLocator getInstance() {
		return instance;
	}

	public static ServiceLocator getLocalInstance() {
		instance.initXML();

		return instance;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public Object getBean(String name) {
		return applicationContext != null ? applicationContext.getBean(name) : null;
	}

	public ValidatorService getValidatorService(String serviceName) {
		return (ValidatorService) getBean(serviceName);
	}

	public <T extends ValidatorService> T getValidatorService(Class<T> targetValidatorType, String serviceName) {
		return (T) applicationContext.getBean(serviceName, targetValidatorType);
	}

	private void initXML() {
		if (applicationContext == null) {
			applicationContext = new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml" });
		}
	}

	public void printBeans() {
		if (instance == null) return;
		for (String beanName : applicationContext.getBeanDefinitionNames()) {
			System.out.println(beanName);
		}
	}

}
