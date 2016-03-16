package com.br.musa.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProdutoPedidoPK implements Serializable {

	private static final long serialVersionUID = 8876718548753648439L;

	@Column(name = "FK_PRODUTO")
	private Long idProduto;

	@Column(name = "FK_PEDIDO")
	private Long idPedido;

	public ProdutoPedidoPK() {
	}
	
	public ProdutoPedidoPK(Long idProduto, Long idPedido) {
		this.idProduto = idProduto;
		this.idPedido = idPedido;
	}

	public Long getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Long idProduto) {
		this.idProduto = idProduto;
	}

	public Long getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}

}
