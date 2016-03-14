package com.br.musa.util;

import com.br.musa.exeption.MusaExecao;

public class CpfUtil {

	public static String gerar() {
		StringBuilder iniciais = new StringBuilder();

		for (int i = 0; i < 9; i++) {
			Integer numero = Integer.valueOf((int) (Math.random() * 10.0D));
			iniciais.append(numero.toString());
		}
		return new StringBuilder().append(iniciais.toString())
				.append(gerarDigitoVerificador(iniciais.toString())).toString();
	}

	public static String formatar(String cpf) throws Exception {
		String cpfSoNumeros = limpar(cpf);

		if (isValido(cpfSoNumeros)) {
			throw new Exception(
					new StringBuilder()
							.append("CPF inv�lido. Tamanho de um CPF v�lido � 11. Este CPF possui ")
							.append(cpfSoNumeros.length()).append(" n�meros.")
							.toString());
		}
		return MascaraUtil.adicionarMascara(cpfSoNumeros, "###.###.###-##");
	}

	public static boolean isValido(String cpf) {
		cpf = MascaraUtil.removerMascara(cpf);

		int soma = 0;
		int resto = 0;

		if ((cpf.length() != 11) || (cpf.equals("00000000000"))
				|| (cpf.equals("11111111111")) || (cpf.equals("22222222222"))
				|| (cpf.equals("33333333333")) || (cpf.equals("44444444444"))
				|| (cpf.equals("55555555555")) || (cpf.equals("66666666666"))
				|| (cpf.equals("77777777777")) || (cpf.equals("88888888888"))
				|| (cpf.equals("99999999999"))) {
			return false;
		}

		for (int i = 0; i < 9; i++) {
			soma += Integer.parseInt(Character.toString(cpf.charAt(i)))
					* (10 - i);
		}

		resto = 11 - soma % 11;

		if ((resto == 10) || (resto == 11)) {
			resto = 0;
		}
		if (resto != Integer.parseInt(Character.toString(cpf.charAt(9)))) {
			return false;
		}

		soma = 0;
		for (int i = 0; i < 10; i++) {
			soma += Integer.parseInt(Character.toString(cpf.charAt(i)))
					* (11 - i);
		}

		resto = 11 - soma % 11;

		if ((resto == 10) || (resto == 11)) {
			resto = 0;
		}

		return resto == Integer.parseInt(Character.toString(cpf.charAt(10)));
	}

	public static String limpar(String cpf) {
		if (cpf == null)
			throw new MusaExecao("O CPF informado � nulo.");
		if ("".equals(cpf))
			throw new MusaExecao("O CPF informado � vazio.");
		char[] chars = cpf.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (int indice = 0; indice < chars.length; indice++) {
			if (Numeros.isInteger(String.valueOf(chars[indice]))) {
				sb.append(chars[indice]);
			}
		}
		return sb.toString();
	}

	public static String gerarDigitoVerificador(String num) {
		return Numeros.gerarDigitoVerificadorModulo11Base10(num);
	}
}
