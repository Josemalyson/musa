package com.br.musa.entidades.Vo;

import java.util.List;

import com.br.musa.entidades.Cliente;
import com.br.musa.entidades.Pedido;

public class PedidoVO {

	private Pedido pedido;
	private Cliente cliente;
	private List<ProdutoVO> produtoVOList;
	private String numeroPedido;

	public List<ProdutoVO> getProdutoVOList() {
		return produtoVOList;
	}

	public void setProdutoVOList(List<ProdutoVO> produtoVOList) {
		this.produtoVOList = produtoVOList;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pedido == null) ? 0 : pedido.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PedidoVO other = (PedidoVO) obj;
		if (pedido == null) {
			if (other.pedido != null)
				return false;
		} else if (!pedido.equals(other.pedido))
			return false;
		return true;
	}

}
