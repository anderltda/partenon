package br.com.project.commons.util;

import java.util.HashSet;
import java.util.Set;

public class ValidatorUtil {
	private static final Set<String> CPF_BLACKLIST;

	static {
		CPF_BLACKLIST = new HashSet<String>();
		CPF_BLACKLIST.add("12345678909");
		CPF_BLACKLIST.add("01234567890");
	}

	public static final int[] gerarDigitoCPF(final String cpf) {
		String cpfSize9 = cpf.replaceAll("\\D", "");
		if (cpf.length() != 9) {
			cpfSize9 = StringUtil.lpad(cpf, 9, '0');
		}
		char[] chCPF = cpfSize9.toCharArray();
		int dv1, dv2;
		int totalDv1 = 0, totalDv2 = 0, pesoDv1 = 10, pesoDv2 = 11;
		for (int i = 0; i < chCPF.length; i++) {
			totalDv1 += Character.getNumericValue(chCPF[i]) * pesoDv1--;
			totalDv2 += Character.getNumericValue(chCPF[i]) * pesoDv2--;
		}
		int resto = totalDv1 % 11;
		if (resto <= 1)
			dv1 = 0;
		else dv1 = 11 - resto;

		totalDv2 += dv1 * 2;

		resto = totalDv2 % 11;
		if (resto <= 1)
			dv2 = 0;
		else dv2 = 11 - resto;

		int[] returnValue = { dv1, dv2 };
		return returnValue;
	}

	public static final boolean verificarCPF(String cpf) {
		if (StringUtil.isEmpty(cpf)) return false;
		if (cpf.matches("(\\d)\\1{10}")) return false;

		String cpfSize11 = cpf.replaceAll("\\D", "");
		if (cpfSize11.length() != 11) {
			return false;
		}

		if (CPF_BLACKLIST.contains(cpf)) return false;
		int[] dvGerado = gerarDigitoCPF(cpfSize11.substring(0, 9));
		int[] dvExistente = { Character.getNumericValue(cpfSize11.charAt(9)), Character.getNumericValue(cpfSize11.charAt(10)) };

		if (dvGerado[0] != dvExistente[0] || dvGerado[1] != dvExistente[1])
			return false;
		else return true;
	}

	public static final boolean verificarCPF(Long cpf) {
		if (NumberUtil.longValue(cpf) <= 0)
			return false;
		else return verificarCPF(StringUtil.lpad(cpf.toString(), 11, '0'));
	}

	public static final int[] gerarDigitoCNPJ(final String cpf) {
		String cnpfSize12 = cpf.replaceAll("\\D", "");
		if (cpf.length() != 12) {
			cnpfSize12 = StringUtil.lpad(cpf, 12, '0');
		}
		char[] chCNPJ = cnpfSize12.toCharArray();
		int dv1, dv2;
		int totalDv1 = 0, pesoDv1 = 5;
		for (int i = 0; i < 4; i++) {
			totalDv1 += Character.getNumericValue(chCNPJ[i]) * pesoDv1--;
		}
		pesoDv1 = 9;
		for (int i = 4; i < chCNPJ.length; i++) {
			totalDv1 += Character.getNumericValue(chCNPJ[i]) * pesoDv1--;
		}

		int resto = totalDv1 % 11;
		if (resto <= 1)
			dv1 = 0;
		else dv1 = 11 - resto;

		chCNPJ = (cnpfSize12 + dv1).toCharArray();
		int totalDv2 = 0, pesoDv2 = 6;
		for (int i = 0; i < 5; i++) {
			totalDv2 += Character.getNumericValue(chCNPJ[i]) * pesoDv2--;
		}
		pesoDv2 = 9;
		for (int i = 5; i < chCNPJ.length; i++) {
			totalDv2 += Character.getNumericValue(chCNPJ[i]) * pesoDv2--;
		}

		resto = totalDv2 % 11;
		if (resto <= 1)
			dv2 = 0;
		else dv2 = 11 - resto;
		int[] returnValue = { dv1, dv2 };
		return returnValue;
	}

