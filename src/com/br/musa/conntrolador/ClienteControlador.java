package com.br.musa.conntrolador;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import com.br.musa.entidades.Cliente;
import com.br.musa.servicos.ClienteServico;

@ManagedBean
@ViewScoped
public class ClienteControlador extends ManterClienteControlador {

	private static final long serialVersionUID = 921184405757324590L;

	// Servicos
	@Inject
	private ClienteServico clienteServico;

	// LISTAS
	private List<Cliente> clientesList;
	private List<Cliente> clientesListFiltrados;

	// OBJETOS

	private Cliente cliente;

	@PostConstruct
	public void init() {
		cliente = new Cliente();

	}

	public String salvarCliente() {
		return super.salvarCliente();
	}

	public List<Cliente> getClientesList() {
		return clientesList;
	}

	public void setClientesList(List<Cliente> clientesList) {
		this.clientesList = clientesList;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Cliente> getClientesListFiltrados() {
		return clientesListFiltrados;
	}

	public void setClientesListFiltrados(List<Cliente> clientesListFiltrados) {
		this.clientesListFiltrados = clientesListFiltrados;
	}

}
