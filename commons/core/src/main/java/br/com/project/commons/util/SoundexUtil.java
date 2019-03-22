package br.com.project.commons.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class SoundexUtil {
	private static final String accentConversionString = "áéíóúÁÉÍÓÚâêîôûÂÊÎÔÛãõñÃÕÑàèìòùÀÈÌÒÙäëïöüÄËÏÖÜçÇyYqQnNxXzZ";
	private static final String nonAccentConversionString = "aeiouAEIOUaeiouAEIOUaonAONaeiouAEIOUaeiouAEIOUsSiIkKmMsSsS";
	private static final Map<Character, Character> accentConversionMap;
	private static final List<FoneticRule> foneticRuleList;

	static {
		accentConversionMap = new HashMap<Character, Character>(accentConversionString.length());
		for (int i = 0; i < accentConversionString.length(); i++) {
			accentConversionMap.put(accentConversionString.charAt(i), nonAccentConversionString.charAt(i));
		}

		foneticRuleList = new ArrayList<FoneticRule>();

		// composite rules, must not reaply on the same sequence inputed here

		foneticRuleList.add(new FoneticRule("MG", "G", false, true));
		foneticRuleList.add(new FoneticRule("RG", "G", false, true));
		foneticRuleList.add(new FoneticRule("CH", "S", false, true));
		foneticRuleList.add(new FoneticRule("ST", "T", false, true));
		foneticRuleList.add(new FoneticRule("RS", "S", false, true));

		foneticRuleList.add(new FoneticRule("SHOW", "SHOU"));
		foneticRuleList.add(new FoneticRule("SCH", "X"));

		foneticRuleList.add(new FoneticRule("W", "V", false, true));

		foneticRuleList.add(new FoneticRule("BR", "B"));
		foneticRuleList.add(new FoneticRule("BL", "B"));

		foneticRuleList.add(new FoneticRule("PH", "F"));

		foneticRuleList.add(new FoneticRule("GR", "G"));
		foneticRuleList.add(new FoneticRule("GL", "G"));

		foneticRuleList.add(new FoneticRule("NG", "G"));

		foneticRuleList.add(new FoneticRule("GE", "J"));
		foneticRuleList.add(new FoneticRule("GI", "J"));
		foneticRuleList.add(new FoneticRule("RJ", "J"));
		foneticRuleList.add(new FoneticRule("MJ", "J"));
		foneticRuleList.add(new FoneticRule("NJ", "J"));

		foneticRuleList.add(new FoneticRule("CA", "K"));
		foneticRuleList.add(new FoneticRule("CO", "K"));
		foneticRuleList.add(new FoneticRule("CU", "K"));
		foneticRuleList.add(new FoneticRule("CK", "K"));

		foneticRuleList.add(new FoneticRule("LH", "L"));

		// foneticRuleList.add(new FoneticRule("SM", "M"));

		foneticRuleList.add(new FoneticRule("RM", "SM"));

		foneticRuleList.add(new FoneticRule("NH", "N"));
		foneticRuleList.add(new FoneticRule("GM", "M"));
		foneticRuleList.add(new FoneticRule("MD", "M"));

		foneticRuleList.add(new FoneticRule("PR", "P"));

		foneticRuleList.add(new FoneticRule("CE", "S"));
		foneticRuleList.add(new FoneticRule("CI", "S"));
		foneticRuleList.add(new FoneticRule("CS", "S"));

		foneticRuleList.add(new FoneticRule("TS", "S"));

		foneticRuleList.add(new FoneticRule("LT", "T"));
		foneticRuleList.add(new FoneticRule("TL", "T"));
		foneticRuleList.add(new FoneticRule("TR", "T"));
		foneticRuleList.add(new FoneticRule("CT", "T"));
		foneticRuleList.add(new FoneticRule("RT", "T"));
		foneticRuleList.add(new FoneticRule("CT", "T"));

		// final L by R
		foneticRuleList.add(new FoneticRule("L", "R"));

		// remove terminations
		foneticRuleList.add(new FoneticRule("S", "", true));
		foneticRuleList.add(new FoneticRule("Z", "", true));
		foneticRuleList.add(new FoneticRule("R", "", true));
		foneticRuleList.add(new FoneticRule("M", "", true));
		foneticRuleList.add(new FoneticRule("N", "", true));
		foneticRuleList.add(new FoneticRule("AO", "", true));
		foneticRuleList.add(new FoneticRule("L", "", true));

	}

	public static String encode(String source) {
		if (StringUtil.isEmpty(source))
			return source;
		String nonAccent = applySimpleRules(source);
		Pattern patSplitter = Pattern.compile("[^a-z^A-Z^0-9]");
		String[] items = patSplitter.split(nonAccent);
		StringBuilder returnValue = new StringBuilder();
		for (int i = 0; i < items.length; i++) {
			if (StringUtil.isNotEmptyTrim(items[i])) {
				returnValue.append(applyFoneticRules(items[i]));
			}
		}

		return returnValue.toString();
	}

	private static String applySimpleRules(String source) {
		char[] modified = source.toUpperCase().toCharArray();
		for (int i = 0; i < modified.length; i++) {
			Character replacementCh = accentConversionMap.get(modified[i]);
			if (replacementCh != null) {
				modified[i] = replacementCh.charValue();
			}
		}
		return String.valueOf(modified);
	}

	private static String applyFoneticRules(String source) {
		String foneticString = source;

		for (FoneticRule fr : FoneticRule.getListEndOfExpression(foneticRuleList, false)) {
			String mark = "##";
			if (fr.allowRecursiveReplacement) {
				mark = "";
			}
			foneticString = foneticString.replaceAll(fr.getRegEx(), mark + fr.getReplacement() + mark);
		}
		foneticString = foneticString.replaceAll("##", "");

		for (FoneticRule fr : FoneticRule.getListEndOfExpression(foneticRuleList, true)) {
			foneticString = foneticString.replaceAll(fr.getRegEx(), fr.getReplacement());
		}

		foneticString = removeConsecutiveDuplicateAndVowels(foneticString);
		return foneticString;
	}

	private static String removeConsecutiveDuplicateAndVowels(String source) {
		return source.replaceAll("(A|E|I|O|U|H)", "").replaceAll("([a-zA-Z])(?=\\1)", "");

	}

}

