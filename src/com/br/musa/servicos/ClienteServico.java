package com.br.musa.servicos;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import com.br.musa.constantes.Constantes;
import com.br.musa.constantes.MsgConstantes;
import com.br.musa.entidades.Cliente;
import com.br.musa.entidades.Contato;
import com.br.musa.exeption.BusinessException;
import com.br.musa.repositorio.ClienteRepositorio;
import com.br.musa.util.CpfUtil;
import com.br.musa.util.JavaScriptUtil;
import com.br.musa.util.MascaraUtil;

public class ClienteServico {

	@Inject
	private ClienteRepositorio clienteRepositorio;

	public List<Cliente> listarTodosClientes() {
		return clienteRepositorio.listar();
	}

	@Transactional
	public void salvar(Cliente cliente) {

		cliente.setCpf(MascaraUtil.removerMascara(cliente.getCpf()));
		cliente.setRg(MascaraUtil.removerMascara(cliente.getRg()));
		cliente.setFlExcluido(false);

		if (cliente.getEndereco() != null && cliente.getEndereco().getCep() != null) {
			cliente.getEndereco().setCep(MascaraUtil.removerMascara(cliente.getEndereco().getCep()));
			cliente.getEndereco().setCliente(cliente);
		}

		validarClienteParaSalvar(cliente);
		clienteRepositorio.salvar(cliente);
	}

	@Transactional
	public void excluir(Cliente cliente) {
		clienteRepositorio.excluir(cliente);
	}

	public void validarClienteParaSalvar(Cliente cliente) {
		validarcamposObrigatoriosCliente(cliente);
		validarCpfCliente(cliente);
	}

	public void adicionarContatos(Contato contato, Cliente cliente) {
		contato.setCliente(cliente);
		contato.setFlExcluido(false);

		validarCamposObrigatorioContato(contato);

		if (!cliente.getContatoList().contains(contato)) {
			contato.setTelefone(MascaraUtil.removerMascara(contato.getTelefone()));
			contato.setDdd(MascaraUtil.removerMascara(contato.getDdd()));
			cliente.getContatoList().add(contato);
		}

	}

	private void validarCamposObrigatorioContato(Contato contato) {
		StringBuilder erro = new StringBuilder();

		if (contato != null ) {
			
			if (contato.getDdd() == null || contato.getDdd().isEmpty()) {
				erro.append("Preencha o campo DDD").append(Constantes.TAG_BR);
				JavaScriptUtil.marcarCampoObrigatorio("ddd");
			}
			
			if (contato.getTelefone() == null || contato.getTelefone().isEmpty()) {
				erro.append("Preencha o campo Telefone").append(Constantes.TAG_BR);
				JavaScriptUtil.marcarCampoObrigatorio("telefone");
			}
		}
		

		if (!erro.toString().isEmpty()) {
			throw new BusinessException(erro.toString());
		}
	}

	private void validarcamposObrigatoriosCliente(Cliente cliente) {
		StringBuilder erro = new StringBuilder();

		if (cliente.getNome() == null || cliente.getNome().isEmpty()) {
			erro.append("Preencha o campo cliente").append(Constantes.TAG_BR);
			JavaScriptUtil.marcarCampoObrigatorio("nome");
		}

		if (cliente.getCpf() == null || cliente.getCpf().isEmpty()) {
			erro.append("Preencha o campo cpf").append(Constantes.TAG_BR);
			JavaScriptUtil.marcarCampoObrigatorio("cpf");
		}

		if (cliente.getRg() == null || cliente.getRg().isEmpty()) {
			erro.append("Preencha o campo Rg").append(Constantes.TAG_BR);
			JavaScriptUtil.marcarCampoObrigatorio("rg");
		}

		if (!erro.toString().isEmpty()) {
			throw new BusinessException(erro.toString());
		}
	}

	private void validarCpfCliente(Cliente cliente) {
		if (!CpfUtil.isValido(cliente.getCpf())) {
			throw new BusinessException(MsgConstantes.CPF_INVALIDO);
		}
	}

	public void excluirContato(Cliente cliente, Contato contato) {
		cliente.getContatoList().remove(contato);

	}
}
