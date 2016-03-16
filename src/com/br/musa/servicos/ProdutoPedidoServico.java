package com.br.musa.servicos;

import javax.inject.Inject;
import javax.transaction.Transactional;

import com.br.musa.entidades.ProdutoPedido;
import com.br.musa.repositorio.ProdutoRepositorio;

public class ProdutoPedidoServico {

	
	@Inject
	private ProdutoRepositorio produtoRepositorio;
	
	@Transactional
	public void salvarProdutoPedido(ProdutoPedido produtoPedido){
		produtoRepositorio.salvar(produtoPedido);
	}
	
}
