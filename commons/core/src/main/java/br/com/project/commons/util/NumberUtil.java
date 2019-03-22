package br.com.project.commons.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

import org.apache.commons.beanutils.ConvertUtils;

public class NumberUtil {
	public static final int PRECISAO_CALCULOS = 2;
	public static final BigDecimal DEFAULT_BIGD_VALUE = new BigDecimal("0");

	public static final BigDecimal ZERO_BIGD_VALUE;

	static {
		ZERO_BIGD_VALUE = new BigDecimal("0");
		ZERO_BIGD_VALUE.setScale(PRECISAO_CALCULOS, RoundingMode.HALF_UP);
	}

	public static final double doubleValue(Object value) {
		if (value == null)
			return 0;
		Double returnValue = (Double) ConvertUtils.convert(value, Double.class);
		return returnValue.doubleValue();
	}

	public static final double parseDouble(String value) {
		if (value == null)
			return 0;
		NumberFormat nf = NumberFormat.getNumberInstance();
		try {
			Number number = nf.parse(value);
			return number.doubleValue();
		} catch (ParseException e) {
			Double returnValue = (Double) ConvertUtils.convert(value, Double.class);
			return returnValue;
		}
	}

	public static final Double getDouble(Object value) {
		if (value == null)
			return null;
		return (Double) ConvertUtils.convert(value, Double.class);
	}

	public static final Short getShort(Object value) {
		if (value == null)
			return null;
		return (Short) ConvertUtils.convert(value, Short.class);
	}

	public static final Byte getByte(Object value) {
		if (value == null)
			return null;
		return (Byte) ConvertUtils.convert(value, Byte.class);
	}

	public static final byte byteValue(Object value) {
		if (value == null)
			return 0;
		Byte returnValue = (Byte) ConvertUtils.convert(value, Byte.class);
		return returnValue.byteValue();
	}

	public static final long longValue(Object value) {
		if (value == null)
			return 0L;
		Long returnValue = (Long) ConvertUtils.convert(value, Long.class);
		return returnValue.longValue();
	}

	public static final Long getLong(Object value) {
		if (value == null)
			return null;
		return (Long) ConvertUtils.convert(value, Long.class);
	}

	public static final int intValue(Object value) {
		if (value == null)
			return 0;
		Integer returnValue = (Integer) ConvertUtils.convert(value, Integer.class);
		return returnValue.intValue();
	}

	public static final Integer getInteger(Object value) {
		if (value == null)
			return null;
		return (Integer) ConvertUtils.convert(value, Integer.class);
	}

	public static final boolean isPositive(Long longValue) {
		return longValue != null && longValue.longValue() > 0 ? true : false;
	}

	public static final boolean nonPositive(Long longValue) {
		return !isPositive(longValue);
	}

	public static final boolean isPositive(Byte byteValue) {
		return byteValue != null && byteValue.byteValue() > 0 ? true : false;
	}

	public static final boolean nonPositive(Byte byteValue) {
		return !isPositive(byteValue);
	}

	public static final boolean isPositive(Short shortValue) {
		return shortValue != null && shortValue.shortValue() > 0 ? true : false;
	}

	public static final boolean nonPositive(Short shortValue) {
		return !isPositive(shortValue);
	}

	public static final boolean isPositive(Integer intValue) {
		return intValue != null && intValue.intValue() > 0 ? true : false;
	}

	public static final boolean nonPositive(Integer intValue) {
		return !isPositive(intValue);
	}

	public static final boolean nonPositive(Double doubleValue) {
		return !isPositive(doubleValue);
	}

	public static final boolean isPositive(Double doubleValue) {
		return doubleValue != null && doubleValue.doubleValue() > 0 ? true : false;
	}

	public static final boolean isPositive(BigDecimal bigDecimalValue) {
		if (bigDecimalValue == null)
			return false;
		return bigDecimalValue.doubleValue() > 0;
	}

	public static final Float getFloat(Object value) {
		if (value == null)
			return null;
		return (Float) ConvertUtils.convert(value, Float.class);
	}

	public static final Float floatValue(Object value) {
		if (value == null)
			return 0f;
		Float returnValue = (Float) ConvertUtils.convert(value, Float.class);
		return returnValue.floatValue();
	}

	public static final short shortValue(Object value) {
		if (value == null)
			return 0;
		Short returnValue = (Short) ConvertUtils.convert(value, Short.class);
		return returnValue.shortValue();
	}

