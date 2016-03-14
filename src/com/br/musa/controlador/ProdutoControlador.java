package com.br.musa.controlador;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.br.musa.constantes.MsgConstantes;
import com.br.musa.entidades.Produto;
import com.br.musa.exeption.MusaExecao;
import com.br.musa.servicos.ProdutoServico;

@ManagedBean
@ViewScoped
public class ProdutoControlador extends CoreControlador {

	private static final long serialVersionUID = -4092451941917851066L;
	private static final Logger logger = Logger.getLogger(ProdutoControlador.class);

	// OBJETOS
	private Produto produto;
	
	//SERVICOS
	@Inject
	private ProdutoServico produtoServico;

	@PostConstruct
	public void init() {
		produto = new Produto(); 

	}

	public void salvarProduto(){
		try {
			produto.setFlExcluido(false);
			produtoServico.salvarProduto(produto);
		} catch (MusaExecao e) {
			logger.error(e.getMessage(), e);
			adicionarErro(e.getMessage());
			return;
		}
		produto = new Produto();
		adicionarMensagem(MsgConstantes.MSG_SUCESSO);
	}
	
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

}