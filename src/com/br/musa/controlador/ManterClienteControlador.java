package com.br.musa.controlador;

import java.util.ArrayList;
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
import com.br.musa.entidades.Vo.ClienteVO;
import com.br.musa.servicos.ClienteServico;


@ManagedBean
@ViewScoped
public class ManterClienteControlador extends CoreControlador {

	static final long serialVersionUID = 921184405757324590L;

	// Servicos
	@Inject
	private ClienteServico clienteServico;

	// LISTAS
	private List<Cliente> clientesListFiltrados;
	private List<ClienteVO> clienteVOlist;

	// OBJETOS

	private Cliente clienteSelecioando;
	private Date dataMax;

	@PostConstruct
	public void init() {
		clienteSelecioando = new Cliente();
		listarTodosOsClientes();
		dataMax = new Date();

	}

	public void listarTodosOsClientes() {
		clienteVOlist = new ArrayList<ClienteVO>();
		clienteServico.montarClienteVO(clienteVOlist);
	}

	public String editarCliente(Cliente cliente) {
		adicionarAtributoFlash("cliente", cliente);
		return sendRedirect(Constantes.PAGINA_CLIENTE);
	}

	public void excluir() {

		clienteServico.excluir(clienteSelecioando);
		adicionarMensagem(MsgConstantes.MSG_ALTERACAO_SUCESSO);
		listarTodosOsClientes();
		RequestContext.getCurrentInstance().update("tabelaCliente");

	}

	public Cliente getCliente() {
		return clienteSelecioando;
	}

	public void setCliente(Cliente cliente) {
		this.clienteSelecioando = cliente;
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

	public ClienteServico getClienteServico() {
		return clienteServico;
	}

	public void setClienteServico(ClienteServico clienteServico) {
		this.clienteServico = clienteServico;
	}

	public List<ClienteVO> getClienteVOlist() {
		return clienteVOlist;
	}

	public void setClienteVOlist(List<ClienteVO> clienteVOlist) {
		this.clienteVOlist = clienteVOlist;
	}

}
