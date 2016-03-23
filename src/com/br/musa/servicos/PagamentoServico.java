package com.br.musa.servicos;

import java.util.List;

import javax.inject.Inject;

import com.br.musa.entidades.Pagamento;
import com.br.musa.repositorio.PagamentoRepositorio;

public class PagamentoServico {

	
	@Inject
	private PagamentoRepositorio pagamentoRepositorio;
	
	
	public List<Pagamento> listarPagamentoPorPedido(Long idPedido){
		return pagamentoRepositorio.listarPagamentoPorPedido(idPedido);
	}
}
