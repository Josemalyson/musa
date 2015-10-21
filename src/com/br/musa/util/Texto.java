package com.br.musa.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.text.WordUtils;

public class Texto {
	private static final String[] VOGAIS_ACENTUADAS = { "á", "à", "â", "ã",
			"é", "ê", "í", "î", "ó", "ô", "õ", "ú", "û", "ü", "Á", "À", "Â",
			"Ã", "É", "Ê", "Í", "Î", "Ó", "Ô", "Õ", "Ú", "Û", "Ü" };

	private static final String[] VOGAIS_NAO_ACENTUADAS = { "a", "a", "a", "a",
			"e", "e", "i", "i", "o", "o", "o", "u", "u", "u", "A", "A", "A",
			"A", "E", "E", "I", "I", "O", "O", "O", "U", "U", "U" };
	private static final String ABRE_PARENTESES = "(";
	private static final String FECHA_PARENTESES = ")";
	private static final String ESPACO = " ";
	private static final String PONTO_ = ".";
	private static final String BARRA = "/";
	private static final String TRACO = "-";
	private static final String ESPACO_BRANCO = "";
	private static final String PONTO = "\\.";
	private static final String UNDER_LINE = "_";
	public static final String MD5 = "MD5";
	public static final String SHA1 = "SHA-1";

	public static byte[] gerarHash(String frase, String algoritmo) {
		try {
			MessageDigest md = MessageDigest.getInstance(algoritmo);
			md.update(frase.getBytes());
			return md.digest();
		} catch (NoSuchAlgorithmException e) {
		}
		return null;
	}

