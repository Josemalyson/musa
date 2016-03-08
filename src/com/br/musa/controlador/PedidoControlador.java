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
import com.br.musa.servicos.ClienteServico;

@ManagedBean
@ViewScoped
public class PedidoControlador extends CoreControlador {
	private static final long serialVersionUID = 4425238557135997397L;

	// SERVICOS
	@Inject
	private ClienteServico clienteServico;

	// OBJETOS
	private Cliente cliente;
	private Pedido pedido;
	private Double totalCusto;
	private Double totalVenda;

	// LISTA
	private List<Cliente> clienteList;
	private List<Produto> produtoList;

	private static final Logger logger = Logger.getLogger(PedidoControlador.class);

	@PostConstruct
	public void init() {
		listarCliente();
		cliente = new Cliente();
		totalCusto = new Double(0);
		totalVenda = new Double(0);
		pedido = new Pedido();
		pedido.setDtPedido(new Date());
		produtoList = new ArrayList<>();
		totalPrecoDeCusto();

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

	public void atualizarCliente(){
		if (this.cliente.getId() != null) {
			this.cliente = clienteServico.buscarPorCodigo(cliente);
			this.cliente.setCpf(clienteServico.adicionarMascaraCpf(cliente));
			this.pedido.setCliente(this.cliente);
		}else {
			this.cliente = new Cliente();
		}
	}
	
	
	public void totalPrecoDeCusto(){
		Produto e = new Produto();
		e.setPrecoCusto(new BigDecimal(100));
		e.setPrecoVenda(new BigDecimal(100));
		Produto e1 = new Produto();
		e1.setPrecoCusto(new BigDecimal(200));
		e1.setPrecoVenda(new BigDecimal(200));
		produtoList = new ArrayList<>();
		produtoList.add(e);
		produtoList.add(e1);
		totalCusto = produtoList.stream().mapToDouble(produto -> produto.getPrecoCusto().longValueExact()).sum();
		totalVenda = produtoList.stream().mapToDouble(produto -> produto.getPrecoVenda().longValueExact()).sum();
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

	public Double getTotalCusto() {
		return totalCusto;
	}

	public void setTotalCusto(Double totalCusto) {
		this.totalCusto = totalCusto;
	}

	public Double getTotalVenda() {
		return totalVenda;
	}

	public void setTotalVenda(Double totalVenda) {
		this.totalVenda = totalVenda;
	}

}