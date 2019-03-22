package br.com.project.commons.util;

public interface DynamicClassResolver {
	Class<?> getConcreteClass(Object obj);
}
