package com.br.musa.entidades.Vo;

import java.util.List;

import com.br.musa.entidades.Cliente;
import com.br.musa.entidades.Pedido;

public class PedidoVO {

	private Pedido pedido;
	private Cliente cliente;
	private List<ProdutoVO> produtoVOList;
	private Double totalPedio = new Double(0);
	private Double totalVenda = new Double(0);
	private Double totalCusto = new Double(0);

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

	public Double getTotalVenda() {
		return totalVenda;
	}

	public void setTotalVenda(Double totalVenda) {
		this.totalVenda = totalVenda;
	}

	public Double getTotalCusto() {
		return totalCusto;
	}

	public void setTotalCusto(Double totalCusto) {
		this.totalCusto = totalCusto;
	}

}
