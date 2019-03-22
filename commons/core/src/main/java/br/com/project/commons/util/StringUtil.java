package br.com.project.commons.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author anderson.nascimento
 *
 */
public class StringUtil {
	public static Pattern PATTERN_SPLITTER = Pattern.compile("[=_\\s*]");
	public static final String NON_ACCENT_TRANSLATION_DEST = "aeiouAEIOUaeiouAEIOUaonAONaeiouAEIOUaeiouAEIOUcCao";
	public static final Map<Character, Character> ACCENT_CONVERSION_MAP;
	public static final String ACCENT_TRANSLATION_SOURCE = "áéíóúÁÉÍÓÚâêîôûÂÊÎÔÛãõñÃÕÑàèìòùÀÈÌÒÙäëïöüÄËÏÖÜçÇªº";
	public static final String LINE_BREAK = "\r\n";
	public static final String EMPTY = "";

	static {
		ACCENT_CONVERSION_MAP = new HashMap<Character, Character>(ACCENT_TRANSLATION_SOURCE.length());
		for (int i = 0; i < ACCENT_TRANSLATION_SOURCE.length(); i++) {
			ACCENT_CONVERSION_MAP.put(ACCENT_TRANSLATION_SOURCE.charAt(i), NON_ACCENT_TRANSLATION_DEST.charAt(i));
		}
	}

	public static boolean isNotEmpty(String string) {
		return string != null && string.length() > 0;
	}

	public static boolean isEmpty(String string) {
		return string == null || string.length() == 0;
	}

	public static final boolean isEmptyTrim(String value) {
		if (value == null || value.length() == 0)
			return true;
		return (value.trim().length() == 0);
	}

	public static final boolean anyEmpty(String... value) {
		if (value == null || value.length == 0)
			return false;
		for (String string : value) {
			if (isEmptyTrim(string))
				return true;
		}
		return false;
	}

	public static final boolean isNotEmptyTrim(String value) {
		return !isEmptyTrim(value);
	}

	public static void removeLast(StringBuilder builder) {
		if (builder == null || builder.length() == 0)
			return;
		builder.setLength(builder.length() - 1);
	}

	public static String removeLast(String str) {
		if (isEmpty(str))
			return str;
		else
			return str.substring(0, str.length() - 1);
	}

	public static String subString(String str, int lastPosition) {
		if (str.length() < lastPosition)
			return str;
		else
			return str.substring(0, lastPosition);
	}

	public static String subStringEmpty(String str, int lastPosition) {
		if (str == null) {
			return EMPTY;
		} else if (str.length() < lastPosition) {
			return str;
		} else {
			return str.substring(0, lastPosition);
		}
	}

	public static String getNonAccentString(String source) {
		if (isEmpty(source))
			return source;
		else {
			char[] modified = source.toUpperCase().toCharArray();
			for (int i = 0; i < modified.length; i++) {
				Character replacementCh = ACCENT_CONVERSION_MAP.get(modified[i]);
				if (replacementCh != null) {
					modified[i] = replacementCh.charValue();
				}
			}
			return String.valueOf(modified);

		}
	}

	public static String getUpperNonAccentString(String source) {
		return toUpperCase(getNonAccentString(source));
	}

	public static String insert(String str, int pos, String strToInsert) {
		return str.substring(0, pos) + strToInsert + str.substring(pos);
	}

