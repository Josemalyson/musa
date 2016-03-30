package com.br.musa.servicos;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.br.musa.entidades.StatusPedido;
import com.br.musa.enums.StatusPedidoEnum;
import com.br.musa.repositorio.StatusPedidoRepositorio;

public class StatusPedidoServico implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3463970945787030100L;
	@Inject
	private StatusPedidoRepositorio statusPesdidoRepositorio;

	public List<StatusPedido> listar() {
		return statusPesdidoRepositorio.listar();
	}

	public StatusPedido buscarPorCodigo(StatusPedidoEnum naoPago) {
		return statusPesdidoRepositorio.buscarPorCodigo(naoPago.getCodigo());
	}

	
}