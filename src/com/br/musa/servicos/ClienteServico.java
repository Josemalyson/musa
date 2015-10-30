package com.br.musa.servicos;

import java.util.List;

import javax.inject.Inject;

import com.br.musa.constantes.Constantes;
import com.br.musa.constantes.MsgConstantes;
import com.br.musa.entidades.Cliente;
import com.br.musa.exeption.BusinessException;
import com.br.musa.repositorio.ClienteRepositorio;
import com.br.musa.util.CpfUtil;
import com.br.musa.util.FacesUtil;
import com.sun.xml.internal.fastinfoset.sax.Properties;

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

	public boolean validarCpfCliente(Cliente cliente) {
		if (!CpfUtil.isValido(cliente.getCpf())) {
			throw new BusinessException(MsgConstantes.CPF_INVALIDO);
		}
		return true;

	}
}
