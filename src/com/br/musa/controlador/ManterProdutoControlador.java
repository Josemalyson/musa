package com.br.musa.controlador;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;
import org.primefaces.context.RequestContext;

import com.br.musa.constantes.MsgConstantes;
import com.br.musa.entidades.Produto;
import com.br.musa.exeption.MusaExecao;
import com.br.musa.servicos.ProdutoServico;

@ManagedBean
@ViewScoped
public class ManterProdutoControlador extends CoreControlador {

	private static final long serialVersionUID = -4092451941917851066L;
	private static final Logger logger = Logger.getLogger(ManterProdutoControlador.class);

	// OBJETOS
	private Produto produto;
	private List<Produto> produtoList;

	// FLAG
	private boolean flPrecoCusto;
	private boolean flPrecoVenda;

	// SERVICOS
	@Inject
	private ProdutoServico produtoServico;

	@PostConstruct
	public void init() {
		produto = new Produto();
		listarProdutos();
		habilitarFlags();
	}

	public void habilitarFlags() {
		flPrecoCusto = false;
		flPrecoVenda = false;
	}

	public void desabilitarFlags() {
		flPrecoCusto = true;
		flPrecoVenda = true;
	}

	private void listarProdutos() {
		produtoList = produtoServico.listarProdutosAtivos();
	}

	public void inicializarObjeto() {
		produto = new Produto();
		habilitarFlags();
		RequestContext.getCurrentInstance().update("incluirProduto");
	}

	public void selecionarProduto(Produto produtoSelecionado) {
		this.produto = produtoSelecionado;
		
		if (produto.getId() == null) {
			habilitarFlags();
		}else {
			desabilitarFlags();
		}
	}

	public void salvarProduto() {
		try {
			produto.setFlExcluido(false);
			produtoServico.salvarProduto(produto);
			inicializarObjeto();
			listarProdutos();
			RequestContext.getCurrentInstance().update("tabelaProduto");
			RequestContext.getCurrentInstance().execute("PF('incluirProduto').hide();");
			adicionarMensagem(MsgConstantes.MSG_SUCESSO);
		} catch (MusaExecao e) {
			logger.error(e.getMessage(), e);
			adicionarErro(e.getMessage());
			return;
		}
	}

	public void excluirProduto() {
		try {
			produtoServico.excluirProduto(produto);
			inicializarObjeto();
			listarProdutos();
			adicionarMensagem(MsgConstantes.MSG_SUCESSO);
		} catch (MusaExecao e) {
			logger.error(e.getMessage(), e);
			adicionarErro(e.getMessage());
			return;
		}
	}

	public List<Produto> getProdutoList() {
		return produtoList;
	}

	public void setProdutoList(List<Produto> produtoList) {
		this.produtoList = produtoList;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public boolean isFlPrecoCusto() {
		return flPrecoCusto;
	}

	public void setFlPrecoCusto(boolean flPrecoCusto) {
		this.flPrecoCusto = flPrecoCusto;
	}

	public boolean isFlPrecoVenda() {
		return flPrecoVenda;
	}

	public void setFlPrecoVenda(boolean flPrecoVenda) {
		this.flPrecoVenda = flPrecoVenda;
	}

}