package com.br.musa.servicos;

import javax.inject.Inject;

import com.br.musa.constantes.Constantes;
import com.br.musa.entidades.Produto;
import com.br.musa.exeption.MusaExecao;
import com.br.musa.repositorio.ProdutoRepositorio;
import com.br.musa.util.JavaScriptUtil;

public class ProdutoServico {

	@Inject
	private ProdutoRepositorio produtoRepositorio;
	
	public void salvarProduto(Produto produto){
		validarProdutoParaSalvar(produto);
		produtoRepositorio.salvar(produto);
	}

	private void validarProdutoParaSalvar(Produto produto) {
		
		if (produto != null) {
			StringBuilder erro = new StringBuilder();

			if (produto.getDescricaoProduto() == null || produto.getDescricaoProduto().isEmpty()) {
				erro.append("Preencha o campo Descrição do Produto.").append(Constantes.TAG_BR);
				JavaScriptUtil.marcarCampoObrigatorio("descricao");
			}
			
			if (produto.getPrecoCusto() == null) {
				erro.append("Preencha o campo Preço de Custo.").append(Constantes.TAG_BR);
				JavaScriptUtil.marcarCampoObrigatorio("precoCusto");
			}

			if (produto.getPrecoVenda() == null) {
				erro.append("Preencha o campo Preço de Venda.").append(Constantes.TAG_BR);
				JavaScriptUtil.marcarCampoObrigatorio("precoVenda");
			}
			
			if (!erro.toString().isEmpty()) {
				throw new MusaExecao(erro.toString());
			}
		}
		
	}
	
}