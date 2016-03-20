package com.br.musa.util;

import java.math.BigDecimal;

import com.br.musa.constantes.Constantes;
import com.br.musa.constantes.MsgConstantes;
import com.br.musa.exeption.CalculadoraExecao;
import com.br.musa.interfaces.Calculadora;

public final class CalcularUtil implements Calculadora {

	public static BigDecimal calcularDesconto(BigDecimal total, BigDecimal desconto) {
		if (isDescontoValido(total)) {
			throw new CalculadoraExecao(MsgConstantes.VALOR_ZERO_NAO_PODE_SER_DESCONTADO);
		} else {
			return total.subtract(total.multiply(desconto.divide(new BigDecimal(100))));

		}
	}

	private static boolean isDescontoValido(BigDecimal total) {
		return total == null || total.equals(Constantes.ZERO) || total.intValue() <= 0;
	}

}
