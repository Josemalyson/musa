package com.br.musa.controlador;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;
import org.primefaces.context.RequestContext;

import com.br.musa.constantes.Constantes;
import com.br.musa.constantes.MsgConstantes;
import com.br.musa.entidades.Cliente;
import com.br.musa.entidades.Pedido;
import com.br.musa.entidades.Produto;
import com.br.musa.entidades.StatusPedido;
import com.br.musa.entidades.TipoPedido;
import com.br.musa.entidades.vo.PedidoVO;
import com.br.musa.entidades.vo.ProdutoVO;
import com.br.musa.exeption.MusaExecao;
import com.br.musa.servicos.ClienteServico;
import com.br.musa.servicos.PedidoServico;
import com.br.musa.servicos.ProdutoServico;
import com.br.musa.servicos.StatusPedidoServico;
import com.br.musa.servicos.TipoPedidoServico;

@ManagedBean
@ViewScoped
public class PedidoControlador extends CoreControlador {
	private static final long serialVersionUID = 4425238557135997397L;

	// SERVICOS
	@Inject
	private ClienteServico clienteServico;
	@Inject
	private ProdutoServico produtoServico;
	@Inject
	private PedidoServico pedidoServico;
	@Inject
	private TipoPedidoServico tipoPedidoServico;
	@Inject
	private StatusPedidoServico statusPedidoServico;

	// OBJETOS
	private Cliente cliente;
	private Pedido pedido;
	private Produto produto;
	private PedidoVO pedidoVO;

	// FLAGS
	private boolean flBotaoAdicionarPedido;
	private boolean fltabelaPedido;
	private boolean flbotaoSalvar;
	private boolean flTipoPedido;
	private boolean flStatusPedido;
	private boolean flAutoCompleteCliente;
	private boolean flDesconto;
	private boolean flBotaoEditarAutocomplete;

	// LISTA
	private List<Cliente> clienteList;
	private List<Produto> produtoList;
	private List<Produto> produtoListPedido;
	private List<TipoPedido> tipoPedidoList;
	private List<StatusPedido> statusPedidoList;
	private List<BigDecimal> listDesconto;

	private static final Logger logger = Logger.getLogger(PedidoControlador.class);

	@PostConstruct
	public void init() {

		pedido = (Pedido) obterAtributoFlash("pedido");

		if (pedido == null) {
			cliente = new Cliente();
			produto = new Produto();
			pedido = new Pedido();
			pedido.setDtPedido(new Date());
			pedido.setFlExcluido(false);
		}

		listarTipoPedido();
		listarStatusPedido();
		listarProdutosAtivos();
		listarCliente();
		montarPedido();
		montarListaDesconto();
		isPedidoNovoOuEditado();

	}

	private void isPedidoNovoOuEditado() {
		if (pedido.getId() == null) {
			habilitarTodosOsCamposParaEdicao();
			flDesconto = true;
		} else {
			desabilitarTodosOsCampos();
		}

	}

	private void montarListaDesconto() {
		listDesconto = pedidoServico.montarListaDesconto();
	}

	private void habilitarTodosOsCamposParaEdicao() {
		flBotaoAdicionarPedido = false;
		fltabelaPedido = false;
		flbotaoSalvar = false;
		flTipoPedido = false;
		flAutoCompleteCliente = false;
		flDesconto = false;
		flBotaoEditarAutocomplete = false;
	}

	private void desabilitarTodosOsCampos() {
		fltabelaPedido = true;
		flBotaoAdicionarPedido = true;
		flbotaoSalvar = true;
		flTipoPedido = true;
		flStatusPedido = true;
		flAutoCompleteCliente = true;
		flDesconto = true;
		flBotaoEditarAutocomplete = true;
	}

	private void verificarSeExisteProdutosCadastrados() {
		try {
			pedidoServico.verificarSeExisteProdutosCadastrados();
		} catch (MusaExecao e) {
			desabilitarTodosOsCampos();
			RequestContext.getCurrentInstance().update("pedido");
			logger.error(e.getMessage(), e);
			adicionarErro(e.getMessage());
			return;
		}
	}

	private void listarTipoPedido() {
		tipoPedidoList = tipoPedidoServico.listar();
	}

	private void listarStatusPedido() {
		statusPedidoList = statusPedidoServico.listar();
	}

	private void montarPedido() {
		pedidoVO = pedidoServico.montarPedidoNovoEdicao(pedido, cliente);
	}

	private void listarProdutosAtivos() {
		produtoList = new ArrayList<>();

		if (pedido.getId() == null) {
			produtoList = produtoServico.listarProdutosAtivos();
		}else {
			produtoList = produtoServico.listar();
		}
		
	}

	private void listarCliente() {
		clienteList = new ArrayList<>();
		try {
			clienteList = clienteServico.listarTodosClientes().get();
		} catch (InterruptedException | ExecutionException e) {
			logger.info(" Erro na execução do método assícrono " + e.getMessage(), e);
			adicionarErro(MsgConstantes.ERRO_NO_PROCESSAMENTO);
		}

	}

	public List<Cliente> autoCompleteCliente(String query) {
		return clienteServico.autoCompleteClienteServico(query, clienteList);
	}

