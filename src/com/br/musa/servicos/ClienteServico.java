package com.br.musa.servicos;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.jboss.logging.Logger;

import com.br.musa.constantes.Constantes;
import com.br.musa.constantes.MsgConstantes;
import com.br.musa.entidades.Cliente;
import com.br.musa.entidades.Contato;
import com.br.musa.entidades.Endereco;
import com.br.musa.entidades.Estado;
import com.br.musa.entidades.vo.ClienteVO;
import com.br.musa.enums.EstadoEnum;
import com.br.musa.exeption.MusaExecao;
import com.br.musa.repositorio.ClienteRepositorio;
import com.br.musa.util.CpfUtil;
import com.br.musa.util.JavaScriptUtil;
import com.br.musa.util.MascaraUtil;

public class ClienteServico implements Serializable {

	private static final long serialVersionUID = 4806862435948739858L;
	private static final Logger logger = Logger.getLogger(ClienteServico.class);

	@Inject
	private ClienteRepositorio clienteRepositorio;
	@Inject
	private EstadoServico estadoServico;
	@Inject
	private CidadeServico cidadeServico;

	@Asynchronous
	public Future<List<Cliente>> listarTodosClientes() {
		return new javax.ejb.AsyncResult<>(clienteRepositorio.listar());
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

	// TODO SÃ“ SERA POSSIVEL EXCLUIR UM CLIENTE QUANDO O MESMO NÃƒO POSSUIR
	// CONTAS PENDENTES.
	@Transactional
	public void excluir(Cliente cliente) {
		if (cliente != null) {
			clienteRepositorio.excluir(cliente);
		}

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

		if (contato != null) {

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
			throw new MusaExecao(erro.toString());
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
			throw new MusaExecao(erro.toString());
		}
	}

	private void validarCpfCliente(Cliente cliente) {
		if (!CpfUtil.isValido(cliente.getCpf())) {
			throw new MusaExecao(MsgConstantes.CPF_INVALIDO);
		}
	}

	public void excluirContato(Cliente cliente, Contato contato) {
		cliente.getContatoList().remove(contato);

	}

	public void montarEstadoECidadePadrao(Cliente cliente) {
		if (cliente.getEndereco() == null || cliente.getEndereco().getEstado() == null
				|| cliente.getEndereco().getCidade() == null) {
			Estado estado = estadoServico.buscarPorCodigo(EstadoEnum.PARAIBA.getCodigo());
			Endereco endereco = new Endereco();
			endereco.setEstado(estado);
			cliente.setEndereco(endereco);
			cliente.getEndereco().setEstado(estado);
			cliente.getEndereco()
					.setCidade(cidadeServico.buscarCidadePorCodigoEstado(Constantes.ID_JOAO_PESSOA, estado));
		}
	}

	@Asynchronous
	public Future<List<ClienteVO>> montarClienteVO() {
		List<Cliente> clienteList = clienteRepositorio.listar();

		List<ClienteVO> clienteVOList = new ArrayList<>();
		for (Cliente cliente : clienteList) {
			ClienteVO clienteVO = new ClienteVO();
			clienteVO.setCliente(cliente);
			clienteVO.setCodigo(cliente.getId().toString());
			clienteVO.setNome(cliente.getNome());
			try {

				clienteVO.setCpf(adicionarMascaraCpf(cliente).get());
				clienteVO.setDataNascimento(montarDataNascimento(cliente).get());
				clienteVO.setNumeroTelefone(montarNumeroTelefone(cliente).get());
				clienteVO.setRg(adiconarMascaraRg(cliente).get());

			} catch (InterruptedException | ExecutionException e) {
				logger.info(" Erro na execução do método assícrono " + e.getMessage(), e);
				throw new MusaExecao(MsgConstantes.ERRO_NO_PROCESSAMENTO);
			}

			clienteVOList.add(clienteVO);
		}
		return new AsyncResult<>(clienteVOList);
	}

	@Asynchronous
	private Future<String> montarNumeroTelefone(Cliente cliente) {
		List<String> telefoneFormatado = new ArrayList<>();
		if (cliente.getContatoList() != null || cliente.getContatoList().isEmpty()) {
			for (Contato contato : cliente.getContatoList()) {
				telefoneFormatado.add(contato.getTelefone());
			}
		}
		return new javax.ejb.AsyncResult<>(telefoneFormatado.toString().replace("[", "").replace("]", "").concat("."));
	}

	@Asynchronous
	private Future<String> montarDataNascimento(Cliente cliente) {
		if (cliente.getDtNascimento() != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			return new AsyncResult<>(dateFormat.format(cliente.getDtNascimento()));
		}
		return new AsyncResult<>(Constantes.STRING_VAZIA);
	}

	@Asynchronous
	public Future<String> adicionarMascaraCpf(Cliente cliente) {
		try {
			return new AsyncResult<>(MascaraUtil.adicionarMascara(cliente.getCpf(), MascaraUtil.CPF));
		} catch (ParseException e) {
			logger.error(e.getMessage());
			return new AsyncResult<>(Constantes.STRING_VAZIA);
		}
	}

	@Asynchronous
	private Future<String> adiconarMascaraRg(Cliente cliente) {
		try {
			return new AsyncResult<>(MascaraUtil.adicionarMascara(cliente.getRg(), "###.###-#"));
		} catch (ParseException e) {
			logger.error(e.getMessage());
			return new AsyncResult<>(Constantes.STRING_VAZIA);
		}
	}

	public Cliente buscarPorCodigo(Cliente cliente) {
		return clienteRepositorio.consultarPorId(cliente);
	}

	public List<Cliente> autoCompleteClienteServico(String query, List<Cliente> clienteList) {
		List<Cliente> clienteFiltradosList = new ArrayList<>();

		for (int i = 0; i < clienteList.size(); i++) {
			Cliente cliente = clienteList.get(i);
			if (cliente.getNome().toLowerCase().startsWith(query.toLowerCase())) {
				clienteFiltradosList.add(cliente);
			}
		}

		return clienteFiltradosList;
	}
}