	public static final boolean verificarCNPJ(String cnpj) {
		if (StringUtil.isEmpty(cnpj)) return false;
		if (cnpj.matches("(\\d)\\1{13}")) return false;

		String cnpjsize14 = cnpj.replaceAll("\\D", "");
		if (cnpjsize14.length() != 14) {
			return false;
		}
		int[] dvGerado = gerarDigitoCNPJ(cnpjsize14.substring(0, 12));
		int[] dvExistente = { Character.getNumericValue(cnpjsize14.charAt(12)), Character.getNumericValue(cnpjsize14.charAt(13)) };
		if (dvGerado[0] != dvExistente[0] || dvGerado[1] != dvExistente[1])
			return false;
		else return true;
	}

	public static final boolean verificarCNPJ(Long cnpj) {
		if (NumberUtil.longValue(cnpj) <= 0)
			return false;
		else return verificarCNPJ(StringUtil.lpad(cnpj.toString(), 14, '0'));
	}
	
	public static final boolean verificarCNPJCPF(String cnpjCpf) {
		if (verificarCNPJ(cnpjCpf)) {
			return true;
		} else if (verificarCPF(cnpjCpf)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static String gerarDigitoVerificadorAgencia(Long numeroAgencia, Long numeroBanco) {
		String returnValue = null;
		if (numeroAgencia == null || numeroBanco == null) return null;
		switch (numeroBanco.intValue()) {
		case 1: // banco do Brasil
			returnValue = gerarDigitoAgenciaBanco001(numeroAgencia, numeroBanco);
			break;
		case 151: // Banco Nossa Caixa S.A.
			returnValue = gerarDigitoAgenciaBanco151(numeroAgencia, numeroBanco);
			break;
		case 237: // bradesco
			returnValue = gerarDigitoAgenciaBanco237(numeroAgencia, numeroBanco);
			break;

		default:
			break;
		}
		return returnValue;
	}

	private static final String gerarDigitoAgenciaBanco001(Long numeroAgencia, Long numeroBanco) {
		return gerarDigitoGenericoAgencia(numeroAgencia, 10, 11, "X");
	}

	private static final String gerarDigitoAgenciaBanco151(Long numeroAgencia, Long numeroBanco) {
		String dv = "";
		char[] chAgencia = StringUtil.lpad("" + numeroAgencia, 4, '0').toCharArray();
		int peso = 2;
		int tot = 0;

		for (int i = chAgencia.length - 1; i >= 0; i--) {
			tot += Character.getNumericValue(chAgencia[i]) * peso;
			peso++;
		}

		int resto = tot % 11;
		switch (resto) {
		case 0:
			dv = "1";
			break;
		case 1:
			dv = "0";
			break;
		default:
			dv = String.valueOf(11 - resto);
			break;
		}

		return dv;
	}

	private static final String gerarDigitoAgenciaBanco237(Long numeroAgencia, Long numeroBanco) {
		return gerarDigitoGenericoAgencia(numeroAgencia, 10, 11, "0");
	}

	public static final String gerarDigitoGenericoAgencia(String numero, final int peso, final int modulo, String restoPeso) {
		String dv = null;
		char[] chAgencia = numero.toCharArray();
		int tot = 0;
		int tmpPeso = peso - 1;
		for (int i = chAgencia.length - 1; i >= 0; i--) {
			tot += Character.getNumericValue(chAgencia[i]) * tmpPeso;
			if (tmpPeso == 2)
				tmpPeso = peso - 1;
			else tmpPeso--;
		}
		int resto = tot % modulo;
		if (resto == peso)
			dv = restoPeso;
		else dv = String.valueOf(resto);
		return dv;
	}

	public static final String gerarDigitoGenericoAgencia(Long numero, final int peso, final int modulo, String restoPeso) {
		return gerarDigitoGenericoAgencia(numero.toString(), peso, modulo, restoPeso);
	}

	public static final boolean verificarDVContaCorrente(Long numeroBanco, Long numeroAgencia, String contaCorrente, String dvContaCorrente, String digitoAgencia) {
		if (numeroAgencia == null || numeroBanco == null) return true;
		if (StringUtil.isEmpty(contaCorrente)) return false;

		char dvExistente = dvContaCorrente.charAt(dvContaCorrente.length() - 1);
		char dvGerado;
		switch (numeroBanco.intValue()) {
		case 1: // banco do Brasil
			dvGerado = gerarDigitoCCBanco001(numeroAgencia, numeroBanco, contaCorrente).charAt(0);
			break;
		case 237: // bradesco
			dvGerado = gerarDigitoCCBanco237(numeroAgencia, numeroBanco, contaCorrente).charAt(0);
			break;
		case 341: // itau
			dvGerado = gerarDigitoCCBanco341(numeroAgencia, numeroBanco, contaCorrente).charAt(0);
			break;
		case 151: // nossa caixa
			dvGerado = gerarDigitoCCBanco151(numeroAgencia, numeroBanco, contaCorrente, digitoAgencia).charAt(0);
			break;
		case 33: // banespa
			dvGerado = gerarDigitoCCBanco33(numeroAgencia, numeroBanco, contaCorrente).charAt(0);
			break;

		default:
			return true;
		}
		return dvGerado == dvExistente;
	}

	public static final String gerarDVContaCorrente(Long numeroBanco, Long numeroAgencia, String contaCorrente, String digitoAgencia) {
		if (numeroAgencia == null || numeroBanco == null) return "";
		if (StringUtil.isEmpty(contaCorrente)) return "";

		switch (numeroBanco.intValue()) {
		case 1: // banco do Brasil
			return gerarDigitoCCBanco001(numeroAgencia, numeroBanco, contaCorrente);
		case 237: // bradesco
			return gerarDigitoCCBanco237(numeroAgencia, numeroBanco, contaCorrente);
		case 341: // itau
			return gerarDigitoCCBanco341(numeroAgencia, numeroBanco, contaCorrente);
		case 151: // nossa caixa
			return gerarDigitoCCBanco151(numeroAgencia, numeroBanco, contaCorrente, digitoAgencia);
		case 33: // banespa
			return gerarDigitoCCBanco33(numeroAgencia, numeroBanco, contaCorrente);
		default:
			return "";
		}
	}

	private static String gerarDigitoCCBanco33(Long numeroAgencia, Long numeroBanco, String contaCorrente) {
		int[] peso = { 9, 7, 3, 1, 9, 7, 1, 3, 1, 9, 7, 3 };
		char[] chConta = (StringUtil.lpad("" + numeroAgencia, 5, '0').substring(1, 5) + StringUtil.lpad("" + contaCorrente, 8, '0')).toCharArray();
		int tot = 0;
		for (int i = 0; i < chConta.length; i++) {
			tot += Character.getNumericValue(chConta[i]) * peso[i];
		}
		int dv = Character.getNumericValue(StringUtil.lastChar(String.valueOf(tot)));
		if (dv != 0) dv = 10 - dv;
		return String.valueOf(dv);
	}

	private static String gerarDigitoCCBanco151(Long numeroAgencia, Long numeroBanco, String contaCorrente, String digitoAgencia) {
		char[] chConta = (StringUtil.lpad("" + numeroAgencia + digitoAgencia, 5, '0').substring(0, 4) + StringUtil.lpad("" + contaCorrente, 8, '0')).toCharArray();
		int total = 0, peso = 2;
		for (int i = chConta.length - 1; i >= 0; i--) {
			total += Character.getNumericValue(chConta[i]) * peso;
			if (peso == 7)
				peso = 2;
			else peso++;
		}
		int resto = total % 11;
		switch (resto) {
		case 0:
			return "1";
		case 1:
			return "0";
		default:
			return String.valueOf(11 - resto);
		}
	}

	private static String gerarDigitoCCBanco001(Long numeroAgencia, Long numeroBanco, String contaCorrente) {
		Long contaCorrentLong = NumberUtil.getLong(contaCorrente);
		return gerarDigitoGenericoAgencia(contaCorrentLong, 10, 11, "X");
	}

	private static String gerarDigitoCCBanco237(Long numeroAgencia, Long numeroBanco, String contaCorrente) {
		Long contaCorrentLong = NumberUtil.getLong(contaCorrente);
		return gerarDigitoGenericoAgencia(contaCorrentLong, 10, 11, "0");
	}

	private static String gerarDigitoCCBanco341(Long numeroAgencia, Long numeroBanco, String contaCorrente) {
		int[] peso = { 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2 };
		char[] chConta = (StringUtil.lpad("" + numeroAgencia, 4, '0') + StringUtil.lpad("" + contaCorrente, 5, '0')).toCharArray();
		int tot = 0;
		int valor = 0;
		for (int i = 0; i < chConta.length; i++) {
			valor = Character.getNumericValue(chConta[i]) * peso[i];
			char[] tmpValor = StringUtil.lpad("" + valor, 2, '0').toCharArray();
			tot += Character.getNumericValue(tmpValor[0]) + Character.getNumericValue(tmpValor[1]);
		}
		int resto = tot % 10;
		if (resto != 0) resto = 10 - resto;
		return "" + resto;
	}

	public static String modulo11(String numero) {
		if (StringUtil.isEmptyTrim(numero)) {
			return null;
		}
		int total = 0;
		for ( int i = numero.length() - 1, peso = 2; i >= 0; i-- ) {
			total += peso * ( numero.charAt( i ) - '0' );
			if ( ++peso > 9 ) {
				peso = 2;
			}
		}
		total = 11 - ( total % 11 );
		return total > 9 ? "0" : ""+total;
	}
	
	public static boolean validadorNumeroDanfe(String numDanfe) {
		/*
		 * 35120402099642000115550010000452851007057900
		 * 35 - Código do ibge
		 * 12 - Ano de emissão 
		 * 04 - Mês de emissão 
		 * 02099642000115 - Cnpj do cliente emissor
		 * 55 - Modelo da nfe
		 * 001 - Série
		 * 000045285 - Número da nfe
		 * 100705790 - Sequêncial 
		 * 0 - Digito verificador
		 * */

		Boolean result = false;
		
		if (numDanfe != null && numDanfe.trim().length() == 44) {
			numDanfe = numDanfe.trim();
			try {
				Integer ibge = NumberUtil.getInteger(numDanfe.substring(0, 2));
				Integer mes = NumberUtil.getInteger(numDanfe.substring(4, 6));
				String cnpj = numDanfe.substring(6, 20);
				String modelo = numDanfe.substring(20, 22);

				if (!validaIbge(ibge)) {
					result = false;
				} else if (!validaMes(mes)) {
					result = false;
				} else if (!verificarCNPJCPF(cnpj)) {
					result = false;
				} else if (!"55".equals(modelo)) {
					result = false;
				} else if (!numDanfe.substring(43).equals(ValidatorUtil.modulo11(numDanfe.substring(0, 43))) ) {
					result = false;
				} else {
					result = true;
				}
				
			} catch (Exception e) {
				result = false;
			}
		}
		return result;
	}
	
	public static boolean validaMes(Integer mes) {
		if (mes >= 1 && mes <= 12) {
			return true;
		}
		return false;
	}
	
	public static boolean validaIbge(Integer codigoIbge) {
		Integer ibge[] = {11,12,13,14,15,16,17,21,22,23,24,25,26,27,28,29,31,32,33,35,41,42,43,50,51,52,53};
		for (int i = 0; i < ibge.length; i++) {
			if (codigoIbge.equals(ibge[i])) {
				return true;
			}
		}
		return false;
	}
}