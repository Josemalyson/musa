package com.br.musa.enums;

public enum StatusPedidoEnum {
	PAGO(1L, "PAGO"), NAO_PAGO(2L, "NAO PAGO");

	private Long codigo;
	private String descricao;

	StatusPedidoEnum(Long codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Long getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

}