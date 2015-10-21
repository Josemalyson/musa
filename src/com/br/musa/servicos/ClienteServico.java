package com.br.musa.servicos;

import java.util.List;

import javax.inject.Inject;

import com.br.musa.entidades.Cliente;
import com.br.musa.repositorio.ClienteRepositorio;

public class ClienteServico {

	@Inject
	private ClienteRepositorio clienteRepositorio;
	
	
	public List<Cliente> listarTodosClientes(){
		return clienteRepositorio.listar();
	}


	public void salvar(Cliente cliente) {
		clienteRepositorio.salvar(cliente);
	}
}
