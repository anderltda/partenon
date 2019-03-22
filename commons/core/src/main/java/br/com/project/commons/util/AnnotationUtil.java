package br.com.project.commons.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.project.commons.annotation.Column;
import br.com.project.commons.annotation.Combo;
import br.com.project.commons.annotation.FilterProperty;
import br.com.project.commons.annotation.Label;
import br.com.project.commons.annotation.filter.ButtonFilter;
import br.com.project.commons.annotation.filter.DateFilter;
import br.com.project.commons.annotation.filter.DialogFilter;
import br.com.project.commons.annotation.filter.MaskFilter;
import br.com.project.commons.annotation.filter.SelectFilter;
import br.com.project.commons.annotation.filter.TextFilter;

/**
 * @author anderson.nascimento
 *
 */
public class AnnotationUtil {

	@SuppressWarnings("unchecked")
	public static List<Field> listFields(Class<?> clazz, List<Class<? extends Annotation>> annotationsClass) {
		List<Field> annotedFields = new ArrayList<Field>();
		List<Field> fields = ReflectionUtil.getFields(clazz, Object.class);
		for (Field field : fields) {
			for (Class<?> annotationClass : annotationsClass) {
				Class<Annotation> cl = (Class<Annotation>) annotationClass;
				Annotation a = (Annotation) field.getAnnotation(cl);
				if (a != null) {
					annotedFields.add(field);
				}
			}
		}

		return annotedFields;
	}

	@SuppressWarnings("unchecked")
	public static List<FilterProperty> filter(Class<?> clazz) {
		List<FilterProperty> filters = new ArrayList<FilterProperty>();
		List<Field> annotedFields = listFields(clazz, Arrays.asList(TextFilter.class,
				SelectFilter.class, DateFilter.class, MaskFilter.class, DialogFilter.class,
				Combo.class, ButtonFilter.class));
		
		for (Field field : annotedFields) {
			TextFilter text = field.getAnnotation(TextFilter.class);
			SelectFilter select = field.getAnnotation(SelectFilter.class);
			DateFilter date = field.getAnnotation(DateFilter.class);
			MaskFilter mask = field.getAnnotation(MaskFilter.class);	
			DialogFilter dialog = field.getAnnotation(DialogFilter.class);	
			Combo combo = field.getAnnotation(Combo.class);
			ButtonFilter button = field.getAnnotation(ButtonFilter.class);

			FilterProperty fp = new FilterProperty();
			fp.setName(field.getName());
			fp.setFieldType(field.getType());

			if (text != null) {
				fp.setAnnotation(text);
			} else if (select != null) {
				fp.setAnnotation(select);
			} else if (date != null) {
				fp.setAnnotation(date);
			} else if (mask != null) {
				fp.setAnnotation(mask);
			} else if (dialog != null) {
				fp.setAnnotation(dialog);
			} else if (combo != null) {
				fp.setAnnotation(combo);
			} else if (button != null) {
				fp.setAnnotation(button);
			}
			
			filters.add(fp);
		}
		
/*		if(filters.size() % 2 > 0) {
			Annotation a = clazz.getAnnotation(annotationClass);
			FilterProperty fp = new FilterProperty();
			fp.setName(null);
			fp.setAnnotation(Blanck.class);
			fp.setFieldType(null);
			filters.add(fp);
		}*/

		Collections.sort(filters, new Comparator<FilterProperty>() {

			@Override
			public int compare(FilterProperty o1, FilterProperty o2) {
				return o1.getOrder() - o2.getOrder();
			}

		});

		return filters;
	}

	@SuppressWarnings("unchecked")
	public static List<FilterProperty> table(Class<?> clazz) {
		List<FilterProperty> filters = new ArrayList<FilterProperty>();
		List<Field> annotedFields = listFields(clazz, Arrays.asList(Label.class, Column.class));
		
		for (Field field : annotedFields) {
			Label label = field.getAnnotation(Label.class);	
			Column column = field.getAnnotation(Column.class);

			FilterProperty fp = new FilterProperty();
			fp.setName(field.getName());
			fp.setFieldType(field.getType());

			if (label != null) {
				fp.setAnnotation(label);
			} else if (column != null) {
				fp.setAnnotation(column);
			} 
			
			filters.add(fp);
		}

		Collections.sort(filters, new Comparator<FilterProperty>() {

			@Override
			public int compare(FilterProperty o1, FilterProperty o2) {
				return o1.getOrder() - o2.getOrder();
			}

		});

		return filters;
	}

	public static <T extends Annotation> List<Method> listMethods(Class<?> clazz, Class<T> annotationClass) {
		List<Method> annotedMethods = new ArrayList<Method>();
		List<Method> methods = ReflectionUtil.getMethods(clazz, Object.class, annotationClass);
		for (Method method : methods) {
			annotedMethods.add(method);
		}

		return annotedMethods;
	}

	public static <T extends Annotation> boolean invoke(Object obj, Class<T> annotationClass, Object... args) throws Exception {
		List<Method> methods = listMethods(obj.getClass(), annotationClass);
		for (Method method : methods) {
			try {
				method.invoke(obj, args);
			} catch (Exception e) {
				throw e;
			}
		}

		return methods.size() > 0;
	}

	public static <T extends Annotation> Annotation getAnnotation(Class<?> clazz, Class<T> annotationClass) {
		return getAnnotation(clazz, annotationClass, null);
	}

	public static <T extends Annotation> Annotation getAnnotation(Class<?> clazz, Class<T> annotationClass, String fieldName) {
		Annotation a = clazz.getAnnotation(annotationClass);
		if (a == null && fieldName != null) {
			Field field = ReflectionUtil.getField(clazz, fieldName);
			if (field != null) {
				a = field.getAnnotation(annotationClass);
			}

			if (a == null) {
				Method m = ReflectionUtil.getMethod(clazz, "get", StringUtil.firstUpper(fieldName), new Class<?>[0]);
				if (m != null) {
					a = m.getAnnotation(annotationClass);
				}
				if (a == null && clazz.getSuperclass() != null) {
					return getAnnotation(clazz.getSuperclass(), annotationClass, fieldName);
				}
			}
		}

		return a;
	}
}
