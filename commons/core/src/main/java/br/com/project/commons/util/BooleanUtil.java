package br.com.project.commons.util;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

public class BooleanUtil {
	public static final String TRUE = "V";
	public static final String FALSE = "F";

	public static final Integer INT_FALSE = 0;
	public static final Integer INT_TRUE = 1;

	static {
		Converter bc = new CustomBooleanConverter(Boolean.class);
		ConvertUtils.register(bc, Boolean.class);
		ConvertUtils.register(bc, Boolean.TYPE);
	}

	public static final boolean booleanValue(Object value) {
		if (value == null)
			return false;
		Boolean returnValue = (Boolean) ConvertUtils.convert(value, Boolean.class);
		return returnValue.booleanValue();
	}

	public static final boolean getBoolean(Object value) {
		if (value == null)
			return new Boolean(false);
		return (Boolean) ConvertUtils.convert(value, Boolean.class);
	}

	public static final char getCharValue(boolean bol) {
		return bol == true ? CustomBooleanConverter.getDefaultTrueChar() : CustomBooleanConverter.getDefaultFalseChar();
	}

	public static final char getCharValue(Boolean bol) {
		return booleanValue(bol) ? CustomBooleanConverter.getDefaultTrueChar() : CustomBooleanConverter.getDefaultFalseChar();
	}

	public static final String getYesNo(String value) {
		if (value == null || value.length() == 0)
			return null;
		if (booleanValue(value))
			return TRUE;
		else
			return FALSE;
	}

	public static final String getYesNoNull(String value) {
		if (value == null || value.length() == 0)
			return FALSE;
		if (booleanValue(value))
			return TRUE;
		else
			return FALSE;
	}

	public static final String getYesNo(boolean value) {
		return value ? TRUE : FALSE;
	}

	public static final String getYesNo(Boolean value) {
		return (value != null & value.booleanValue() == true) ? TRUE : FALSE;
	}

	public static final int asInt(Boolean value) {
		return value ? INT_TRUE : INT_FALSE;
	}

}