	public static final BigDecimal getBigDecimalValue(Object value) {
		if (value == null)
			return null;
		BigDecimal returnValue = (BigDecimal) ConvertUtils.convert(value, BigDecimal.class);
		return returnValue;
	}

	public static final BigDecimal getBigDecimalValueFromDouble(double valor) {
		BigDecimal tmp = new BigDecimal(valor);
		return tmp.setScale(PRECISAO_CALCULOS, RoundingMode.HALF_UP);
	}

	public static final BigDecimal getBigDecimalValueFromDouble(double valor, int precisao) {
		BigDecimal tmp = new BigDecimal(valor);
		return tmp.setScale(precisao, RoundingMode.HALF_UP);
	}

	public static final Double getBigDecimalAsZeroDouble(BigDecimal value) {
		if (value == null)
			return 0d;
		else
			return value.doubleValue();
	}

	public static final boolean isBetween(Long value, Long start, Long end) {
		if (value == null || start == null || end == null)
			return false;
		return value.longValue() >= start.longValue() && value.longValue() <= end.longValue();
	}

	public static final boolean isIn(Integer value, Integer... args) {
		if (value == null || args == null || args.length == 0)
			return false;

		for (int i = 0; i < args.length; i++) {
			if (args[i] != null && args[i].intValue() == value.intValue())
				return true;
		}
		return false;
	}

	public static final boolean isBetween(Integer value, Integer start, Integer end) {
		if (value == null || start == null || end == null)
			return false;
		return value.intValue() >= start.intValue() && value.intValue() <= end.intValue();
	}

	public static final boolean isBetween(Double value, Double start, Double end) {
		if (value == null || start == null || end == null)
			return false;
		return value.doubleValue() >= start.doubleValue() && value.doubleValue() <= end.doubleValue();
	}

	public static final boolean isPercentageValid(Double value) {
		return isBetween(value, 0d, 100d);
	}

	public static final boolean equals(Double... args) {
		if (args == null)
			return true;
		if (args.length == 1)
			return true;
		for (int i = 1; i < args.length; i++) {
			if (doubleValue(args[i]) != doubleValue(args[i - 1]))
				return false;
		}
		return true;
	}

	public static final Double getDoubleComPrecisao(Object value) {
		Double dblVal = getDouble(value);
		if (dblVal != null) {
			return getBigDecimalValueFromDouble(dblVal).doubleValue();
		} else
			return null;
	}

	public static final Double getDoubleComPrecisao(Double value) {
		if (value != null) {
			return getBigDecimalValueFromDouble(value.doubleValue()).doubleValue();
		} else
			return new Double(0);
	}

	public static final Double getDoubleComPrecisao(Double value, int precisao) {
		if (value != null) {
			return getBigDecimalValueFromDouble(value.doubleValue(), precisao).doubleValue();
		} else
			return new Double(0);
	}

	public static final BigDecimal getBigDecimalComPrecisao(Object value) {
		if (value != null) {
			BigDecimal tmp = (BigDecimal) ConvertUtils.convert(value, BigDecimal.class);
			return tmp.setScale(PRECISAO_CALCULOS, RoundingMode.HALF_UP);
		} else
			return null;
	}

	public static final BigDecimal getBigDecimal(BigDecimal value) {
		if (value != null) {
			return value.setScale(PRECISAO_CALCULOS, RoundingMode.HALF_UP);
		} else
			return value;
	}

	public static final String numberAsCurrency(Object value) {
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		return nf.format(value);
	}

	public static final BigDecimal add(BigDecimal n1, BigDecimal n2) {
		if (n1 == null) {
			if (n2 == null) {
				return DEFAULT_BIGD_VALUE;
			} else
				return n2;
		} else {
			if (n2 == null) {
				return n1;
			} else
				return n1.add(n2);
		}
	}

	public static final BigDecimal addComPrecisao(BigDecimal n1, BigDecimal n2) {
		BigDecimal returnValue;
		if (n1 == null) {
			if (n2 == null) {
				return ZERO_BIGD_VALUE;
			} else
				returnValue = n2;
		} else {
			if (n2 == null) {
				returnValue = n1;
			} else
				returnValue = n1.add(n2);
		}
		returnValue.setScale(PRECISAO_CALCULOS, RoundingMode.HALF_UP);
		return returnValue;
	}

