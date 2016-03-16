package com.br.musa.servicos;

import java.util.List;

import javax.inject.Inject;

import com.br.musa.entidades.TipoPedido;
import com.br.musa.repositorio.TipoPedidoRepositorio;

public class TipoPedidoServico {

	
	@Inject
	TipoPedidoRepositorio tipoPedidoRepositorio;
	
	public List<TipoPedido> listar(){
		return tipoPedidoRepositorio.listar();
	}
}
