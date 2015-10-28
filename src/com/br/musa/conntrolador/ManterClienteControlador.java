package com.br.musa.conntrolador;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;

import com.br.musa.constantes.Constantes;
import com.br.musa.constantes.MsgConstantes;
import com.br.musa.entidades.Cliente;
import com.br.musa.servicos.ClienteServico;
import com.br.musa.util.Data;
import com.br.musa.util.MascaraUtil;
import com.br.musa.util.ObjetoUtil;

@ManagedBean
@ViewScoped
public class ManterClienteControlador extends CoreControlador {

	private static final long serialVersionUID = 921184405757324590L;

	// Servicos
	@Inject
	private ClienteServico clienteServico;

	// LISTAS
	private List<Cliente> clientesList;
	private List<Cliente> clientesListFiltrados;

	// OBJETOS

	private Cliente cliente;
	private Date dataMax;

	@PostConstruct
	public void init() {
		listarTodosOsClientes();
		dataMax = new Date();

	}

	public void listarTodosOsClientes() {
		clientesList = clienteServico.listarTodosClientes();
	}

	public String adiconarMascaraCpf(Cliente cliente) {
		try {
			return MascaraUtil.adicionarMascara(cliente.getCpf(), MascaraUtil.CPF);
		} catch (ParseException e) {
			e.printStackTrace();
			return new String();
		}
	}

	public String adiconarMascaraRg(Cliente cliente) {
		try {
			return MascaraUtil.adicionarMascara(cliente.getRg(), "###.###-#");
		} catch (ParseException e) {
			e.printStackTrace();
			return new String();
		}
	}

	public String editarCliente(Cliente cliente) {
		adicionarAtributoFlash("cliente", cliente);
		return sendRedirect(Constantes.PAGINA_EDITAR_CLIENTE);
	}

	public void excluir() {

		if (ObjetoUtil.notBlank(cliente)) {
			clienteServico.excluir(cliente);
			adicionarMensagem(MsgConstantes.MSG_ALTERACAO_SUCESSO);
			listarTodosOsClientes();
			RequestContext.getCurrentInstance().update("tabelaCliente");
		} else {
			adicionarErro(MsgConstantes.MSG_ERRO);
		}

	}

	public String salvarCliente() {
		cliente.setCpf(MascaraUtil.removerMascara(cliente.getCpf()));
		cliente.setRg(MascaraUtil.removerMascara(cliente.getRg()));
		clienteServico.salvar(cliente);
		cliente = new Cliente();
		listarTodosOsClientes();
		adicionarMensagem(MsgConstantes.MSG_SUCESSO);
		RequestContext.getCurrentInstance().update("tabelaCliente");
		return sendRedirect(Constantes.PAGINA_LISTAR_CLIENTES);
	}

	public String montarDataNascimento(Cliente cliente) {

		if (ObjetoUtil.notBlank(cliente.getDtNascimento())) {
			return Data.getDataFormatada(cliente.getDtNascimento());
		}
		return Constantes.STRING_VAZIA;
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

	public Date getDataMax() {
		return dataMax;
	}

	public void setDataMax(Date dataMax) {
		this.dataMax = dataMax;
	}

	public List<Cliente> getClientesListFiltrados() {
		return clientesListFiltrados;
	}

	public void setClientesListFiltrados(List<Cliente> clientesListFiltrados) {
		this.clientesListFiltrados = clientesListFiltrados;
	}

}
