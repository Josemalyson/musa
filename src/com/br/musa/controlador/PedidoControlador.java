package com.br.musa.controlador;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;
import org.primefaces.context.RequestContext;

import com.br.musa.constantes.MsgConstantes;
import com.br.musa.entidades.Cliente;
import com.br.musa.entidades.Pedido;
import com.br.musa.entidades.Produto;
import com.br.musa.entidades.TipoPedido;
import com.br.musa.entidades.Vo.PedidoVO;
import com.br.musa.entidades.Vo.ProdutoVO;
import com.br.musa.exeption.MusaExecao;
import com.br.musa.servicos.ClienteServico;
import com.br.musa.servicos.PedidoServico;
import com.br.musa.servicos.ProdutoServico;
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

	// OBJETOS
	private Cliente cliente;
	private Pedido pedido;
	private Produto produto;
	private PedidoVO pedidoVO;
	private boolean flBotaoAdicionarPedido;

	// LISTA
	private List<Cliente> clienteList;
	private List<Produto> produtoList;
	private List<Produto> produtoListPedido;
	private List<TipoPedido> tipoPedidoList;

	private static final Logger logger = Logger.getLogger(PedidoControlador.class);

	@PostConstruct
	public void init() {
		listarCliente();
		cliente = new Cliente();
		produto = new Produto();
		pedido = new Pedido();
		pedido.setDtPedido(new Date());
		pedido.setFlExcluido(false);
		flBotaoAdicionarPedido = false;
		listarProdutosAtivos();
		montarPedido();
		listarTipoPedido();

	}

	private void listarTipoPedido() {
		tipoPedidoList = tipoPedidoServico.listar();
	}

	private void montarPedido() {
		pedidoVO = new PedidoVO();
		pedidoVO.setCliente(cliente);
		pedidoVO.setNumeroPedido(pedidoServico.obterNumerorDoProximoPedido().toString());
		pedidoVO.setPedido(pedido);
		pedidoVO.setProdutoVOList(new ArrayList<ProdutoVO>());

	}

	private void listarProdutosAtivos() {
		produtoList = new ArrayList<Produto>();
		produtoList = produtoServico.listarProdutosAtivos();
	}

	private void listarCliente() {
		clienteList = new ArrayList<Cliente>();
		clienteList = clienteServico.listarTodosClientes();

	}

	public List<Cliente> autoCompleteCliente(String query) {
		List<Cliente> clienteFiltradosList = new ArrayList<Cliente>();

		for (int i = 0; i < clienteList.size(); i++) {
			Cliente cliente = clienteList.get(i);
			if (cliente.getNome().toLowerCase().startsWith(query)) {
				clienteFiltradosList.add(cliente);
			}
		}

		return clienteFiltradosList;
	}

	public void atualizarCliente() {
		if (this.pedidoVO.getCliente().getId() != null) {
			this.pedidoVO.setCliente(clienteServico.buscarPorCodigo(this.pedidoVO.getCliente()));
			this.pedidoVO.getCliente().setCpf(clienteServico.adicionarMascaraCpf(this.pedidoVO.getCliente()));
		} else {
			this.pedidoVO.setCliente(new Cliente());
		}
	}

	public void adicionarProduto() {

		if (pedidoVO != null && pedidoVO.getProdutoVOList() != null) {
			ProdutoVO produtoVO = new ProdutoVO();
			produtoVO.setQuantidadeProduto(new Long(1));
			getProduto().setPrecoCusto(new BigDecimal(0));
			getProduto().setPrecoVenda(new BigDecimal(0));
			produtoVO.setProduto(getProduto());
			pedidoVO.getProdutoVOList().add(0, produtoVO);
		}
		calcularTotal();
	}

	public void calcularTotal() {
		calcularTotalCusto();
		calcularTotalVenda();
	}

	private void calcularTotalCusto() {
		pedidoVO.getPedido().setNuTotalCusto(new BigDecimal(pedidoVO.getProdutoVOList().stream()
				.mapToDouble(p -> p.getQuantidadeProduto() * (p.getProduto().getPrecoCusto()).doubleValue()).sum()));
	}

	private void calcularTotalVenda() {
		pedidoVO.getPedido().setNuTotalVenda(new BigDecimal(pedidoVO.getProdutoVOList().stream()
				.mapToDouble(p -> p.getQuantidadeProduto() * (p.getProduto().getPrecoVenda()).doubleValue()).sum()));
	}

	public void ajustarTela(ProdutoVO produtoVO) {

		try {
			pedidoServico.validarQuantidadeDoProduto(produtoVO);
			if (produtoVO.getQuantidadeProduto() == null) {
				produtoVO.setQuantidadeProduto(new Long(1));
			}
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

	public void salvarPedido() {
		try {
			pedidoServico.salvar(pedidoVO);
			adicionarMensagem(MsgConstantes.MSG_SUCESSO);
		} catch (MusaExecao e) {
			logger.error(e.getMessage(), e);
			adicionarErro(e.getMessage());
			return;
		}
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

}