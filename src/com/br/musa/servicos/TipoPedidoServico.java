package com.br.musa.servicos;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import com.br.musa.entidades.TipoPedido;
import com.br.musa.enums.TipoPedidoEnum;
import com.br.musa.repositorio.TipoPedidoRepositorio;

public class TipoPedidoServico {

	
	@Inject
	private TipoPedidoRepositorio tipoPedidoRepositorio;
	
	public List<TipoPedido> listar(){
		return tipoPedidoRepositorio.listar();
	}
	
	@Transactional
	public void salvarTipoPedido(TipoPedido tipoPedido){
		tipoPedidoRepositorio.salvar(tipoPedido);
	}
	
	public TipoPedido buscarPorCodigo(TipoPedidoEnum pedidoEnum){
		return tipoPedidoRepositorio.buscarPorCodigo(pedidoEnum.getCodigo());
	}
}
