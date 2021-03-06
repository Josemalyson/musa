package com.br.musa.servicos;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import com.br.musa.constantes.Constantes;
import com.br.musa.entidades.Produto;
import com.br.musa.exeption.MusaExecao;
import com.br.musa.repositorio.ProdutoRepositorio;
import com.br.musa.util.JavaScriptUtil;

public class ProdutoServico implements Serializable {

	private static final long serialVersionUID = 2296788064359833138L;
	@Inject
	private ProdutoRepositorio produtoRepositorio;
	@Inject
	private ProdutoPedidoServico produtoPedidoServico;

	@Transactional
	public void salvarProduto(Produto produto) {
		validarProdutoParaSalvar(produto);
		produtoRepositorio.salvar(produto);
	}

	private void validarProdutoParaSalvar(Produto produto) {

		if (produto != null) {
			StringBuilder erro = new StringBuilder();

			if (produto.getDescricaoProduto() == null || produto.getDescricaoProduto().isEmpty()) {
				erro.append("Preencha o campo Descri��o do Produto.").append(Constantes.TAG_BR);
				JavaScriptUtil.marcarCampoObrigatorio("produto");
			}

			if (produto.getPrecoCusto() == null) {
				erro.append("Preencha o campo Pre�o de Custo.").append(Constantes.TAG_BR);
				JavaScriptUtil.marcarCampoObrigatorio("precoCusto");
			}

			if (produto.getPrecoVenda() == null) {
				erro.append("Preencha o campo Pre�o de Venda.").append(Constantes.TAG_BR);
				JavaScriptUtil.marcarCampoObrigatorio("precoVenda");
			}

			if (!erro.toString().isEmpty()) {
				throw new MusaExecao(erro.toString());
			}
		}

	}

	public List<Produto> listar() {
		List<Produto> produtoList = produtoRepositorio.listar();
		ordenarListaProdutos(produtoList);
		return produtoList;
	}

	@Transactional
	public void excluirProduto(Produto produto) {

		if (produtoPedidoServico.buscarPedidoPorProduto(produto.getId()) == null) {
			produtoRepositorio.excluir(produto);
		} else {
			produto.setFlExcluido(true);
			salvarProduto(produto);
		}
	}

	public List<Produto> listarProdutosAtivos() {
		return produtoRepositorio.listarProdutosAtivos();
	}

	private void ordenarListaProdutos(List<Produto> produtoList) {
		produtoList.sort((p1, p2) -> p1.getDescricaoProduto().compareToIgnoreCase(p2.getDescricaoProduto()));
	}

	public List<Produto> listarProdutosPorPedido(Long id) {
		return produtoRepositorio.listarProdutosPorPededido(id);
	}

}