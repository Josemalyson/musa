package com.br.musa.entidades.Vo;

import com.br.musa.entidades.Produto;

public class ProdutoVO {

	private Long quantidadeProduto;
	private Produto produto;

	public Long getQuantidadeProduto() {
		return quantidadeProduto;
	}

	public void setQuantidadeProduto(Long quantidadeProduto) {
		this.quantidadeProduto = quantidadeProduto;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

}
