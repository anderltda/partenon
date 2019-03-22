package br.com.project.commons.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

public class SetUtil {
	public static final boolean isEmpty(Collection<?> set) {
		if (set == null)
			return true;
		else
			return set.size() == 0;
	}

	public static final boolean nonEmpty(Collection<?> set) {
		return !isEmpty(set);
	}

	public static final boolean isEmpty(Map<?, ?> map) {
		if (map == null)
			return true;
		else
			return map.isEmpty();
	}

	public static final <T> boolean isEmpty(T[] array) {
		return array == null || array.length == 0;
	}

	public static final <T> boolean nonEmpty(T[] array) {
		return !isEmpty(array);
	}

	public static final boolean nonEmpty(Map<?, ?> map) {
		return !isEmpty(map);
	}

	public static final int getSize(Collection<?> col) {
		if (col == null)
			return 0;
		else
			return col.size();
	}

	public static final boolean matchAny(boolean[] booleanValues, boolean match) {
		for (int i = 0; i < booleanValues.length; i++) {
			if (booleanValues[i] == match) return true;
		}
		return false;
	}

	public static final <T> Set<T> getNonNullSetFromList(Collection<T> source) {
		Set<T> set = new HashSet<T>();
		if (source == null) return set;
		for (T t : source) {
			set.add(t);
		}
		return set;
	}

	public static final boolean isEmpty(Object object) {
		if (object == null) return true;

		if (object instanceof Collection) {
			return ((Collection<?>) object).isEmpty();
		} else if (object instanceof List) {
			return ((List<?>) object).isEmpty();
		} else if (object instanceof Object[]) {
			return ((Object[]) object).length == 0;
		} else if (object instanceof Iterator) {
			Iterator<?> it = (Iterator<?>) object;
			return !it.hasNext();
		} else if (object instanceof Map) {
			Map<?, ?> map = (Map<?, ?>) object;
			return map.isEmpty();
		} else if (object instanceof Enumeration) {
			Enumeration<?> it = (Enumeration<?>) object;
			return !it.hasMoreElements();
		} else if (object.getClass().isArray()) {
			return Array.getLength(object) > 0;
		} else {
			throw new IllegalArgumentException("Unsupported object type: " + object.getClass().getName());
		}

	}

	public static final boolean nonEmpty(Object object) {
		return !isEmpty(object);
	}

	public static final String setAsString(Object object) {
		if (object == null) return "(nulo)";

		String str = "";
		if (object instanceof Map) {
			Map<?, ?> map = (Map<?, ?>) object;
			for (Entry<?, ?> entry : map.entrySet()) {
				str += "[key=" + entry.getKey() + " values=" + entry.getValue() + "]";
			}
		} else if (object instanceof List) {
			List<?> list = (List<?>) object;
			for (Object obj : list) {
				str += "[" + obj + "]";
			}
		} else if (object instanceof Object[]) {
			Object[] list = (Object[]) object;
			for (Object obj : list) {
				str += "[" + obj + "]";
			}
		} else if (object instanceof Iterator) {
			Iterator<?> it = (Iterator<?>) object;
			while (it.hasNext()) {
				Object obj = it.next();
				str += "[" + obj + "]";
			}
		} else if (object instanceof Collection) {
			Collection<?> list = (Collection<?>) object;
			for (Object obj : list) {
				str += "[" + obj + "]";
			}
		} else if (object instanceof Enumeration) {
			Enumeration<?> it = (Enumeration<?>) object;
			while (it.hasMoreElements()) {
				Object obj = it.nextElement();
				str += "[" + obj + "]";
			}
		} else if (object.getClass().isArray()) {
			for (int i = 0; i < Array.getLength(object); i++) {
				str += "[" + Array.get(object, i) + "]";
			}
		} else {
			throw new IllegalArgumentException("Unsupported object type: " + object.getClass().getName());
		}

		return str;
	}

	public static final boolean isSet(Object object) {
		if (object == null) return false;
		return object instanceof Map || object instanceof List || object instanceof Object[] || object instanceof Iterator || object instanceof Collection || object instanceof Enumeration || object.getClass().isArray();

	}

	public static final Object[] asArray(Object object) {
		// se não for um set, volta com apenas 1...paciência
		if (!isSet(object)) {
			return new Object[] { object };
		}

		if (object instanceof Collection) {
			Collection<?> list = (Collection<?>) object;
			return list.toArray(new Object[0]);
		} else if (object instanceof List) {
			List<?> list = (List<?>) object;
			return list.toArray(new Object[0]);
		} else if (object instanceof Object[]) {
			return (Object[]) object;
		} else if (object instanceof Iterator) {
			Iterator<?> it = (Iterator<?>) object;
			List<Object> list = new ArrayList<Object>();
			while (it.hasNext()) {
				list.add(it.next());
			}
			return list.toArray(new Object[0]);
		} else if (object instanceof Map) {
			Map<?, ?> map = (Map<?, ?>) object;
			return map.values().toArray(new Object[0]);
		} else if (object instanceof Enumeration) {
			Enumeration<?> enu = (Enumeration<?>) object;
			List<Object> list = new ArrayList<Object>();
			while (enu.hasMoreElements()) {
				list.add(enu.nextElement());
			}
			return list.toArray(new Object[0]);
		} else if (object.getClass().isArray()) {
			Object[] array = new Object[Array.getLength(object)];
			for (int i = 0; i < Array.getLength(object); i++) {
				array[i] = Array.get(object, i);
			}
			return array;
		} else {
			throw new IllegalArgumentException("Unsupported object type: " + object.getClass().getName());
		}
	}

	public static final String asString(Object object, String separator) {
		StringBuilder msg = new StringBuilder();
		boolean addSeparator = false;
		for (Object item : asArray(object)) {
			if (addSeparator) {
				msg.append(separator);
			}
			addSeparator = true;
			msg.append(item);;
		}
		return msg.toString();
	}

	public static final <T> List<T> createListFromVarArgs(T... args) {
		if (args == null || args.length == 0)
			return (List<T>) null;
		else {
			List<T> returnValue = new ArrayList<T>(args.length);
			for (T t : args) {
				if (t != null) {
					returnValue.add(t);
				}
			}
			return returnValue;
		}
	}

	public static final List<Integer> generateRandonList(int size) {
		List<Integer> listaRandom = new ArrayList<Integer>(size);
		Random r = new Random((new Date()).getTime());
		for (int i = 0; i < size; i++) {
			listaRandom.add(Math.abs(r.nextInt()));
		}
		return listaRandom;
	}

}