	public static final BigDecimal subtractComPrecisao(BigDecimal n1, BigDecimal n2) {
		BigDecimal returnValue;
		if (n1 == null) {
			if (n2 == null) {
				return ZERO_BIGD_VALUE;
			} else
				returnValue = n2;
		} else {
			if (n2 == null) {
				returnValue = n1;
			} else
				returnValue = n1.subtract(n2);
		}
		returnValue.setScale(PRECISAO_CALCULOS, RoundingMode.HALF_UP);
		return returnValue;
	}

	public static final BigDecimal multiplyComPrecisao(BigDecimal n1, BigDecimal n2) {
		BigDecimal returnValue;
		if (n1 == null) {
			if (n2 == null) {
				return ZERO_BIGD_VALUE;
			} else
				returnValue = n2;
		} else {
			if (n2 == null) {
				returnValue = n1;
			} else
				returnValue = n1.multiply(n2);
		}
		returnValue.setScale(PRECISAO_CALCULOS, RoundingMode.HALF_UP);
		return returnValue;
	}

	public static final BigDecimal divideComPrecisao(BigDecimal n1, BigDecimal n2) {
		BigDecimal returnValue;
		if (n1 == null) {
			if (n2 == null) {
				return ZERO_BIGD_VALUE;
			} else
				returnValue = n2;
		} else {
			if (n2 == null) {
				returnValue = n1;
			} else
				returnValue = n1.divide(n2, PRECISAO_CALCULOS);
		}
		returnValue.setScale(PRECISAO_CALCULOS, RoundingMode.HALF_UP);
		return returnValue;
	}

	public static final BigDecimal add(BigDecimal n1, double n2) {
		if (n1 == null) {
			if (n2 == 0) {
				return DEFAULT_BIGD_VALUE;
			} else
				return getBigDecimalComPrecisao(n2);
		} else {
			if (n2 == 0) {
				return n1;
			} else
				return n1.add(getBigDecimalValueFromDouble(n2));
		}
	}

	public static String formatNumberDouble(String value) {
		
		if(StringUtil.isNotEmpty(value)) {
			return value.replace(".", "").replace(",", ".").replace("%", "");			
		}
		
		return null;
	}
	
	/**
	 * Formata um short para o padrao (R$0.000,00)
	 * 
	 * @param s
	 *                Valor
	 * @return Short formatado
	 */
	public static String formatNumber(Short s) {
		return NumberUtil.formatNumber(s.doubleValue());
	}

	/**
	 * Formata um double para o padrao (R$0.000,00)
	 * 
	 * @param d
	 *                Valor
	 * @return Double formatado
	 */
	public static String formatNumber(Long l) {
		return NumberUtil.formatNumber(l.doubleValue());
	}

	/**
	 * Formata um float para o padrao (R$0.000,00)
	 * 
	 * @param s
	 *                Valor
	 * @return Float formatado
	 */
	public static String formatNumber(Float f) {
		return NumberUtil.formatNumber(f.doubleValue());
	}

	/**
	 * Formata um long para o padrao (R$0.000,00)
	 * 
	 * @param s
	 *                Valor
	 * @return Long formatado
	 */
	public static String formatNumber(Double l) {
		DecimalFormat df = new DecimalFormat(DECIMAL_PATTERN);
		return df.format(l);
	}

	private static final String DECIMAL_PATTERN = "#,##0.00";

	/**
	 * Formata um Short conforme o Patter informado
	 * 
	 * @param s
	 *                Valor
	 * @return Short formatado
	 */
	public static String formatNumber(String pattern, Short s) {
		return formatNumber(pattern, s.longValue());
	}

	/**
	 * Formata um Integer conforme o Patter informado
	 * 
	 * @param s
	 *                Valor
	 * @return Integer formatado
	 */
	public static String formatNumber(String pattern, Integer i) {
		return formatNumber(pattern, i.longValue());
	}

	/**
	 * Formata um Long conforme o Patter informado
	 * 
	 * @param s
	 *                Valor
	 * @return Long formatado
	 */
	public static String formatNumber(String pattern, Long l) {
		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(l);
	}

	/**
	 * Formata um Double conforme o Patter informado
	 * 
	 * @param s
	 *                Valor
	 * @return Double formatado
	 */
	public static String formatNumber(String pattern, Double d) {
		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(d);
	}

}