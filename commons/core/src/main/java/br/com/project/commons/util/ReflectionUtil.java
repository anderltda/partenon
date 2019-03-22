package br.com.project.commons.util;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ReflectionUtil {
	private static final Log log = LogFactory.getLog(ReflectionUtil.class);
	private static DynamicClassResolver CUSTOM_CONCRETECLASS_RESOLVER = null;

	private static final Set<String> basicClasses;
	private static final Set<Object> basicInterfaces;
	static {
		basicClasses = new HashSet<String>();
		basicClasses.add(Integer.class.getSimpleName());
		basicClasses.add(Long.class.getSimpleName());
		basicClasses.add(String.class.getSimpleName());
		basicClasses.add(Boolean.class.getSimpleName());
		basicClasses.add(Byte.class.getSimpleName());
		basicClasses.add(Short.class.getSimpleName());
		basicClasses.add(Float.class.getSimpleName());
		basicClasses.add(Double.class.getSimpleName());
		basicClasses.add(BigDecimal.class.getSimpleName());
		basicClasses.add(Date.class.getSimpleName());

		basicInterfaces = new HashSet<Object>();
		/*
		 * basicInterfaces.add(Collection.class); basicInterfaces.add(List.class); basicInterfaces.add(Set.class); basicInterfaces.add(Map.class);
		 */
	}

	private static boolean isPrintableType(Class<?> c) {
		if (c == null)
			return false;
		if (c.isPrimitive())
			return true;
		if (basicClasses.contains(c.getSimpleName()))
			return true;
		if (basicInterfaces.contains(c))
			return true;
		return false;
	}

	public static boolean isBasicClasses(Class<?> c) {
		return basicClasses.contains(c.getSimpleName());
	}

	public synchronized static final void registerConcreteClassResolver(DynamicClassResolver newConcreteClass) {
		CUSTOM_CONCRETECLASS_RESOLVER = newConcreteClass;
	}

	public static final boolean hasACustomConcreteClassResolver() {
		return CUSTOM_CONCRETECLASS_RESOLVER != null;
	}

	@SuppressWarnings("unchecked")
	public static final <T> Class<T> getClassByParameterizedType(Class<T> clazz, int index) {
		if (clazz == null) {
			return null;
		}
		Type gsc = clazz.getGenericSuperclass();
		if (gsc instanceof ParameterizedType) {
			Object tmp = ((ParameterizedType) gsc).getActualTypeArguments()[index];
			return tmp instanceof Class ? (Class<T>) tmp : null;
		}
		return (Class<T>) getClassByParameterizedType(clazz.getSuperclass(), index);
	}

	public static <T> T newInstance(Class<T> clazz, Object... params) {
		if (clazz != null) {
			List<Class<?>> classes = new ArrayList<Class<?>>();
			for (int i = 0; i < params.length; i++) {
				if (params[i] != null && !params[i].toString().equals("")) {
					classes.add(params[i].getClass());
				}
			}
			Constructor<T> cons = null;
			try {
				if (classes.size() > 0) {
					Class<?>[] classArray = new Class[classes.size()];
					cons = clazz.getDeclaredConstructor(classes.toArray(classArray));
					return cons != null ? cons.newInstance(params) : null;
				} else {
					return clazz.newInstance();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static <T> Method getMethod(Class<T> clazz, String prefix, String fieldName, Class<?>... params) {
		String methodName = prefix != null ? prefix + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1) : fieldName;
		Method m = null;
		try {
			m = clazz.getDeclaredMethod(methodName, params);
		} catch (Exception e) {
			try {
				m = clazz.getMethod(methodName, params);
			} catch (Exception e1) {
				Method[] ms = clazz.getMethods();
				for (Method mtmp : ms) {
					if (mtmp.getName().equals(methodName)) {
						m = mtmp;
						break;
					}
				}
			}
		}
		return m;
	}

	public static <T> Method getGetterMethod(Class<T> clazz, String fieldName) {
		return getMethod(clazz, "get", fieldName, new Class[] {});
	}

	public static Object executeMethod(String prefix, Object object, String fieldName, Class<?>[] valueClass, Object[] value) {
		if (object == null) {
			return null;
		}

		Object ret = null;

		object = getLastObject(object, fieldName);
		if (object != null) {
			Class<?> objectClass = (Class<?>) getConcreteClass(object);

			if (fieldName.indexOf('.') > 0) {
				fieldName = fieldName.substring(fieldName.lastIndexOf('.') + 1);
			}

			Method method = getMethod(objectClass, prefix, fieldName, valueClass);
			if (method != null) {
				try {
					ret = method.invoke(object, value);
				} catch (Exception e) {
					log.debug("Erro no executeMethod " + object.getClass() + " field: " + fieldName);
				}
			}
		}

		return ret;
	}

	@SuppressWarnings("unchecked")
	public static <T> Object executeMethod(String prefix, Object object, String fieldName, T value) {
		Class<?> valueClass = getFieldType(object, fieldName);
		if (valueClass != null && List.class.isAssignableFrom(valueClass)) {
			valueClass = (Class<T>) List.class;
		}
		return executeMethod(prefix, object, fieldName, new Class[] { valueClass }, new Object[] { value });
	}

	@SuppressWarnings("unchecked")
	public static <T> Object executeSetterMethod(Object object, String fieldName, T value) {
		Class<?> valueClass = getFieldType(object, fieldName);
		if (valueClass != null && List.class.isAssignableFrom(valueClass)) {
			valueClass = (Class<T>) List.class;
		}
		return executeMethod("set", object, fieldName, new Class[] { valueClass }, new Object[] { value });
	}

	public static Object executeGetterMethod(Object object, String fieldName) {
		return executeMethod("get", object, fieldName, new Class[] {}, new Object[] {});
	}

	public static Field getField(Class<?> objectClass, String fieldName) {
		Field f = null;
		try {
			int strIdx = fieldName.indexOf("Str");
			fieldName = strIdx > 0 ? fieldName.substring(0, strIdx) : fieldName;
			f = objectClass.getDeclaredField(fieldName);
		} catch (Exception e) {
			if (objectClass != null) {
				return getField(objectClass.getSuperclass(), fieldName);
			}
		}

		return f;
	}

	public static Field getField(Object object, String fieldName) {
		object = getLastObject(object, fieldName);
		int idx = fieldName.lastIndexOf('.');
		if (idx > 0) {
			fieldName = fieldName.substring(idx + 1);
		}
		return getField(getConcreteClass(object), fieldName);
	}

	public static boolean hasField(Object object, String fieldName) {
		return getField(object, fieldName) != null;
	}

	public static boolean hasField(Class<?> objectClass, String fieldName) {
		return getField(objectClass, fieldName) != null;
	}

	public static List<Field> getFields(Class<?> clazz, Class<?> classLimit) {
		ArrayList<Field> ret = new ArrayList<Field>();
		listFields(clazz, classLimit, ret, ".*");
		return ret;
	}

	public static List<Field> getFields(Class<?> clazz, Class<?> classLimit, String pattern) {
		ArrayList<Field> ret = new ArrayList<Field>();
		listFields(clazz, classLimit, ret, pattern);
		return ret;
	}

	public static List<Field> getFields(Class<?> clazz) {
		return getFields(clazz, Object.class);
	}

	private static void listFields(Class<?> clazz, Class<?> classLimit, List<Field> ret, String pattern) {
		if (!classLimit.equals(clazz) && clazz != null) {

			Field[] fields = clazz.getDeclaredFields();

			for (Field field : fields) {
				if (!ret.contains(field) && field.getName().matches(pattern)) {
					ret.add(field);
				}
			}
			listFields(clazz.getSuperclass(), classLimit, ret, pattern);
		}
	}

	public static List<Method> getMethods(Class<?> clazz, Class<?> classLimit) {
		ArrayList<Method> ret = new ArrayList<Method>();
		listMethods(clazz, classLimit, null, ret);
		return ret;
	}

	public static <T extends Annotation> List<Method> getMethods(Class<?> clazz, Class<?> classLimit, Class<T> withAnnotation) {
		ArrayList<Method> ret = new ArrayList<Method>();
		listMethods(clazz, classLimit, withAnnotation, ret);
		return ret;
	}

	public static List<Method> getMethods(Class<?> clazz) {
		return getMethods(clazz, Object.class);
	}

	public static <T extends Annotation> List<Method> getMethodsWithAnnotation(Class<?> clazz, Class<T> withAnnotation) {
		return getMethods(clazz, Object.class, withAnnotation);
	}

	private static <T extends Annotation> void listMethods(Class<?> clazz, Class<?> classLimit, Class<T> withAnnotation, ArrayList<Method> ret) {
		if (!classLimit.equals(clazz)) {
			Method[] methods = clazz.getDeclaredMethods();
			for (Method method : methods) {
				if (withAnnotation == null || method.getAnnotation(withAnnotation) != null) {
					ret.add(method);
				}
			}
			listMethods(clazz.getSuperclass(), classLimit, withAnnotation, ret);
		}
	}

	public static Object getLastObject(Object obj, String fieldName) {
		if (fieldName.indexOf('.') > 0) {
			String fieldNameTmp = fieldName.substring(fieldName.indexOf('.') + 1);
			fieldName = fieldName.substring(0, fieldName.indexOf('.'));

			Object tmp = executeGetterMethod(obj, fieldName);
			if (tmp != null && Collection.class.isAssignableFrom(getConcreteClass(tmp))) {
				Field field = getField(obj, fieldName);
				Class<?> clazz = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
				try {
					tmp = clazz.newInstance();
				} catch (Exception e) {
				}
			} else if (tmp == null) {
				tmp = newInstance(getFieldType(getConcreteClass(obj), fieldName));
				executeSetterMethod(obj, fieldName, tmp);
			}
			obj = tmp;
			return getLastObject(obj, fieldNameTmp);
		}

		return obj;
	}

	public static Class<?> getConcreteClass(Object obj) {
		if (CUSTOM_CONCRETECLASS_RESOLVER == null) {
			Class<?> objClass = null;
			if (obj != null) {
				objClass = obj.getClass();
			}
			return objClass;
		} else
			return CUSTOM_CONCRETECLASS_RESOLVER.getConcreteClass(obj);
	}

	public static String getBeanClassName(Object obj) {
		Class<?> clazz = obj instanceof Class<?> ? (Class<?>) obj : getConcreteClass(obj);
		return clazz != null ? clazz.getSimpleName() : null;
	}

	public static Class<?> getFieldType(Class<?> objectClass, String fieldName) {
		Class<?> ret = null;
		try {
			Field f = objectClass.getDeclaredField(fieldName);
			ret = (Class<?>) f.getType();
		} catch (Exception e) {
			if (objectClass != null && !objectClass.isInterface() && !Object.class.equals(objectClass)) {
				return getFieldType(objectClass.getSuperclass(), fieldName);
			}
		}

		return ret;
	}

	public static Class<?> getFieldType(Object object, String fieldName) {
		object = getLastObject(object, fieldName);
		int idx = fieldName.lastIndexOf('.');
		if (idx > 0) {
			fieldName = fieldName.substring(idx + 1);
		}
		return getFieldType(getConcreteClass(object), fieldName);
	}

	public static ArrayList<Class<?>> loadClasses(URL url, String matchPack) {
		ArrayList<String> listaClass = new ArrayList<String>();
		String path = url.getPath();
		if (path.indexOf(".jar") > 0) {
			try {
				populateClassFiles(new JarFile(path.substring(6, path.indexOf("!"))), listaClass);
			} catch (IOException e) {
				try {
					populateClassFiles(new JarFile(path.substring(5, path.indexOf("!"))), listaClass);
				} catch (IOException e1) {
				}
			}
		} else {
			if (path.indexOf("META-INF/persistence.xml") >= 0) {
				path = path.substring(0, path.indexOf("META-INF/persistence.xml"));
			}
			populateClassFiles(new File(path), listaClass);
		}

		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
		for (String className : listaClass) {
			String name = pathToPackage(className);
			if (matchPack == null || name.matches(translatePackageER(matchPack))) {
				Class<?> clazz = null;
				try {
					clazz = Class.forName(name);
				} catch (ClassNotFoundException e) {
				}
				if (clazz != null) {
					classes.add(clazz);
				}
			}
		}

		return classes;
	}

	private static void populateClassFiles(File dir, ArrayList<String> lista) {
		if (dir.isDirectory()) {
			File[] arqs = dir.listFiles();
			for (File arq : arqs) {
				populateClassFiles(arq, lista);
			}
		} else {
			String name = dir.getAbsolutePath();
			name = name.substring(name.indexOf("classes" + File.separatorChar) + 8).replaceAll("\\" + File.separatorChar, ".");
			if (name.endsWith(".class")) {
				lista.add(name);
			}
		}
	}

	private static void populateClassFiles(JarFile jar, ArrayList<String> lista) {
		Enumeration<JarEntry> je = jar.entries();
		while (je.hasMoreElements()) {
			String name = je.nextElement().getName();
			if (name.endsWith(".class")) {
				lista.add(name);
			}
		}
	}

	private static final String pathToPackage(String p) {
		int classIdx = p.indexOf(".class");
		if (classIdx >= 0) {
			p = p.substring(0, classIdx);
		}
		return p.replaceAll("\\/", ".");
	}

	public static final String translatePackageER(String pack) {
		return pack.replaceAll("\\.", "\\\\\\.").replaceAll("\\*", ".*?");
	}

	public static final String getPropertyName(String name) {
		if (StringUtil.isEmptyTrim(name))
			return name;
		String[] items = StringUtil.PATTERN_SPLITTER.split(name);
		StringBuilder returnValue = new StringBuilder();
		for (int i = 0; i < items.length; i++) {
			returnValue.append(StringUtil.firstUpperRestLower(items[i]));
		}
		return StringUtil.firstLower(returnValue.toString());
	}

	public static String getSetterName(String name) {
		return "set" + StringUtil.firstUpper(getPropertyName(name));
	}

	public static String getGetterName(String name) {
		return "get" + StringUtil.firstUpper(getPropertyName(name));
	}

	public static final boolean isIdNull(Object object) {
		if (object == null)
			return true;
		try {
			Object id = ReflectionUtil.executeGetterMethod(object, "id");
			if (id != null)
				return false;
			id = ReflectionUtil.executeGetterMethod(object, "id" + object.getClass().getSimpleName());
			return id == null;
		} catch (Exception e) {
			return true;
		}
	}

	public static final String toString(Object obj) {
		StringBuilder returnValue = new StringBuilder();
		Field instanceList[] = getConcreteClass(obj).getDeclaredFields();
		ArrayList<Field> fieldList = new ArrayList<Field>();
		Class<?> parentClass = getConcreteClass(obj).getSuperclass();
		while (true) {
			Field parentFieldList[] = parentClass.getDeclaredFields();
			if (parentFieldList.length > 0) {
				CollectionUtils.addAll(fieldList, parentFieldList);
				parentClass = parentClass.getSuperclass();
			} else {
				break;
			}
		}

		for (int i = 0; i < instanceList.length; i++) {
			Field fld = instanceList[i];
			if (!fld.getName().equals("serialVersionUID") && isPrintableType(fld.getType())) {
				String tmp;
				try {
					tmp = BeanUtils.getProperty(obj, fld.getName());
					returnValue.append("[").append(fld.getName()).append("=").append(tmp).append("]");
				} catch (Exception e) {
				}
			}
		}

		for (Field fld : fieldList) {
			if (!fld.getName().equals("serialVersionUID") && isPrintableType(fld.getType())) {
				String tmp;
				try {
					tmp = BeanUtils.getProperty(obj, fld.getName());
					returnValue.append("[").append(fld.getName()).append("=").append(tmp).append("]");
				} catch (Exception e) {
				}
			}
		}
		return returnValue.toString();
	}
}
