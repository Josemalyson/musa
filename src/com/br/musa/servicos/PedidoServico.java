package com.br.musa.servicos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.jboss.logging.Logger;

import com.br.musa.constantes.Constantes;
import com.br.musa.constantes.MsgConstantes;
import com.br.musa.entidades.Cliente;
import com.br.musa.entidades.Pagamento;
import com.br.musa.entidades.Pedido;
import com.br.musa.entidades.Produto;
import com.br.musa.entidades.ProdutoPedido;
import com.br.musa.entidades.ProdutoPedidoPK;
import com.br.musa.entidades.StatusPedido;
import com.br.musa.entidades.TipoPedido;
import com.br.musa.entidades.vo.PedidoVO;
import com.br.musa.entidades.vo.ProdutoVO;
import com.br.musa.enums.DescontoEnum;
import com.br.musa.enums.StatusPedidoEnum;
import com.br.musa.enums.TipoPedidoEnum;
import com.br.musa.exeption.CalculadoraExecao;
import com.br.musa.exeption.MusaExecao;
import com.br.musa.repositorio.PedidoRepositorio;
import com.br.musa.util.CalcularUtil;
import com.br.musa.util.JavaScriptUtil;

@Stateless
public class PedidoServico implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2926719468007590581L;
	@Inject
	private PedidoRepositorio pedidoRepositorio;
	@Inject
	private ProdutoPedidoServico produtoPedidoServico;
	@Inject
	private ClienteServico clienteServico;
	@Inject
	private ProdutoServico produtoServico;
	@Inject
	private TipoPedidoServico tipoPedidoServico;
	@Inject
	private StatusPedidoServico statusPedidoServico;
	@Inject
	private PagamentoServico pagamentoServico;

	private static final Logger logger = Logger.getLogger(PedidoServico.class);

	public void validarQuantidadeDoProduto(ProdutoVO produtoVO) {
		ProdutoVO produtoVOTemp = produtoVO;

		if (produtoVOTemp == null) {
			produtoVOTemp = new ProdutoVO();
			Produto produto = new Produto();
			produtoVOTemp.setProduto(produto);
		}

		isQuantidadeNula(produtoVOTemp);
		isQuantidadeZero(produtoVOTemp);
	}

	private void isQuantidadeZero(ProdutoVO produtoVO) {
		if (produtoVO.getQuantidadeProduto() <= 0) {
			throw new MusaExecao(MsgConstantes.ERRO_QUANTIDADE_ZERO);
		}
	}

	private void isQuantidadeNula(ProdutoVO produtoVO) {
		if (produtoVO.getQuantidadeProduto() == null) {
			produtoVO.setQuantidadeProduto(new Integer(1));
		}
	}

	public BigInteger obterNumerorDoProximoPedido() {
		return pedidoRepositorio.obterNumeroDoProximoPedido() == null ? new BigInteger("1")
				: pedidoRepositorio.obterNumeroDoProximoPedido().add(new BigInteger("1"));
	}

	@Asynchronous
	public Future<List<Pedido>> listarNaoExcluidos() {
		return new AsyncResult<>(pedidoRepositorio.listarNaoExcluidos());
	}

	public List<Pedido> listarPedidosPorCliente(Long id) {
		if (pedidoRepositorio.listarPedidosPorCliente(id) == null
				|| pedidoRepositorio.listarPedidosPorCliente(id).isEmpty()) {
			throw new MusaExecao(MsgConstantes.NAO_HA_PEDIDOS_PARA_CLIENTE);
		}

		return pedidoRepositorio.listarPedidosPorCliente(id);
	}

	public PedidoVO montarPedidoNovoEdicao(Pedido pedido, Cliente cliente) {
		PedidoVO pedidoVO = new PedidoVO();

		if (isPedidoValido(pedido)) {
			montarNovoPedido(pedido, cliente, pedidoVO);
		} else {
			montarPedidoEdicao(pedido, pedidoVO);
		}
		return pedidoVO;
	}

	private void montarPedidoEdicao(Pedido pedido, PedidoVO pedidoVO) {
		pedidoVO.setPedido(pedido);
		pedidoVO.setCliente(pedido.getCliente());
		pedidoVO.setNumeroPedido(pedido.getId().toString());

		List<ProdutoVO> produtoVOList = new ArrayList<>();
		List<Produto> produtoBDList = produtoServico.listarProdutosPorPedido(pedido.getId());

		for (Produto produto : produtoBDList) {
			ProdutoVO produtoVO = new ProdutoVO();
			produtoVO.setProduto(produto);
			produtoVO.setQuantidadeProduto(
					produtoPedidoServico.buscarPedidoPorPedido(pedido.getId(), produto.getId()).getQtdProduto());
			produtoVOList.add(produtoVO);

		}

		pedidoVO.setProdutoVOList(produtoVOList);
	}

	private void montarNovoPedido(Pedido pedido, Cliente cliente, PedidoVO pedidoVO) {
		pedidoVO.setCliente(cliente);
		pedidoVO.setNumeroPedido(obterNumerorDoProximoPedido().toString());
		pedidoVO.setPedido(pedido);
		pedidoVO.setProdutoVOList(new ArrayList<ProdutoVO>());
		pedidoVO.getPedido().setTipoPedido(montarTipoPedidoNovo() != null ? montarTipoPedidoNovo() : new TipoPedido());
		pedidoVO.getPedido()
				.setStatusPedido(montarStatusPedidoNovo() != null ? montarStatusPedidoNovo() : new StatusPedido());
	}

	private boolean isPedidoValido(Pedido pedido) {
		return pedido.getId() == null;
	}

	private StatusPedido montarStatusPedidoNovo() {
		return statusPedidoServico.buscarPorCodigo(StatusPedidoEnum.NAO_PAGO);
	}

	private TipoPedido montarTipoPedidoNovo() {
		return tipoPedidoServico.buscarPorCodigo(TipoPedidoEnum.ATACADO);
	}

	public void adicionarProduto(PedidoVO pedidoVO, Produto produto) {

		if (isListaProdutoValida(pedidoVO)) {
			ProdutoVO produtoVO = new ProdutoVO();
			produtoVO.setQuantidadeProduto(new Integer(1));
			produto.setPrecoCusto(new BigDecimal(0));
			produto.setPrecoVenda(new BigDecimal(0));
			produtoVO.setProduto(produto);
			pedidoVO.getProdutoVOList().add(0, produtoVO);
		}
		calcularTotal(pedidoVO);
	}

	private boolean isListaProdutoValida(PedidoVO pedidoVO) {
		return pedidoVO != null && pedidoVO.getProdutoVOList() != null;
	}

	@Asynchronous
	public void calcularTotal(PedidoVO pedidoVO) {
		calcularTotalCusto(pedidoVO);
		calcularTotalVenda(pedidoVO);
		calcularTotalPedido(pedidoVO);
	}

	private void calcularTotalPedido(PedidoVO pedidoVO) {
		if (isTipoPedidoAtacado(pedidoVO)) {
			pedidoVO.getPedido().setValorTotal(pedidoVO.getPedido().getNuTotalCusto());
		} else {
			pedidoVO.getPedido().setValorTotal(pedidoVO.getPedido().getNuTotalVenda());
		}

	}

	public boolean isTipoPedidoAtacado(PedidoVO pedidoVO) {
		return pedidoVO.getPedido().getTipoPedido().getId().equals(TipoPedidoEnum.ATACADO.getCodigo());
	}

	private void calcularTotalCusto(PedidoVO pedidoVO) {
		pedidoVO.getPedido().setNuTotalCusto(new BigDecimal(pedidoVO.getProdutoVOList().stream()
				.mapToDouble(p -> p.getQuantidadeProduto() * (p.getProduto().getPrecoCusto()).doubleValue()).sum()));
	}

	private void calcularTotalVenda(PedidoVO pedidoVO) {
		pedidoVO.getPedido().setNuTotalVenda(new BigDecimal(pedidoVO.getProdutoVOList().stream()
				.mapToDouble(p -> p.getQuantidadeProduto() * (p.getProduto().getPrecoVenda()).doubleValue()).sum()));
	}

	@Transactional
	public void salvar(PedidoVO pedidoVO) {

		verificarCampoObrigatorios(pedidoVO);

		if (pedidoVO.getCliente() != null) {
			pedidoVO.getPedido().setCliente(clienteServico.buscarPorCodigo(pedidoVO.getCliente()));
		}

		Pedido pedidoBD = pedidoRepositorio.salvar(pedidoVO.getPedido());

		for (ProdutoVO produtoVO : pedidoVO.getProdutoVOList()) {
			ProdutoPedido produtoPedido = new ProdutoPedido();
			produtoPedido.setQtdProduto(produtoVO.getQuantidadeProduto());
			ProdutoPedidoPK id = new ProdutoPedidoPK(produtoVO.getProduto().getId(), pedidoBD.getId());
			produtoPedido.setId(id);
			produtoPedidoServico.salvarProdutoPedido(produtoPedido);
		}

	}

	private void verificarCampoObrigatorios(PedidoVO pedidoVO) {

		StringBuilder erro = new StringBuilder();

		if (pedidoVO.getPedido() != null && pedidoVO.getPedido().getTipoPedido() == null) {
			JavaScriptUtil.marcarCampoObrigatorio("tipoPedidoId");
			erro.append("Preencher campo Tipo Pedido").append(Constantes.TAG_BR);
		}

		if (pedidoVO.getPedido() != null && pedidoVO.getPedido().getStatusPedido() == null) {
			JavaScriptUtil.marcarCampoObrigatorio("statusPedido");
			erro.append("Preencher campo Status Pedido").append(Constantes.TAG_BR);
		}

		if (pedidoVO.getProdutoVOList() == null || pedidoVO.getProdutoVOList().isEmpty()) {
			JavaScriptUtil.marcarCampoObrigatorio("statusPedido");
			erro.append("Preencher campo Produto").append(Constantes.TAG_BR);
		}

		if (!erro.toString().isEmpty()) {
			throw new MusaExecao(erro.toString());
		}
	}

	@Asynchronous
	public Future<List<PedidoVO>> montartPedidosVO(List<Pedido> pedidolist) {
		List<PedidoVO> pedidoVOlist = new ArrayList<>();
		for (Pedido pedido : pedidolist) {
			PedidoVO pedidoVO = new PedidoVO();

			pedidoVO.setPedido(pedido);
			pedidoVO.setCliente(pedido.getCliente());
			pedidoVO.setNumeroPedido(pedido.getId().toString());
			pedidoVO.setProdutoVOList(new ArrayList<ProdutoVO>());
			try {
				pedidoVO.setPagamento(montarUltimoPagamento(pedido).get());
			} catch (InterruptedException | ExecutionException e) {
				logger.info(Constantes.ERRO_NA_EXECUÇÃO_DO_MÉTODO_ASSÍCRONO +e.getMessage(),e);
				throw new MusaExecao(MsgConstantes.ERRO_NO_PROCESSAMENTO);
			}
			pedidoVOlist.add(pedidoVO);
		}

		return new AsyncResult<>(pedidoVOlist);
	}

	@Asynchronous
	private Future<Pagamento> montarUltimoPagamento(Pedido pedido) {
		List<Pagamento> pagamentosBDList = pagamentoServico.listarPagamentoPorPedido(pedido.getId());

		Pagamento pagamento = new Pagamento();

		if (pagamentosBDList != null && !pagamentosBDList.isEmpty()) {
			pagamento = pagamentosBDList.get(0);
			return new AsyncResult<>(pagamento);
		}

		pagamento.setObservacao(Constantes.STRING_VAZIA);
		return new AsyncResult<>(pagamento);
	}

	public List<Pedido> listarPedidosSemCliente() {
		return pedidoRepositorio.listarPedidosSemCliente();
	}

	public void verificarSeExisteProdutosCadastrados() {
		if (produtoServico.listarProdutosAtivos() == null || produtoServico.listarProdutosAtivos().isEmpty()) {
			throw new MusaExecao(MsgConstantes.NAO_EXISTE_PRODUTOS_CADASTRADOS);
		}

	}

	public void calcularDesconto(PedidoVO pedidoVO) {
		try {
			verificarSeDescontoFoiAplicado(pedidoVO);
			if (isTipoPedidoAtacado(pedidoVO)) {
				calcularDescontoTotalCusto(pedidoVO);
			} else {
				calcularDescontoTotalVenda(pedidoVO);
			}
		} catch (CalculadoraExecao e) {
			logger.error(e.getMessage(), e);
			throw new MusaExecao(MsgConstantes.PEDIDO_COM_VALOR_ZER0);

		}
	}

	private void verificarSeDescontoFoiAplicado(PedidoVO pedidoVO) {

		if (pedidoVO.getPedido().getId() != null) {

			if (isTipoPedidoAtacado(pedidoVO)) {
				if (pedidoVO.getPedido().getNuTotalCusto().intValue() < pedidoVO.getPedido().getValorTotal()
						.intValue()) {
					throw new MusaExecao(MsgConstantes.DESCONTO_JA_FOI_APLICADO);
				}
			} else {
				if (pedidoVO.getPedido().getNuTotalVenda().intValue() < pedidoVO.getPedido().getValorTotal()
						.intValue()) {
					throw new MusaExecao(MsgConstantes.DESCONTO_JA_FOI_APLICADO);
				}
			}
		}
	}

	private void calcularDescontoTotalVenda(PedidoVO pedidoVO) {
		pedidoVO.getPedido().setValorTotal(CalcularUtil.calcularDesconto(pedidoVO.getPedido().getNuTotalVenda(),
				pedidoVO.getPedido().getDesconto()));
	}

	private void calcularDescontoTotalCusto(PedidoVO pedidoVO) {
		pedidoVO.getPedido().setValorTotal(CalcularUtil.calcularDesconto(pedidoVO.getPedido().getNuTotalCusto(),
				pedidoVO.getPedido().getDesconto()));
	}

	public List<BigDecimal> montarListaDesconto() {
		return Arrays.asList(DescontoEnum.DESCONTO_0.getCodigo(), DescontoEnum.DESCONTO_10.getCodigo(),
				DescontoEnum.DESCONTO_20.getCodigo(), DescontoEnum.DESCONTO_30.getCodigo(),
				DescontoEnum.DESCONTO_40.getCodigo(), DescontoEnum.DESCONTO_50.getCodigo(),
				DescontoEnum.DESCONTO_60.getCodigo(), DescontoEnum.DESCONTO_70.getCodigo(),
				DescontoEnum.DESCONTO_80.getCodigo(), DescontoEnum.DESCONTO_90.getCodigo(),
				DescontoEnum.DESCONTO_100.getCodigo());
	}

	@Transactional
	public void excluir(Pedido pedido) {

		if (isPedidoComStatusNaoPago(pedido)) {
			throw new MusaExecao(MsgConstantes.NAO_PODE_EXCLUIR_PEDIDO_NAO_PAGO);
		}

		pedido.setFlExcluido(true);
		pedidoRepositorio.salvar(pedido);
	}

	private boolean isPedidoComStatusNaoPago(Pedido pedido) {
		return pedido.getStatusPedido().getId().equals(StatusPedidoEnum.NAO_PAGO.getCodigo());
	}

	public Pedido consultarPorId(Pedido pedido) {
		return pedidoRepositorio.consultarPorId(pedido);
	}

	@Transactional
	public void salvar(Pedido pedidoBD) {
		pedidoRepositorio.salvar(pedidoBD);

	}

	//TODO VERIDICAR PQ O MUSAEXECAO NAO ESTAVA SENDO CAPTURADO
	public void verificarcalculoDoValorRestante(PedidoVO pedidoVOSelecionado) {
		if (pedidoVOSelecionado.getPagamento().getValorPago().intValue() <= 0) {
			throw new RuntimeException(MsgConstantes.PAGAMENTO_COM_VALOR_IGUAL_OU_MENOR_QUE_ZERO);
		}

		if (pedidoVOSelecionado.getPagamento().getValorRestante() != null) {
			if (pedidoVOSelecionado.getPagamento().getValorPago().intValue() > pedidoVOSelecionado.getPagamento()
					.getValorRestante().intValue()) {
				throw new RuntimeException(MsgConstantes.PAGAMENTO_MAIOR_QUE_VALOR_RESTANTE);
			}

		}else if(pedidoVOSelecionado.getPagamento().getValorPago().intValue() > pedidoVOSelecionado.getPedido().getValorTotal().intValue()) {
			throw new RuntimeException(MsgConstantes.PAGAMENTO_MAIOR_QUE_VALOR_TOTAL);
		}

		calcularValorRestanteDoPedidoAposValidacoes(pedidoVOSelecionado);
	}

	private void calcularValorRestanteDoPedidoAposValidacoes(PedidoVO pedidoVOSelecionado) {

		List<Pagamento> listPagamento = pagamentoServico
				.listarPagamentoPorPedido(pedidoVOSelecionado.getPedido().getId());

		if (listPagamento != null && !listPagamento.isEmpty()) {
			Pagamento ultimoPagamento = listPagamento.get(0);
			pedidoVOSelecionado.getPagamento().setValorRestante(
					ultimoPagamento.getValorRestante().subtract(pedidoVOSelecionado.getPagamento().getValorPago()));
		} else {
			Pedido pedidoBD = pedidoRepositorio.consultarPorId(pedidoVOSelecionado.getPedido());

			pedidoVOSelecionado.getPagamento().setValorRestante(null);
			pedidoVOSelecionado.getPagamento().setValorTotalPedido(pedidoBD.getValorTotal());

			pedidoVOSelecionado.getPagamento().setValorRestante(
					pedidoBD.getValorTotal().subtract(pedidoVOSelecionado.getPagamento().getValorPago()));
		}
	}
}