	public static String encripta(String texto, String algoritmo) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algoritmo);
			messageDigest.update(texto.getBytes());

			return stringHexa(messageDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new SecurityException(e.getMessage());
		}
	}

	private static String stringHexa(byte[] bytes) {
		StringBuilder s = new StringBuilder();

		for (int i = 0; i < bytes.length; i++) {
			int parteAlta = (bytes[i] >> 4 & 0xF) << 4;
			int parteBaixa = bytes[i] & 0xF;
			if (parteAlta == 0)
				s.append('0');
			s.append(Integer.toHexString(parteAlta | parteBaixa));
		}

		return s.toString();
	}

	public static byte[] converteStringHexadecimalEmBytes(
			String stringHexadecimal) {
		if ((stringHexadecimal == null) || (stringHexadecimal.equals(""))) {
			return new byte[0];
		}

		int tamanho = stringHexadecimal.length();
		if (tamanho % 2 != 0) {
			throw new NumberFormatException(
					"O tamanho da string hexadecimal está incorreto!");
		}
		int numeroDeBytes = tamanho / 2;
		byte[] seq = new byte[numeroDeBytes];
		for (int i = 0; i < numeroDeBytes; i++) {
			String hex = stringHexadecimal.substring(i * 2, i * 2 + 2);
			seq[i] = parseByte(hex);
		}
		return seq;
	}

	public static byte parseByte(String hex) throws NumberFormatException {
		if (hex == null) {
			throw new IllegalArgumentException(
					"A notação hexadecimal está nula.");
		}
		if (hex.equals("")) {
			return 0;
		}
		Integer num = Integer.decode(new StringBuilder().append("0x")
				.append(hex).toString());
		int n = num.intValue();
		if ((n > 255) || (n < 0)) {
			throw new NumberFormatException(new StringBuilder()
					.append(" O número ").append(n)
					.append(" não pode ser convertido em byte!").toString());
		}
		return num.byteValue();
	}

	public static String completaPosicoesEsquerda(String texto, char caracter,
			int tamanhoTotal) {
		return completaPosicoes(texto, caracter, tamanhoTotal,
				TipoDeCompletamento.ESQUERDA);
	}

	public static String completaPosicoesDireita(String texto, char caracter,
			int tamanhoTotal) {
		return completaPosicoes(texto, caracter, tamanhoTotal,
				TipoDeCompletamento.DIREITA);
	}

	private static String completaPosicoes(String texto, char caracter,
			int tamanhoTotal, TipoDeCompletamento tipo) {
		if ((texto == null) || (tamanhoTotal < 1)) {
			return texto;
		}
		String textoNovo = texto;
		while (textoNovo.length() < tamanhoTotal) {
			if (tipo == TipoDeCompletamento.ESQUERDA) {
				textoNovo = new StringBuilder().append(caracter)
						.append(textoNovo).toString();
				continue;
			}
			if (tipo == TipoDeCompletamento.DIREITA) {
				textoNovo = new StringBuilder().append(textoNovo)
						.append(caracter).toString();
			}
		}
		return textoNovo;
	}

	public static boolean equalsCanonico(String palavra1, String palavra2) {
		return palavra1.trim().equalsIgnoreCase(palavra2.trim());
	}

	public static String extraiNomeDaClasse(Class classe) {
		return extraiNomeDaClasse(classe.getName());
	}

	public static String extraiNomeDaClasse(String nomeDaClasse) {
		int posPonto = nomeDaClasse.lastIndexOf(".");
		if (posPonto >= 0) {
			return nomeDaClasse.substring(posPonto + 1);
		}
		return nomeDaClasse;
	}

	public static String extraiNomeDoPacote(String nomeDaClasse) {
		int posPonto = nomeDaClasse.lastIndexOf(".");
		if (posPonto >= 0) {
			return nomeDaClasse.substring(0, posPonto);
		}
		return nomeDaClasse;
	}

	public static String extraiNomeDoPacote(Class classe) {
		return extraiNomeDoPacote(classe.getName());
	}

	public static String retiraAcentos(String palavra) {
		String palavraAlterada = palavra;
		for (int i = 0; i < VOGAIS_ACENTUADAS.length; i++) {
			palavraAlterada = palavraAlterada.replaceAll(VOGAIS_ACENTUADAS[i],
					VOGAIS_NAO_ACENTUADAS[i]);
		}
		return palavraAlterada;
	}

	public static String retiraCaracteresEspeciais(String texto) {
		String novo = retiraAcentos(texto);
		novo = novo.replaceAll("ç", "c");
		novo = novo.replaceAll("Ç", "C");
		return novo;
	}

	public static String criaStringComPieces(Collection col, String separador) {
		return criaStringComPieces(col.iterator(), separador);
	}

	public static String criaStringComPieces(Iterator iter, String separador) {
		String html = "";
		while (iter.hasNext()) {
			if (html.length() > 0) {
				html = new StringBuilder().append(html).append(separador)
						.toString();
			}
			html = new StringBuilder().append(html)
					.append((String) iter.next()).toString();
		}
		return html;
	}

	public static String[] criaArrayDeString(Collection colecaoDeValores) {
		String[] array;
		if (colecaoDeValores.size() == 0)
			array = new String[1];
		else {
			array = new String[colecaoDeValores.size()];
		}
		Iterator iter = colecaoDeValores.iterator();
		for (int i = 0; i < colecaoDeValores.size(); i++) {
			array[i] = ((String) iter.next());
		}
		return array;
	}

	public static boolean isNumerico(String numero) {
		for (int i = 0; i < numero.length(); i++) {
			if (!Character.isDigit(numero.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static boolean isAlfaNumerico(String numero) {
		for (int i = 0; i < numero.length(); i++) {
			if ((!Character.isDigit(numero.charAt(i)))
					&& (!Character.isLetter(numero.charAt(i)))) {
				return false;
			}
		}
		return true;
	}

	public static boolean isLetra(char letra) {
		return Character.isLetter(letra);
	}

	public static String[] stringToArray(String linhasConcatenadas,
			String separador) {
		StringTokenizer to = new StringTokenizer(linhasConcatenadas, separador);
		int posicoes = to.countTokens();
		String[] linhas = new String[posicoes];
		for (int i = 0; i < posicoes; i++) {
			linhas[i] = to.nextToken();
		}
		return linhas;
	}

	public static String adiciona(String texto, String caracterParaAdicionar,
			int tamanhoMaximo) {
		StringBuffer sb = new StringBuffer(texto);
		if (sb.length() < tamanhoMaximo) {
			for (int i = texto.length(); i < tamanhoMaximo; i++) {
				sb.append(caracterParaAdicionar);
			}
			return sb.toString();
		}
		return texto;
	}

	public static String getPiece(String texto, String separador, int pos) {
		StringTokenizer token = new StringTokenizer(texto, separador);
		String pedaco = "";
		for (; (pos > 0) && (token.hasMoreTokens()); pos--) {
			pedaco = token.nextToken();
		}
		if (pos == 0) {
			return pedaco;
		}
		return "";
	}

	public static String trocaCaracter(String caracter, String texto) {
		StringBuilder retorno = new StringBuilder();
		for (int i = 0; i < texto.length(); i++) {
			if (texto.charAt(i) != caracter.charAt(0)) {
				retorno.append(texto.charAt(i));
			}
		}
		return retorno.toString();
	}

	public static String right(String texto, int numeroDeCaracteres) {
		if (numeroDeCaracteres > texto.length()) {
			return texto.toString();
		}
		return texto.substring(texto.length() - numeroDeCaracteres);
	}

	public static String left(String texto, int numeroDeCaracteres) {
		if (numeroDeCaracteres > texto.length()) {
			return texto.toString();
		}
		return texto.substring(0, numeroDeCaracteres);
	}

	public static boolean isUrl(String url) {
		String QUALQUER_CARACTER = "\\w+";
		String PONTO = "\\.";
		String INICIO = "((ftp|http|https|gopher|mailto|news|nntp|telnet|wais|file|prospero|aim|webcal)://)?";

		String FIM = "(\\.(\\w+)(.+))?";
		String EXPRESSAO = "((ftp|http|https|gopher|mailto|news|nntp|telnet|wais|file|prospero|aim|webcal)://)?\\w+\\.\\w+\\.\\w+(\\.(\\w+)(.+))?";

		Pattern pattern = Pattern
				.compile("((ftp|http|https|gopher|mailto|news|nntp|telnet|wais|file|prospero|aim|webcal)://)?\\w+\\.\\w+\\.\\w+(\\.(\\w+)(.+))?");
		Matcher matcher = pattern.matcher(url);
		return matcher.matches();
	}

	public static boolean isCep(String cep) {
		Pattern pattern = Pattern.compile("[0-9]{2}\\.[0-9]{3}-[0-9]{3}");
		Matcher matcher = pattern.matcher("58.102-400");
		return matcher.matches();
	}

	public static String fillField(String value, int sizeOfMask) {
		int sizeOfValue = value.substring(0, value.indexOf(46)).length();
		if (sizeOfValue > sizeOfMask) {
			return null;
		}
		boolean hasFourDigits = sizeOfValue == sizeOfMask;
		if (hasFourDigits) {
			return value.concat("0");
		}
		int difference = sizeOfMask - sizeOfValue;
		StringBuffer newValue = new StringBuffer(value);
		char append = '0';
		while (difference != 0) {
			newValue.insert(0, append);
			difference--;
		}
		return newValue.append('0').toString();
	}

	public static String limitaTamanhoString(String texto, int tamanho) {
		if ((texto != null) && (texto.length() > tamanho)) {
			texto = texto.substring(0, tamanho - 1);
		}

		return texto;
	}

	public static String manterNumeros(String str) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			if (Numeros.isNumber(String.valueOf(str.charAt(i)))) {
				s.append(str.charAt(i));
			}
		}
		return s.toString();
	}

	public static String incluirCaracterInicio(String texto, char c, int q) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < q; i++)
			s.append(c);
		s.append(texto);
		return s.toString();
	}

	public static String capitalizar(String texto, char[] delimitadores) {
		String capitalizado = WordUtils.capitalize(texto, delimitadores);
		return capitalizado;
	}

	public static String capitalizarCompletamente(String texto) {
		String capitalizado = WordUtils.capitalizeFully(texto);
		return capitalizado;
	}

	@Deprecated
	public static String capitalizarIniciais(String texto) {
		String[] split = texto.split(" ");
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < split.length; i++) {
			s.append(String.valueOf(split[i].charAt(0)).toUpperCase());
			s.append(split[i].substring(1, split[i].length()));
			s.append(" ");
		}
		return s.toString().trim();
	}

	public static int contarQuantidadePalavra(String texto, String palavra,
			boolean ignoreCase) {
		if (ignoreCase) {
			texto = texto.toLowerCase();
			palavra = palavra.toLowerCase();
		}
		Pattern padrao = Pattern.compile(palavra);
		Matcher pesquisa = padrao.matcher(texto);
		int r = 0;
		while (pesquisa.find())
			r++;
		return r;
	}

	public static char[] obterAlfabetoMinusculas() {
		return new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
				'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
				'x', 'y', 'w', 'z' };
	}

	public static char[] obterAlfabetoMaiusculas() {
		return new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
				'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
				'X', 'Y', 'W', 'Z' };
	}

	private static enum TipoDeCompletamento {
		DIREITA, ESQUERDA;
	}
}