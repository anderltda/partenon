package br.com.project.foundation.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Transient;

import br.com.project.commons.util.AnnotationUtil;
import br.com.project.commons.util.ReflectionUtil;
import br.com.project.commons.util.StringUtil;
import br.com.project.foundation.persistence.BasicEntityObject;

/**
 * @author anderson.nascimento
 * Classe responsável pela montagem das condições para query
 *
 */
public class BuildCondition {
	
	public static final Map<String, Object> buildMapsEntity(Object object) {		

		Map<String, Object> maps = new HashMap<String, Object>();

		Field[] fields = object.getClass().getDeclaredFields();
		
		String id = null;
		Object value = null;
		String valueString = null;

		for (Field field : fields) {			

			id = field.getName();
			value = ReflectionUtil.executeGetterMethod(object, id);
			
			Annotation annotation = AnnotationUtil.getAnnotation(object.getClass(), Transient.class, id);
			
			if(annotation != null) {
				//continue;
			}
			
			if(value != null) {

				if(value instanceof String) {					
					
					if(StringUtil.isNotEmpty(value.toString())) {
						valueString = " like ".concat("'").concat(value.toString()).concat("'").replace("*", "%");					
						maps.put(id, valueString);
					}
					
				} else if(value instanceof BasicEntityObject) {					
					
					maps.put(id + ".id", "=" + "'" + ReflectionUtil.executeGetterMethod(value, "id") + "'");
					
				} else {
					
					maps.put(id, " = " + (value));
					
				}
			}			
		}
		
		return maps;
	}

}
