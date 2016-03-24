package com.br.musa.servicos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import com.br.musa.entidades.Cliente;
import com.br.musa.entidades.Pagamento;
import com.br.musa.repositorio.PagamentoRepositorio;

public class PagamentoServico {

	@Inject
	private PagamentoRepositorio pagamentoRepositorio;

	public List<Pagamento> listarPagamentoPorPedido(Long idPedido) {
		return pagamentoRepositorio.listarPagamentoPorPedido(idPedido);
	}

	public List<Pagamento> filtarPagamentos(Date dtHoje, Cliente cliente) {

		if (!isClienteValido(cliente) && isDataValida(dtHoje)) {
			return pagamentoRepositorio.obterPagamentosPorData(dtHoje);
		}

		if (isDataEClienteValidos(dtHoje, cliente)) {
			return pagamentoRepositorio.obterPagamentosPorClienteEData(cliente, dtHoje);
		}

		if (!isDataValida(dtHoje) && isClienteValido(cliente)) {
			return pagamentoRepositorio.obterPagamentosPorCliente(cliente);
		}

		return new ArrayList<>();

	}

	private boolean isDataValida(Date dtHoje) {
		return dtHoje != null;
	}

	private boolean isClienteValido(Cliente cliente) {
		return cliente != null && cliente.getId() != null;
	}

	private boolean isDataEClienteValidos(Date dtHoje, Cliente cliente) {
		return isDataValida(dtHoje) && isClienteValido(cliente);
	}

}
