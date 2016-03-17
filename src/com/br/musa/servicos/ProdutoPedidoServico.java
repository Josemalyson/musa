package com.br.musa.servicos;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import com.br.musa.entidades.ProdutoPedido;
import com.br.musa.repositorio.ProdutoPedidoRepositorio;

public class ProdutoPedidoServico {

	
	@Inject
	private ProdutoPedidoRepositorio produtoPedidoRepositorio;
	
	@Transactional
	public void salvarProdutoPedido(ProdutoPedido produtoPedido){
		produtoPedidoRepositorio.salvar(produtoPedido);
	}

	public List<ProdutoPedido> buscarPedidoPorProduto(Long codigoProdudo){
		return produtoPedidoRepositorio.buscarPedidoPorProduto(codigoProdudo);
		
	}
}
