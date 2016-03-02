package com.br.musa.servicos;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

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

	@Transactional
	public void salvar(Cliente cliente) {
		clienteRepositorio.salvar(cliente);
	}

	@Transactional
	public void excluir(Cliente cliente) {
		clienteRepositorio.excluir(cliente);
	}

	public boolean validarClienteParaSalvar(Cliente cliente){
		validarcamposObrigatorios(cliente);
		validarCpfCliente(cliente);
		return true;
		
		
	}

	private void validarcamposObrigatorios(Cliente cliente) {
		StringBuilder erro = new StringBuilder();
		
		if (cliente.getNome() == null || cliente.getNome().isEmpty()) {
			erro.append("Preencha o campo cliente").append("<br />");
		}
		
		if (cliente.getCpf() == null || cliente.getCpf().isEmpty()) {
			erro.append("Preencha o campo cpf").append("<br />");
		}

		if (cliente.getRg() == null || cliente.getRg().isEmpty()) {
			erro.append("Preencha o campo Rg").append("<br />");
		}
		
		if (cliente.getContatoList() == null || cliente.getContatoList().isEmpty()) {
			erro.append("Preencha o campo Contato").append("<br />");
		}
		
		if (!erro.toString().isEmpty()) {
			throw new BusinessException(erro.toString());
		}
	}
	
	private boolean validarCpfCliente(Cliente cliente) {
		if (!CpfUtil.isValido(cliente.getCpf())) {
			throw new BusinessException(MsgConstantes.CPF_INVALIDO);
		}
		return true;

	}
}
