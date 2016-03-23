package com.br.musa.entidades.Vo;

import java.io.Serializable;

import com.br.musa.entidades.Produto;

public class ProdutoVO implements Serializable{

	private static final long serialVersionUID = 6690312927916835680L;
	private Integer quantidadeProduto;
	private Produto produto;

	public Integer getQuantidadeProduto() {
		return quantidadeProduto;
	}

	public void setQuantidadeProduto(Integer quantidadeProduto) {
		this.quantidadeProduto = quantidadeProduto;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

}