	public static String lpad(String str, int size, char c) {
		int currentSize = str == null ? 0 : str.length();
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < size - currentSize; i++) {
			builder.append(c);
		}
		if (isNotEmpty(str)) {
			builder.append(str);
		}
		return builder.substring(0, size).toString();
	}

	public static String rpad(String str, int size, char c) {
		int currentSize = str == null ? 0 : str.length();
		StringBuilder builder = new StringBuilder();
		if (isNotEmpty(str)) {
			builder.append(str);
		}
		for (int i = 0; i < size - currentSize; i++) {
			builder.append(c);
		}
		return builder.substring(0, size).toString();
	}

	public static char lastChar(String value) {
		if (isEmpty(value))
			return '\0';
		else
			return value.charAt(value.length() - 1);
	}

	public static String removeSpecialChar(String value) {
		if (isNotEmpty(value))
			return value.replaceAll("[^a-z^A-Z^0-9]", "");
		else
			return value;
	}

	public static boolean containsOnlyDigit(String value) {
		if (isNotEmpty(value))
			return value.matches("[\\d]+");
		else
			return false;
	}

	public static boolean containsOnlyAlphaNumeric(String value) {
		if (isNotEmpty(value))
			return value.matches("[\\d|\\w]+");
		else
			return false;
	}

	public static String removeWildCards(String value) {
		if (isNotEmpty(value))
			return value.replaceAll("[*%]", "");
		else
			return value;
	}

	public static String removeControlChar(String value) {
		if (isNotEmpty(value))
			return value.replaceAll("[\\n|\\r]", "").replaceAll("'", "\\\\'");
		else
			return value;
	}

	public static List<String> getTokens(String input, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		List<String> returnValue = new ArrayList<String>();
		if (matcher.groupCount() > 0) {
			while (matcher.find()) {
				returnValue.add(matcher.group().replaceAll("\"", ""));
			}
		}
		return returnValue;
	}

	public static final String toUpperCase(String source) {
		if (isNotEmpty(source))
			return source.toUpperCase();
		else
			return source;
	}

	public static final String toLowerCase(String source) {
		if (isNotEmpty(source))
			return source.toLowerCase();
		else
			return source;
	}

	public static String removeNonNumbers(String value) {
		return value != null ? value.replaceAll("\\D", "") : null;
	}

	public static String getSizedString(String value, int size) {
		if (size < 0)
			return value;
		if (value == null)
			return null;
		if (size == 0)
			return "";
		if (value.length() > size)
			return value.substring(0, size);
		else
			return value;
	}

	public static boolean isDifferent(String source, String target) {
		if (source == null) {
			return target == null;
		} else {
			if (target == null)
				return false;
			return !source.equals(target);
		}
	}

	public static boolean isEqual(String source, String target) {
		return !isDifferent(source, target);
	}

	public static String revert(String source) {
		if (isEmptyTrim(source))
			return source;

		int tamanho = source.length();
		char[] chSource = source.toCharArray();
		char[] tmpChar = new char[source.length()];
		for (int i = 0; i < tamanho; i++) {
			tmpChar[i] = chSource[tamanho - 1 - i];
		}
		return new String(tmpChar);
	}

	public static final String[] splitTrim(String source) {
		if (isEmptyTrim(source))
			return new String[0];
		String[] tokens = source.split("\\s{1,}");
		return tokens;
	}

	public static final String fullTrim(String source) {
		if (source == null || source.length() == 0)
			return source;
		else
			return revert(revert(source.trim()).trim());
	}

	public static boolean validEmail(String email) {
		if (email == null) {
			return true;
		}
		return email
				.matches("\\b(^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@([A-Za-z0-9-])+((\\.com)|(\\.net)|(\\.org)|(\\.info)|(\\.edu)|(\\.mil)|(\\.gov)|(\\.biz)|(\\.ws)|(\\.us)|(\\.tv)|(\\.cc)|(\\.aero)|(\\.arpa)|(\\.coop)|(\\.int)|(\\.jobs)|(\\.museum)|(\\.name)|(\\.pro)|(\\.travel)|(\\.nato)|(\\..{2,3})|(\\..{2,3}\\..{2,3}))$)\\b");
	}

	public static final int length(String str) {
		if (str != null)
			return str.length();
		else
			return 0;
	}

	public static final String removeHtmlTags(String source) {
		return removeHtmlTagsWithExceptions(source);
	}

	public static final String removeHtmlTagsWithExceptions(String source, String... leave) {
		if (isEmptyTrim(source))
			return "";
		String returnValue = "";
		String scriptregex = "<(script|style)[^>]*>[^<]*</(script|style)>";
		Pattern p1 = Pattern.compile(scriptregex, Pattern.CASE_INSENSITIVE);
		Matcher m1 = p1.matcher(source);
		int count = 0;
		while (m1.find()) {
			count++;
		}
		returnValue = m1.replaceAll("");
		String tagregex = "<[^>";
		if (SetUtil.nonEmpty(leave)) {
			for (String string : leave) {
				tagregex += "|^" + string;
			}
		}
		tagregex += "]*>";

		Pattern p2 = Pattern.compile(tagregex);
		Matcher m2 = p2.matcher(returnValue);
		count = 0;
		while (m2.find()) {
			count++;
		}

		// Replace any matches with nothing
		returnValue = m2.replaceAll("");

		String multiplenewlines = "(\\n{1,2})(\\s*\\n)+";
		return returnValue.replaceAll(multiplenewlines, "$1");
	}

	public static String firstUpperRestLower(String name) {
		if (isEmpty(name))
			return name;
		String returnValue = name.substring(0, 1).toUpperCase();
		if (name.length() > 1)
			returnValue += name.substring(1).toLowerCase();
		return returnValue;
	}

	public static String getHumanNameFromDBName(String name) {
		if (isEmpty(name))
			return name;
		String[] items = PATTERN_SPLITTER.split(name);
		StringBuilder returnValue = new StringBuilder();
		for (int i = 0; i < items.length; i++) {
			returnValue.append(firstUpperRestLower(items[i])).append(" ");
		}
		removeLast(returnValue);
		return returnValue.toString();
	}
	
	public static String getJavaNameFromHumanName(String name) {
		if (isEmpty(name))
			return name;
		Pattern dot = Pattern.compile("[.()-/]");
		String[] nameUndotted = dot.split(name);
		StringBuilder undotted = new StringBuilder();
		for(int i =0; i<nameUndotted.length; i++){
			undotted.append(nameUndotted[i]);
		}
		
		String[] items = PATTERN_SPLITTER.split(undotted.toString().toLowerCase());
		StringBuilder returnValue = new StringBuilder(items[0]);
		for (int i = 1; i < items.length; i++) {
			returnValue.append(firstUpperRestLower(items[i]));
		}
		return returnValue.toString();
	}

	public static String getHumanNameFromJavaName(String javaName) {
		if (isEmpty(javaName))
			return javaName;
		String mask = "([A-Z]+)";
		Pattern pattern = Pattern.compile(mask);
		Matcher matcher = pattern.matcher(javaName);
		StringBuilder builder = new StringBuilder();
		int start = 0;
		if (matcher.groupCount() > 0) {
			while (matcher.find()) {
				if (start != matcher.start()) {
					builder.append(firstUpper(javaName.substring(start, matcher.start())));
					start = matcher.start();
					builder.append(" ");
				}
			}
			builder.append(firstUpper(javaName.substring(start)));
		}
		return builder.toString();
	}

	public static String firstUpper(String name) {
		if (isEmpty(name))
			return name;
		String returnValue = name.substring(0, 1).toUpperCase();
		if (name.length() > 1)
			returnValue += name.substring(1);
		return returnValue;
	}
	
	public static String firstRemove(String name) {
		
		if (isEmpty(name))
			return name;
		
		String returnValue = "";
		
		if (name.length() > 1)
			returnValue += name.substring(1);
		
		return returnValue;
	}

	public static String firstLower(String name) {
		if (isEmpty(name))
			return name;
		String returnValue = name.substring(0, 1).toLowerCase();
		if (name.length() > 1)
			returnValue += name.substring(1);
		return returnValue;
	}

	public static String getString(Object value) {
		if (value == null)
			return null;
		return StringUtils.trimToNull((String) ConvertUtils.convert(value, String.class));
	}

	public static String getStringEmpty(Object value) {
		if (value == null)
			return EMPTY;
		return trimToEmpty((String) ConvertUtils.convert(value, String.class));
	}

	public static String trimToEmpty(String str) {
		return str == null ? EMPTY : str.trim();
	}

	public static String convertPackageToDir(String pack) {
		return pack.replaceAll("\\.", "/");
	}

	public static String convertDirToPackage(String path) {
		return path.replaceAll("\\\\|\\/", ".");
	}

	public static String convertFileSeparator(String path) {
		return path.replaceAll("\\\\|\\/", "\\" + File.separator);
	}

	public static final String[] extractPattern(String source, String regExp) {
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(source);
		List<String> returnValue = new ArrayList<String>();
		while (m.find()) {
			returnValue.add(m.group(1));
		}
		return returnValue.toArray(new String[0]);
	}

	public static Boolean isContains(String value, String content) {
		if (isEmpty(value))
			return false;
		return value.contains(content);
	}

	public static String convertNode(String value) {
		if (isEmpty(value))
			return EMPTY;
		return trimToEmpty(value).replace("&lt;", "<").replace("&gt;", ">").replace("<?xml version=\"1.0\" encoding=\"iso-8859-1\"?>", "").replace("&amp;", "").replace("amp;", "");
	}

	public static String convertInputStreamToString(InputStream is) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder builder = new StringBuilder();
		String line = null;

		while ((line = reader.readLine()) != null)
			builder.append(convertNode(line));

		is.close();
		return builder.toString();
	}

	public static String convertStringToInteger(String value) {
		Map<String, Integer> map = new LinkedHashMap<String, Integer>();
		StringBuilder builder = new StringBuilder();
		map.put(StringUtil.getString("."), 7);
		int i = 1;
		for (char ch = 'A'; ch <= 'Z'; ch++) {
			map.put(StringUtil.getString(Character.valueOf(ch)), i);
			i++;
			if (i == 10) {
				i = 0;
			}
		}
		for (int j = 0; j < value.length(); j++) {
			builder.append(map.get(StringUtil.getString(value.charAt(j))));
		}
		return builder.toString();
	}

	public static String removerChaves(String value) {
		if (isNotEmpty(value)) {
			StringBuilder builder = new StringBuilder();
			String split[] = value.replaceAll("[\\s{0-9}]", " ").split(" ");
			for (int i = 0; i < split.length; i++) {
				if (isNotEmpty(split[i])) {
					builder.append(split[i]);
					builder.append(" ");
				}
			}
			return trimToEmpty(builder.toString());
		}
		return null;
	}

	/**
	 * Verifica se a string passada é nula ou tem o valor null e substitui pela string de substituição
	 * 
	 * @param value
	 *            string passada para ser analisada
	 * @param replaceWith
	 *            string de substituição
	 * @return se a string for nula ou tiver o valor null substitui pela string de substituição caso contrario retorna a string passada com a aplicação de um trim
	 * @author florenciobueno-sao
	 */
	public static String NVL(String value, String replaceWith) {
		if (isEmpty(value)) {
			return replaceWith;
		}
		if ("null".equals(value.trim())) {
			return replaceWith;
		}
		return value.trim();
	}
	
	public static Map<String, String> asMap(String[] values, String separator) {
		Map<String, String> ret = new HashMap<String, String>();
		for (String item : values) {
			String[] itens = item.split(separator);
			
			ret.put(itens[0], itens[1]);
		}
		
		return ret;
	}
	
	/**
	 * @param number
	 * @param count
	 * @return
	 */
	public static String formatNumero(Integer number, int count) {		
		String num = getString(number);
		String mask = "0";
		
		for (int i = 0; i < count; i++) 
			mask += "0";
		
		
		String retorno = null;
		
		try {
			retorno = mask.substring(0, mask.length() - num.length()) + num;
		} catch (Exception e) {
			System.out.println(num);
			e.printStackTrace();
		}
		
		return retorno;
	}
	
	public static void main(String[] args) {
/*		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, 1995); 
		c.set(Calendar.MONTH, Calendar.MARCH); 
		c.set(Calendar.DAY_OF_MONTH, 20);
		
		System.out.println("Data/Hora atual: "+c.getTime());
		System.out.println("Ano: "+c.get(Calendar.YEAR));
		System.out.println("Mês: "+c.get(Calendar.MONTH));
		System.out.println("Dia do Mês: "+c.get(Calendar.DAY_OF_MONTH));*/
		
		//DateTimeUtil.formatReturnDates("08/2013", "yyyy/MM", -12);
		
		System.out.println(DateTimeUtil.formatReturnDate("08/2013", "yyyy/MM"));
	}
}
