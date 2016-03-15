package com.br.musa.servicos;

import javax.inject.Inject;

import com.br.musa.constantes.MsgConstantes;
import com.br.musa.entidades.Vo.ProdutoVO;
import com.br.musa.exeption.MusaExecao;
import com.br.musa.repositorio.PedidoRepositorio;

public class PedidoServico {

	@Inject
	private PedidoRepositorio pedidoRepositorio;
	
	public void validarQuantidadeDoProduto(ProdutoVO produtoVO){
		if (produtoVO != null && produtoVO.getQuantidadeProduto() != null && produtoVO.getQuantidadeProduto() <= 0) {
//			JavaScriptUtil.marcarCampoObrigatorio("tabelaProdutoVO:0:quantidadeId");
			throw new MusaExecao(MsgConstantes.ERRO_QUANTIDADE_ZERO);
		}
	}
	
	public Long obterNumerorDoProximoPedido(){
		return (Long) (pedidoRepositorio.obterNumeroDoProximoPedido() == null ? 1:pedidoRepositorio.obterNumeroDoProximoPedido()+1);
	}
}
