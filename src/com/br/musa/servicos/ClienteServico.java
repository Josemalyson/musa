package com.br.musa.servicos;

import java.util.List;

import javax.inject.Inject;

import com.br.musa.constantes.MsgConstantes;
import com.br.musa.entidades.Cliente;
import com.br.musa.exeption.BusinessException;
import com.br.musa.repositorio.ClienteRepositorio;
import com.br.musa.util.CpfUtil;

public class ClienteServico {

	@Inject
	private ClienteRepositorio clienteRepositorio;

	public List<Cliente> listarTodosClientes() {
		return clienteRepositorio.listar();
	}

	public void salvar(Cliente cliente) {
		clienteRepositorio.salvar(cliente);
	}

	public void excluir(Cliente cliente) {
		clienteRepositorio.excluir(cliente);
	}

	public boolean validarClienteParaSalvar(Cliente cliente){
		StringBuilder erro = new StringBuilder();
		
		if (cliente.getNome() == null || cliente.getNome().isEmpty()) {
			erro.append("Preencha o campo cliente").append(" \n");
		}
		
		if (cliente.getCpf() == null || cliente.getCpf().isEmpty()) {
			erro.append("Preencha o campo cpf").append(" \n");
		}

		if (cliente.getRg() == null || cliente.getRg().isEmpty()) {
			erro.append("Preencha o campo Rg").append(" \n");
		}
		
		if (cliente.getContatoList() == null || cliente.getContatoList().isEmpty()) {
			erro.append("Preencha o campo Contato").append(" \n");
		}
		
		if (!erro.toString().isEmpty()) {
			throw new BusinessException(erro.toString());
		}
		return true;
		
		
	}
	
	public boolean validarCpfCliente(Cliente cliente) {
		if (!CpfUtil.isValido(cliente.getCpf())) {
			throw new BusinessException(MsgConstantes.CPF_INVALIDO);
		}
		return true;

	}
}
