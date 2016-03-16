package com.br.musa.entidades.Vo;

import java.util.List;

import com.br.musa.entidades.Cliente;
import com.br.musa.entidades.Pedido;

public class PedidoVO {

	private Pedido pedido;
	private Cliente cliente;
	private List<ProdutoVO> produtoVOList;
	private Double totalPedio = new Double(0);
	private String numeroPedido;

	public List<ProdutoVO> getProdutoVOList() {
		return produtoVOList;
	}

	public void setProdutoVOList(List<ProdutoVO> produtoVOList) {
		this.produtoVOList = produtoVOList;
	}

	public Double getTotalPedio() {
		return totalPedio;
	}

	public void setTotalPedio(Double totalPedio) {
		this.totalPedio = totalPedio;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public String getNumeroPedido() {
		return numeroPedido;
	}

	public void setNumeroPedido(String numeroPedido) {
		this.numeroPedido = numeroPedido;
	}

}
