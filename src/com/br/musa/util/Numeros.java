package com.br.musa.util;

import java.math.BigDecimal;

public final class Numeros {
	public static boolean isNumber(String numero) {
		try {
			return isBigDecimal(numero);
		} catch (RuntimeException exception) {
		}
		return false;
	}

	public static boolean isShort(String numero) {
		try {
			Short.valueOf(numero);
			return true;
		} catch (RuntimeException exception) {
		}
		return false;
	}

	public static boolean isInteger(String numero) {
		try {
			Long.valueOf(numero);
			return true;
		} catch (RuntimeException exception) {
		}
		return false;
	}

	public static boolean isDouble(String numero) {
		try {
			Double.valueOf(numero);
			return true;
		} catch (RuntimeException exception) {
		}
		return false;
	}

	public static boolean isFloat(String numero) {
		try {
			Float.valueOf(numero);
			return true;
		} catch (RuntimeException exception) {
		}
		return false;
	}

	public static boolean isBigDecimal(String numero) {
		try {
			new BigDecimal(numero);
			return true;
		} catch (RuntimeException exception) {
		}
		return false;
	}

	public static boolean isLong(String numero) {
		try {
			Long.valueOf(numero);
			return true;
		} catch (RuntimeException exception) {
		}
		return false;
	}

	public static String gerarDigitoVerificadorModulo11Base10(String num) {
		int soma = 0;
		int peso = 10;
		for (int i = 0; i < num.length(); i++)
			soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;
		Integer primDig;
		if (((soma % 11 == 0 ? 1 : 0) | (soma % 11 == 1 ? 1 : 0)) != 0)
			primDig = new Integer(0);
		else {
			primDig = new Integer(11 - soma % 11);
		}
		soma = 0;
		peso = 11;
		for (int i = 0; i < num.length(); i++) {
			soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;
		}
		soma += primDig.intValue() * 2;
		Integer segDig;
		if (((soma % 11 == 0 ? 1 : 0) | (soma % 11 == 1 ? 1 : 0)) != 0)
			segDig = new Integer(0);
		else {
			segDig = new Integer(11 - soma % 11);
		}
		return primDig.toString() + segDig.toString();
	}

	public static boolean isValidarDigitoVerificadorModulo11Base10(String cpf) {
		cpf = Texto.manterNumeros(cpf);
		if (cpf.length() != 11)
			return false;
		String numDig = cpf.substring(0, 9);
		return gerarDigitoVerificadorModulo11Base10(numDig).equals(
				cpf.substring(9, 11));
	}
}