	public void atualizarCliente() {
		if (this.pedidoVO.getCliente().getId() != null) {
			this.pedidoVO.setCliente(clienteServico.buscarPorCodigo(this.pedidoVO.getCliente()));
			try {
				this.pedidoVO.getCliente().setCpf(clienteServico.adicionarMascaraCpf(this.pedidoVO.getCliente()).get());
			} catch (InterruptedException | ExecutionException e) {
				logger.info(" Erro na execução do método assícrono " + e.getMessage(), e);
				adicionarErro(MsgConstantes.ERRO_NO_PROCESSAMENTO);
				return;
			}
		} else {
			this.pedidoVO.setCliente(new Cliente());
		}

		RequestContext.getCurrentInstance().update("cpf");
	}

	public void adicionarProduto() {
		verificarSeExisteProdutosCadastrados();
		pedidoServico.adicionarProduto(pedidoVO, produto);
		flDesconto = false;;
	}

	public void calcularTotal() {
		pedidoServico.calcularTotal(pedidoVO);
	}

	public void calcularDesconto() {
		try {
			pedidoServico.calcularDesconto(pedidoVO);
		} catch (MusaExecao e) {
			logger.error(e.getMessage(), e);
			adicionarErro(e.getMessage());
		}

	}

	public void ajustarTela(ProdutoVO produtoVO) {

		try {
			pedidoServico.validarQuantidadeDoProduto(produtoVO);
			calcularTotal();
			flBotaoAdicionarPedido = false;
			RequestContext.getCurrentInstance().update("tabelaProdutoVO");
			RequestContext.getCurrentInstance().update("btAdicionarProduto");
		} catch (MusaExecao e) {
			logger.error(e.getMessage(), e);
			adicionarErro(e.getMessage());
			return;
		}
	}

	public String salvarPedido() {
		try {
			verificarSeExisteProdutosCadastrados();
			pedidoServico.salvar(pedidoVO);
			adicionarMensagem(MsgConstantes.MSG_SUCESSO);
			return sendRedirect(Constantes.PAGINA_LISTAR_PEDIDOS);
		} catch (MusaExecao e) {
			logger.error(e.getMessage(), e);
			adicionarErro(e.getMessage());
			return Constantes.STRING_VAZIA;
		}
	}

	public boolean isTipoPedidoAtacado() {
		return !pedidoServico.isTipoPedidoAtacado(pedidoVO);
	}

	public void limparCliente() {
		pedidoVO.setCliente(new Cliente());
	}

	public List<Cliente> getClienteList() {
		return clienteList;
	}

	public void setClienteList(List<Cliente> clienteList) {
		this.clienteList = clienteList;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;

	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
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

	public PedidoVO getPedidoVO() {
		return pedidoVO;
	}

	public void setPedidoVO(PedidoVO pedidoVO) {
		this.pedidoVO = pedidoVO;
	}

	public List<Produto> getProdutoListPedido() {
		return produtoListPedido;
	}

	public void setProdutoListPedido(List<Produto> produtoListPedido) {
		this.produtoListPedido = produtoListPedido;
	}

	public boolean isFlBotaoAdicionarPedido() {
		return flBotaoAdicionarPedido;
	}

	public void setFlBotaoAdicionarPedido(boolean flBotaoAdicionarPedido) {
		this.flBotaoAdicionarPedido = flBotaoAdicionarPedido;
	}

	public List<TipoPedido> getTipoPedidoList() {
		return tipoPedidoList;
	}

	public void setTipoPedidoList(List<TipoPedido> tipoPedidoList) {
		this.tipoPedidoList = tipoPedidoList;
	}

	public List<StatusPedido> getStatusPedidoList() {
		return statusPedidoList;
	}

	public void setStatusPedidoList(List<StatusPedido> statusPedidoList) {
		this.statusPedidoList = statusPedidoList;
	}

	public boolean isFltabelaPedido() {
		return fltabelaPedido;
	}

	public void setFltabelaPedido(boolean fltabelaPedido) {
		this.fltabelaPedido = fltabelaPedido;
	}

	public boolean isFlbotaoSalvar() {
		return flbotaoSalvar;
	}

	public void setFlbotaoSalvar(boolean flbotaoSalvar) {
		this.flbotaoSalvar = flbotaoSalvar;
	}

	public boolean isFlTipoPedido() {
		return flTipoPedido;
	}

	public void setFlTipoPedido(boolean flTipoPedido) {
		this.flTipoPedido = flTipoPedido;
	}

	public boolean isFlStatusPedido() {
		return flStatusPedido;
	}

	public void setFlStatusPedido(boolean flStatusPedido) {
		this.flStatusPedido = flStatusPedido;
	}

	public boolean isFlAutoCompleteCliente() {
		return flAutoCompleteCliente;
	}

	public void setFlAutoCompleteCliente(boolean flAutoCompleteCliente) {
		this.flAutoCompleteCliente = flAutoCompleteCliente;
	}

	public List<BigDecimal> getListDesconto() {
		return listDesconto;
	}

	public void setListDesconto(List<BigDecimal> listDesconto) {
		this.listDesconto = listDesconto;
	}

	public boolean isFlDesconto() {
		return flDesconto;
	}

	public void setFlDesconto(boolean flDesconto) {
		this.flDesconto = flDesconto;
	}

	public boolean isFlBotaoEditarAutocomplete() {
		return flBotaoEditarAutocomplete;
	}

	public void setFlBotaoEditarAutocomplete(boolean flBotaoEditarAutocomplete) {
		this.flBotaoEditarAutocomplete = flBotaoEditarAutocomplete;
	}

}