package com.br.musa.enums;

import java.math.BigDecimal;

public enum DescontoEnum {
	DESCONTO_0(new BigDecimal(0), "0%"),
	DESCONTO_10(new BigDecimal(10), "10%"),
	DESCONTO_20(new BigDecimal(20), "20%"),
	DESCONTO_30(new BigDecimal(30), "30%"),
	DESCONTO_40(new BigDecimal(40), "40%"),
	DESCONTO_50(new BigDecimal(50), "50%"),
	DESCONTO_60(new BigDecimal(60), "60%"),
	DESCONTO_70(new BigDecimal(70), "70%"),
	DESCONTO_80(new BigDecimal(80), "80%"),
	DESCONTO_90(new BigDecimal(90), "90%"),
	DESCONTO_100(new BigDecimal(100), "100%");

	private BigDecimal codigo;
	private String descricao;

	DescontoEnum(BigDecimal codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public BigDecimal getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

}