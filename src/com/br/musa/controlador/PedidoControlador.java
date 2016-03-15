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

import com.br.musa.entidades.Cliente;
import com.br.musa.entidades.Pedido;
import com.br.musa.entidades.Produto;
import com.br.musa.entidades.ProdutoPedido;
import com.br.musa.entidades.Vo.PedidoVO;
import com.br.musa.entidades.Vo.ProdutoVO;
import com.br.musa.servicos.ClienteServico;
import com.br.musa.servicos.ProdutoServico;

@ManagedBean
@ViewScoped
public class PedidoControlador extends CoreControlador {
	private static final long serialVersionUID = 4425238557135997397L;

	// SERVICOS
	@Inject
	private ClienteServico clienteServico;

	@Inject
	private ProdutoServico produtoServico;

	// OBJETOS
	private Cliente cliente;
	private Pedido pedido;
	private Produto produto;
	private PedidoVO pedidoVO;
	private BigDecimal totalCusto; 

	// LISTA
	private List<Cliente> clienteList;
	private List<Produto> produtoList;
	private List<Produto> produtoListPedido;
	private List<ProdutoPedido> produtoPedidoList;

	private static final Logger logger = Logger.getLogger(PedidoControlador.class);

	@PostConstruct
	public void init() {
		listarCliente();
		cliente = new Cliente();
		produto = new Produto();
		pedido = new Pedido();
		pedido.setDtPedido(new Date());
		listarProdutosAtivos();
		produtoPedidoList = new ArrayList<ProdutoPedido>();
		totalPrecoDeCusto();
		montarPedido();

	}

	private void montarPedido() {
		pedidoVO = new PedidoVO();
		pedidoVO.setCliente(cliente);
		pedidoVO.setPedido(pedido);

		List<ProdutoVO> produtoVOList = new ArrayList<ProdutoVO>();
		produtoVOList.add(new ProdutoVO());
		pedidoVO.setProdutoVOList(produtoVOList);

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

	public void totalPrecoDeCusto() {

	}

	public void salvarPedido() {
	}
	
	public void adicionarProduto(){
	
		if (pedidoVO != null && pedidoVO.getProdutoVOList() != null) {
			ProdutoVO produtoVO = new ProdutoVO();
			produtoVO.setQuantidadeProduto(new Long(0));
			getProduto().setPrecoCusto(new BigDecimal(0));
			getProduto().setPrecoVenda(new BigDecimal(0));
			produtoVO.setProduto(getProduto());
			pedidoVO.getProdutoVOList().add(0, produtoVO);
		}
		
		calcularTotalCusto();
		calcularTotalVenda();
	}

	private void calcularTotalCusto() {
		pedidoVO.setTotalCusto(pedidoVO.getProdutoVOList().stream().mapToDouble(p -> p.getQuantidadeProduto() * (p.getProduto().getPrecoCusto()).doubleValue()).sum());
	}

	private void calcularTotalVenda() {
		pedidoVO.setTotalVenda(pedidoVO.getProdutoVOList().stream().mapToDouble(p -> p.getQuantidadeProduto() * (p.getProduto().getPrecoVenda()).doubleValue()).sum());
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

	public List<ProdutoPedido> getProdutoPedidoList() {
		return produtoPedidoList;
	}

	public void setProdutoPedidoList(List<ProdutoPedido> produtoPedidoList) {
		this.produtoPedidoList = produtoPedidoList;
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

	public BigDecimal getTotalCusto() {
		return totalCusto;
	}

	public void setTotalCusto(BigDecimal totalCusto) {
		this.totalCusto = totalCusto;
	}

}