package com.br.musa.servicos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.transaction.Transactional;

import com.br.musa.constantes.Constantes;
import com.br.musa.constantes.MsgConstantes;
import com.br.musa.entidades.Cliente;
import com.br.musa.entidades.Pagamento;
import com.br.musa.entidades.Pedido;
import com.br.musa.entidades.StatusPedido;
import com.br.musa.enums.StatusPedidoEnum;
import com.br.musa.exeption.MusaExecao;
import com.br.musa.repositorio.PagamentoRepositorio;
import com.br.musa.util.JavaScriptUtil;

public class PagamentoServico implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2087468224478538322L;

	@Inject
	private PagamentoRepositorio pagamentoRepositorio;

	/**
	 * Provider Ã© utilizado para corrigir o erro de InjeÃ§Ãµes circulares quando
	 * existe beans que injentam um ao outro.
	 */

	@Inject
	private Provider<PedidoServico> pedidoServico;

	@Inject
	private StatusPedidoServico statusPedidoServico;

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

	@Transactional
	public void salvar(Pagamento pagamento) {
		validarPagamento(pagamento);
		Pagamento pagamentoBD = pagamentoRepositorio.salvar(pagamento);
		verificarSePossivelFinalizarPagamento(pagamentoBD);
	}

	private void verificarSePossivelFinalizarPagamento(Pagamento pagamentoBD) {

		if (pagamentoBD.getValorRestante().intValue() == 0) {
			Pedido pedidoBD = pedidoServico.get().consultarPorId(pagamentoBD.getPedido());
			if (pedidoBD != null) {
				StatusPedido statusPedido = statusPedidoServico.buscarPorCodigo(StatusPedidoEnum.PAGO);
				pedidoBD.setStatusPedido(statusPedido);
				pedidoServico.get().salvar(pedidoBD);
			}

		}

	}

	private void validarPagamento(Pagamento pagamento) {
		camposObrigatorios(pagamento);
		isPagamentoValido(pagamento);
	}

	private void camposObrigatorios(Pagamento pagamento) {
		StringBuilder erro = new StringBuilder();

		if (pagamento.getValorPago() == null) {
			erro.append("Preencher campo Valor Pago.").append(Constantes.TAG_BR);
			JavaScriptUtil.marcarCampoObrigatorio("valorPago");
		}

		if (pagamento.getObservacao() == null || pagamento.getObservacao().isEmpty()) {
			erro.append("Preencher campo Observação.").append(Constantes.TAG_BR);
			JavaScriptUtil.marcarCampoObrigatorio("observacao");
		}
		if (!erro.toString().isEmpty()) {
			throw new MusaExecao(erro.toString());
		}
	}

	private void isPagamentoValido(Pagamento pagamento) {

		if (pagamento.getValorPago().intValue() < 0) {
			throw new MusaExecao(MsgConstantes.VALOR_PAGO_DIFERENTE_ZERO);
		}
	}

	public Pagamento consultarPagamentoPorID(Pagamento pagamento) {
		return pagamentoRepositorio.consultarPorId(pagamento);
	}

}
