package br.com.project.commons.util;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.beanutils.converters.AbstractConverter;

public class CustomBooleanConverter extends AbstractConverter {
	private static final char DEFAULT_TRUE_CHAR = 'V';
	private static final char DEFAULT_FALSE_CHAR = 'F';

	private static final String DEFAULT_TRUE_STRING = String.valueOf(DEFAULT_TRUE_CHAR);
	private static final String DEFAULT_FALSE_STRING = String.valueOf(DEFAULT_FALSE_CHAR);
	private static final Set<String> trueStrings;
	static {
		trueStrings = new HashSet<String>();
		trueStrings.add("true");
		trueStrings.add("v");
		trueStrings.add("s");
		trueStrings.add("sim");
		trueStrings.add("yes");
		trueStrings.add("y");
		trueStrings.add("on");
		trueStrings.add("1");
		trueStrings.add("1.0");
	}

	public CustomBooleanConverter(Class<Boolean> defaultType) {
		super(defaultType);
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Object convertToType(Class type, Object value) throws Throwable {
		if (value == null)
			return Boolean.FALSE;
		String stringValue = value.toString().toLowerCase();
		return trueStrings.contains(stringValue);
	}

	public static final char getDefaultTrueChar() {
		return DEFAULT_TRUE_CHAR;
	}

	public static final char getDefaultFalseChar() {
		return DEFAULT_FALSE_CHAR;
	}

	public static final String getDefaultTrueString() {
		return DEFAULT_TRUE_STRING;
	}

	public static final String getDefaultFalseString() {
		return DEFAULT_FALSE_STRING;
	}

	@Override
	protected Class<?> getDefaultType() {
		return String.class;
	}

}
