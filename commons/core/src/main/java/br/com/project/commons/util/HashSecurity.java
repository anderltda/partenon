package br.com.project.commons.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class HashSecurity {

	/**
	 * @param hash
	 * @return
	 */
	public static String generateOTP(String hash) {
		Long pass = System.currentTimeMillis() / 1000 / 15;
		for(int i = 0; i < hash.length(); i ++) {
			char c = hash.charAt(i);
			if (c < 65) {
				pass = pass * c;
			}
		}
		return criptografar(pass.toString());
	}

	/**
	 * @param text
	 * @return
	 */
	private static char[] hexCodes(byte[] text) {

		char[] hexOutput = new char[text.length * 2];

		String hexString;

		for (int i = 0; i < text.length; i++) {
			hexString = "A6Z" + Integer.toHexString(text[i]);
			hexString.toUpperCase().getChars(hexString.length() - 2, hexString.length(), hexOutput, i * 2);
		}

		return hexOutput;
	}

	/**
	 * @param pwd
	 * @return
	 */
	public static String criptografar(String pwd) {

		try {	

			MessageDigest md = MessageDigest.getInstance("MD5");

			if (md != null) {
				return new String(hexCodes(md.digest(pwd.getBytes())));
			}

		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}		
		return null;
	}


	/**
	 * @param number
	 * @return
	 */
	public static String geradorNumerosAleatorios(Integer number) {

		try {	

			Random gerador = new Random(); 
			return StringUtil.getString(gerador.nextInt(number)); 

		} catch (Exception ex) {
			ex.printStackTrace();
		}		
		return null;
	}


}
