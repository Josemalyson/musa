package com.br.musa.controlador;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.br.musa.entidades.Pagamento;
import com.br.musa.servicos.PagamentoServico;

@ManagedBean
@ViewScoped
public class PagamentoControlador extends CoreControlador {
	private static final long serialVersionUID = 4425238557135997397L;

	// SERVICOS
	@Inject
	private PagamentoServico pagamentoServico;

	// OBJETOS

	// FLAGS

	// LISTA
	private List<Pagamento> pagamentoList;

	private static final Logger logger = Logger.getLogger(PagamentoControlador.class);

	@PostConstruct
	public void init() {
		pagamentoList = pagamentoServico.listarPagamentoPorPedido(idPedido);
	}

	public List<Pagamento> getPagamentoList() {
		return pagamentoList;
	}

	public void setPagamentoList(List<Pagamento> pagamentoList) {
		this.pagamentoList = pagamentoList;
	}

}