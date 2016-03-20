package com.br.musa.servicos;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.jboss.logging.Logger;

import com.br.musa.constantes.Constantes;
import com.br.musa.constantes.MsgConstantes;
import com.br.musa.entidades.Cliente;
import com.br.musa.entidades.Pedido;
import com.br.musa.entidades.Produto;
import com.br.musa.entidades.ProdutoPedido;
import com.br.musa.entidades.ProdutoPedidoPK;
import com.br.musa.entidades.Vo.PedidoVO;
import com.br.musa.entidades.Vo.ProdutoVO;
import com.br.musa.enums.TipoPedidoEnum;
import com.br.musa.exeption.CalculadoraExecao;
import com.br.musa.exeption.MusaExecao;
import com.br.musa.repositorio.PedidoRepositorio;
import com.br.musa.util.CalcularUtil;
import com.br.musa.util.JavaScriptUtil;

public class PedidoServico {

	@Inject
	private PedidoRepositorio pedidoRepositorio;
	@Inject
	private ProdutoPedidoServico produtoPedidoServico;
	@Inject
	private ClienteServico clienteServico;
	@Inject
	private ProdutoServico produtoServico;
	
	private static final Logger logger = Logger.getLogger(PedidoServico.class);

	public void validarQuantidadeDoProduto(ProdutoVO produtoVO) {
		if (produtoVO != null && produtoVO.getQuantidadeProduto() != null && produtoVO.getQuantidadeProduto() <= 0) {
			// JavaScriptUtil.marcarCampoObrigatorio("tabelaProdutoVO:0:quantidadeId");
			throw new MusaExecao(MsgConstantes.ERRO_QUANTIDADE_ZERO);
		}

		if (produtoVO.getQuantidadeProduto() == null) {
			produtoVO.setQuantidadeProduto(new Long(1));
		}
	}

	public BigInteger obterNumerorDoProximoPedido() {
		return pedidoRepositorio.obterNumeroDoProximoPedido() == null ? new BigInteger("1")
				: pedidoRepositorio.obterNumeroDoProximoPedido().add(new BigInteger("1"));
	}

	public List<Pedido> listar() {
		return pedidoRepositorio.listar();
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

		if (pedido.getId() == null) {
			pedidoVO.setCliente(cliente);
			pedidoVO.setNumeroPedido(obterNumerorDoProximoPedido().toString());
			pedidoVO.setPedido(pedido);
			pedidoVO.setProdutoVOList(new ArrayList<ProdutoVO>());
		} else {
			pedidoVO.setPedido(pedido);
			pedidoVO.setCliente(pedido.getCliente());
			pedidoVO.setNumeroPedido(pedido.getId().toString());

			List<ProdutoVO> produtoVOList = new ArrayList<ProdutoVO>();

			List<Produto> produtoBDList = new ArrayList<Produto>();
			produtoBDList = produtoServico.listarProdutosPorPedido(pedido.getId());

			for (Produto produto : produtoBDList) {
				ProdutoVO produtoVO = new ProdutoVO();
				produtoVO.setProduto(produto);
				produtoVO.setQuantidadeProduto(
						produtoPedidoServico.buscarPedidoPorPedido(pedido.getId(), produto.getId()).getQtdProduto());
				produtoVOList.add(produtoVO);

			}

			pedidoVO.setProdutoVOList(produtoVOList);
		}
		return pedidoVO;
	}

	public void adicionarProduto(PedidoVO pedidoVO, Produto produto) {

		if (pedidoVO != null && pedidoVO.getProdutoVOList() != null) {
			ProdutoVO produtoVO = new ProdutoVO();
			produtoVO.setQuantidadeProduto(new Long(1));
			produto.setPrecoCusto(new BigDecimal(0));
			produto.setPrecoVenda(new BigDecimal(0));
			produtoVO.setProduto(produto);
			pedidoVO.getProdutoVOList().add(0, produtoVO);
		}
		calcularTotal(pedidoVO);
	}

	public void calcularTotal(PedidoVO pedidoVO) {
		calcularTotalCusto(pedidoVO);
		calcularTotalVenda(pedidoVO);
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

	public List<PedidoVO> montartPedidosVO(List<Pedido> pedidolist) {
		List<PedidoVO> pedidoVOlist = new ArrayList<PedidoVO>();
		for (Pedido pedido : pedidolist) {
			PedidoVO pedidoVO = new PedidoVO();

			pedidoVO.setPedido(pedido);
			pedidoVO.setCliente(pedido.getCliente());
			pedidoVO.setNumeroPedido(pedido.getId().toString());
			pedidoVO.setProdutoVOList(new ArrayList<ProdutoVO>());

			if (pedido.getTipoPedido().getId().equals(TipoPedidoEnum.ATACADO.getCodigo())) {
				pedidoVO.setTotalPedio(new Double(pedido.getNuTotalCusto().toString()));
			} else {
				pedidoVO.setTotalPedio(new Double(pedido.getNuTotalVenda().toString()));
			}

			pedidoVOlist.add(pedidoVO);
		}

		return pedidoVOlist;
	}

	public List<Pedido> listarPedidosSemCliente() {
		return pedidoRepositorio.listarPedidosSemCliente();
	}

	public void verificarSeExisteProdutosCadastrados() {
		if (produtoServico.listarProdutosAtivos() == null || produtoServico.listarProdutosAtivos().isEmpty()
				|| produtoServico.listarProdutosAtivos().size() < 0) {
			throw new MusaExecao(MsgConstantes.NAO_EXISTE_PRODUTOS_CADASTRADOS);
		}

	}

	public void calcularDesconto(PedidoVO pedidoVO) {
		try {
			pedidoVO.getPedido().setNuTotalCusto(CalcularUtil.calcularDesconto(pedidoVO.getPedido().getNuTotalCusto(),
					pedidoVO.getPedido().getDesconto()));
			pedidoVO.getPedido().setNuTotalVenda(CalcularUtil.calcularDesconto(pedidoVO.getPedido().getNuTotalVenda(),
					pedidoVO.getPedido().getDesconto()));
		} catch (CalculadoraExecao e) {
			logger.error(e.getMessage(), e);
			throw new MusaExecao(MsgConstantes.PEDIDO_COM_VALOR_ZER0);
			
		}
	}
}
