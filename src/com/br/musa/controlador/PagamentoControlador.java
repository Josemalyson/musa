package com.br.musa.controlador;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.br.musa.constantes.MsgConstantes;
import com.br.musa.entidades.Cliente;
import com.br.musa.entidades.Pagamento;
import com.br.musa.servicos.ClienteServico;
import com.br.musa.servicos.PagamentoServico;

@ManagedBean
@ViewScoped
public class PagamentoControlador extends CoreControlador {
	private static final long serialVersionUID = 4425238557135997397L;
	private static final Logger logger = Logger.getLogger(PagamentoControlador.class);
	// SERVICOS
	@Inject
	private PagamentoServico pagamentoServico;
	@Inject
	private ClienteServico clienteServico;

	// OBJETOS
	private Pagamento pagamento;
	private Cliente cliente;
	private Date dtHoje;
	private Date dtMax;

	// FLAGS

	// LISTA
	private List<Pagamento> pagamentoList;
	private List<Pagamento> pagamentoListFiltrados;
	private List<Cliente> clienteList;

	@PostConstruct
	public void init() {
		pagamento = new Pagamento();
		cliente = new Cliente();
		dtHoje = new Date();
		dtMax = new Date();

	}

	public List<Cliente> autoCompleteCliente(String query) {
		try {
			clienteList = clienteServico.listarTodosClientes().get();
		} catch (InterruptedException | ExecutionException e) {
			logger.info(" Erro na execu��o do m�todo ass�crono " +e.getMessage(),e);
			adicionarErro(MsgConstantes.ERRO_NO_PROCESSAMENTO);
		}
		return clienteServico.autoCompleteClienteServico(query, clienteList);
	}

	public void listarPagamentos() {
		pagamentoList = pagamentoServico.filtarPagamentos(dtHoje, cliente);
		if (pagamentoList == null || pagamentoList.isEmpty()) {
			adicionarAviso(MsgConstantes.NAO_HA_PEDIDOS_PESQUISADOS);
		}
	}

	public void limparFiltros() {
		dtHoje = null;
		cliente = null;
		pagamentoList = null;
		pagamentoListFiltrados = null;
	}

	public List<Pagamento> getPagamentoList() {
		return pagamentoList;
	}

	public void setPagamentoList(List<Pagamento> pagamentoList) {
		this.pagamentoList = pagamentoList;
	}

	public Date getDtHoje() {
		return dtHoje;
	}

	public void setDtHoje(Date dtHoje) {
		this.dtHoje = dtHoje;
	}

	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Date getDtMax() {
		return dtMax;
	}

	public void setDtMax(Date dtMax) {
		this.dtMax = dtMax;
	}

	public List<Pagamento> getPagamentoListFiltrados() {
		return pagamentoListFiltrados;
	}

	public void setPagamentoListFiltrados(List<Pagamento> pagamentoListFiltrados) {
		this.pagamentoListFiltrados = pagamentoListFiltrados;
	}

}