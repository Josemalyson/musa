package com.br.musa.enums;

public enum TipoPedidoEnum {
	ATACADO(1L, "ATACADO"), VAREJO(2L, "VAREJO");

	private Long codigo;
	private String descricao;

	TipoPedidoEnum(Long codigo, String descricao) {
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