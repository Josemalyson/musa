package com.br.musa.conntrolador;

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
import com.br.musa.util.MascaraUtil;

@ManagedBean
@ViewScoped
public class EditarClienteControlador extends ManterClienteControlador {

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
		cliente = (Cliente) obterAtributoFlash("cliente");

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