class FoneticRule {
	String silab;
	String replacement;
	boolean endOfExpression = false;
	boolean allowRecursiveReplacement = false;

	public boolean isEndOfExpression() {
		return endOfExpression;
	}

	public void setEndOfExpression(boolean endOfExpression) {
		this.endOfExpression = endOfExpression;
	}

	public String getSilab() {
		return silab;
	}

	public void setSilab(String silab) {
		this.silab = silab;
	}

	public String getReplacement() {
		return replacement;
	}

	public void setReplacement(String replacement) {
		this.replacement = replacement;
	}

	public FoneticRule(String silab, String replacement) {
		super();
		this.silab = silab;
		this.replacement = replacement;
	}

	public FoneticRule(String silab, String replacement, boolean endOfExpression) {
		this(silab, replacement);
		this.endOfExpression = endOfExpression;
	}

	public FoneticRule(String silab, String replacement, boolean endOfExpression, boolean allowRecursiveReplacement) {
		this(silab, replacement, endOfExpression);
		this.allowRecursiveReplacement = allowRecursiveReplacement;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (allowRecursiveReplacement ? 1231 : 1237);
		result = prime * result + (endOfExpression ? 1231 : 1237);
		result = prime * result + ((replacement == null) ? 0 : replacement.hashCode());
		result = prime * result + ((silab == null) ? 0 : silab.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final FoneticRule other = (FoneticRule) obj;
		if (allowRecursiveReplacement != other.allowRecursiveReplacement)
			return false;
		if (endOfExpression != other.endOfExpression)
			return false;
		if (replacement == null) {
			if (other.replacement != null)
				return false;
		} else if (!replacement.equals(other.replacement))
			return false;
		if (silab == null) {
			if (other.silab != null)
				return false;
		} else if (!silab.equals(other.silab))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "silaba=" + silab + " rep=" + replacement + " final=" + endOfExpression;
	}

	public String getRegEx() {
		if (endOfExpression) {
			return getSilab() + "$";
		} else {
			return getSilab() + "";
		}
	}

	public static List<FoneticRule> getListEndOfExpression(List<FoneticRule> source, boolean isEnd) {
		List<FoneticRule> returnValue = new ArrayList<FoneticRule>();
		for (FoneticRule foneticRule : source) {
			if (foneticRule.isEndOfExpression() == isEnd)
				returnValue.add(foneticRule);
		}

		return returnValue;
	}

}
