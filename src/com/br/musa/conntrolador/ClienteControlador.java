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
public class ClienteControlador extends CoreControlador {

	private static final long serialVersionUID = 921184405757324590L;

	//Servicos
	@Inject
	private ClienteServico clienteservico;
	
	//LISTAS
	private List<Cliente> clientesList;
	
	@PostConstruct
	public void init() {
		clientesList = clienteservico.listarTodosClientes();
		
	}

	public List<Cliente> getClientesList() {
		return clientesList;
	}

	public void setClientesList(List<Cliente> clientesList) {
		this.clientesList = clientesList;
	}

}
