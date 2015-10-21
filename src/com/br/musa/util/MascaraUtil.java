package com.br.musa.util;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.MaskFormatter;

public abstract class MascaraUtil {
	public static final String CNPJ = "##.###.###/####-##";
	public static final String CPF = "###.###.###-##";
	public static final String TELEFONE = "(##) ####-####";
	public static final String CEP = "#####-###";

	public static String adicionarMascara(String valor, String mascara)	throws ParseException {
		if (!(ObjetoUtil.isBlank(valor).booleanValue())) {
			MaskFormatter mf = new MaskFormatter(mascara);
			mf.setValueContainsLiteralCharacters(false);
			valor = mf.valueToString(valor);
		}
		return valor;
	}

	public static String removerMascara(String valor) {
		Pattern numericos = Pattern.compile("([0-9])");
		Matcher encaixe = numericos.matcher(valor);
		StringBuffer saida = new StringBuffer();
		while (encaixe.find()) {
			saida.append(encaixe.group());
		}
		return saida.toString();
	}
}