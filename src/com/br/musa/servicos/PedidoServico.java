package com.br.musa.servicos;

import java.math.BigInteger;

import javax.inject.Inject;
import javax.transaction.Transactional;

import com.br.musa.constantes.MsgConstantes;
import com.br.musa.entidades.Pedido;
import com.br.musa.entidades.ProdutoPedido;
import com.br.musa.entidades.ProdutoPedidoPK;
import com.br.musa.entidades.Vo.PedidoVO;
import com.br.musa.entidades.Vo.ProdutoVO;
import com.br.musa.exeption.MusaExecao;
import com.br.musa.repositorio.PedidoRepositorio;

public class PedidoServico {

	@Inject
	private PedidoRepositorio pedidoRepositorio;
	@Inject
	private ProdutoPedidoServico produtoPedidoServico;
	@Inject
	private ClienteServico clienteServico;
	
	public void validarQuantidadeDoProduto(ProdutoVO produtoVO){
		if (produtoVO != null && produtoVO.getQuantidadeProduto() != null && produtoVO.getQuantidadeProduto() <= 0) {
//			JavaScriptUtil.marcarCampoObrigatorio("tabelaProdutoVO:0:quantidadeId");
			throw new MusaExecao(MsgConstantes.ERRO_QUANTIDADE_ZERO);
		}
	}
	
	public BigInteger obterNumerorDoProximoPedido(){
		return pedidoRepositorio.obterNumeroDoProximoPedido() == null ? new BigInteger("1") : pedidoRepositorio.obterNumeroDoProximoPedido().add(new BigInteger("1"));
	}

	@Transactional
	public void salvar(PedidoVO pedidoVO) {
		pedidoVO.getPedido().setCliente(clienteServico.buscarPorCodigo(pedidoVO.getCliente()));	
		Pedido pedidoBD = pedidoRepositorio.salvar(pedidoVO.getPedido());
		
		for (ProdutoVO produtoVO : pedidoVO.getProdutoVOList()) {
			ProdutoPedido produtoPedido = new ProdutoPedido();
			ProdutoPedidoPK id = new ProdutoPedidoPK(produtoVO.getProduto().getId(), pedidoBD.getId());
			produtoPedido.setId(id);
			produtoPedidoServico.salvarProdutoPedido(produtoPedido);
		}
		
	}
	
}
