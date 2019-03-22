package br.com.project.foundation.util;

import java.util.ArrayList;
import java.util.List;

import br.com.project.commons.annotation.FilterProperty;
import br.com.project.commons.util.AnnotationUtil;
import br.com.project.commons.util.ReflectionUtil;
import br.com.project.commons.util.SetUtil;

public class ArrayCollectionConvert {
	
	public static List<?> convert(Class<?> clazz, Object[] objects) throws InstantiationException, IllegalAccessException {
		
		List<FilterProperty> filters = AnnotationUtil.filter(clazz);
		List<Object> objectList = new ArrayList<Object>();
		
		if(objects == null)
			objects = new Object[0];		
		
		int interacao = (objects.length / filters.size());
		
		int e = 0;	
		for (int i = 0; i < interacao; i++) {			
			Object object = clazz.newInstance();			
			for (FilterProperty fp : filters) {	
			    ReflectionUtil.executeSetterMethod(object, fp.getName(), objects[e]);
			    e++;
			}			
			objectList.add(object);
		}		
		
		return objectList;
	}
	
	public static Object[] getObjects(Class<?> clazz, List<?> list) {
		
		List<FilterProperty> filters = AnnotationUtil.filter(clazz);
		int sizeArray = SetUtil.nonEmpty(list) ? list.size() : 0;
		Object[] objects = new Object[sizeArray * filters.size()];
		
		int e = 0;
		if(SetUtil.nonEmpty(list)) {
			for (int i = 0; i < list.size(); i++) {				
				for (FilterProperty fp : filters) {					
					Object object = list.get(i);
					objects[e] = ReflectionUtil.executeGetterMethod(object, fp.getName());
					e++;
				}			
			}			
		}
		
		return objects;
	}

}
