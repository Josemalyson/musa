package com.br.musa.servicos;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import com.br.musa.entidades.ProdutoPedido;
import com.br.musa.repositorio.ProdutoPedidoRepositorio;

public class ProdutoPedidoServico implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1130196879582890093L;
	@Inject
	private ProdutoPedidoRepositorio produtoPedidoRepositorio;
	
	@Transactional
	public void salvarProdutoPedido(ProdutoPedido produtoPedido){
		produtoPedidoRepositorio.salvar(produtoPedido);
	}

	public List<ProdutoPedido> buscarPedidoPorProduto(Long codigoProdudo){
		return produtoPedidoRepositorio.buscarPedidoPorProduto(codigoProdudo);
		
	}

	public ProdutoPedido buscarPedidoPorPedido(Long codigoPedido, Long codigoProdudo){
		return produtoPedidoRepositorio.buscarPedidoPorPedido(codigoPedido, codigoProdudo);
		
	}
}
