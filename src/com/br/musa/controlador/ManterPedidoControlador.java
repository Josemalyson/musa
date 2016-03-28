package com.br.musa.controlador;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;
import org.primefaces.context.RequestContext;

import com.br.musa.constantes.Constantes;
import com.br.musa.constantes.MsgConstantes;
import com.br.musa.entidades.Cliente;
import com.br.musa.entidades.Pagamento;
import com.br.musa.entidades.Pedido;
import com.br.musa.entidades.Vo.PedidoVO;
import com.br.musa.exeption.MusaExecao;
import com.br.musa.servicos.ClienteServico;
import com.br.musa.servicos.PagamentoServico;
import com.br.musa.servicos.PedidoServico;

@ManagedBean
@ViewScoped
public class ManterPedidoControlador extends CoreControlador {

	private static final long serialVersionUID = 6521259252524016427L;

	private static final Logger logger = Logger.getLogger(ManterPedidoControlador.class);

	// SERVICOS
	@Inject
	private PedidoServico pedidoServico;
	@Inject
	private ClienteServico clienteServico;
	@Inject
	private PagamentoServico pagamentoServico;
	// OBJETOS
	private Cliente cliente;
	private PedidoVO pedidoVOSelecionado;
	// LISTAS
	private List<PedidoVO> pedidoVOlist;
	private List<Pedido> pedidolist;
	private List<Cliente> clienteList;

	@PostConstruct
	public void init() {
		listarPedido();
		listarClientes();
		montarPedidosVO();
		cliente = new Cliente();

	}

	private void listarClientes() {
		clienteList = clienteServico.listarTodosClientes();
	}

	private void listarPedido() {
		pedidolist = pedidoServico.listarNaoExcluidos();
	}

	private void montarPedidosVO() {
		pedidoVOlist = pedidoServico.montartPedidosVO(pedidolist);

	}

	public List<Cliente> autoCompleteCliente(String query) {
		return clienteServico.autoCompleteClienteServico(query, clienteList);
	}

	public void buscarPedidosPorCliente() {
		try {
			pedidolist = new ArrayList<Pedido>();

			if (cliente != null) {
				pedidolist = pedidoServico.listarPedidosPorCliente(cliente.getId());
			} else {
				pedidolist = pedidoServico.listarPedidosSemCliente();
			}
			montarPedidosVO();
		} catch (MusaExecao e) {
			logger.error(e.getMessage(), e);
			adicionarErro(e.getMessage());
		}
	}

	public String editarPedido(Pedido pedido) {
		adicionarAtributoFlash("pedido", pedido);
		return sendRedirect(Constantes.PAGINA_PEDIDO);
	}

	public void excluir(Pedido pedido) {
		try {
			pedidoServico.excluir(pedido);
			listarPedido();
			montarPedidosVO();
			adicionarMensagem(MsgConstantes.MSG_SUCESSO);
		} catch (MusaExecao e) {
			logger.error(e.getMessage(), e);
			adicionarErro(e.getMessage());
		}

	}

	public void selecionarPedidoVO(PedidoVO pedidoVO) {
		this.pedidoVOSelecionado = new PedidoVO();
		this.pedidoVOSelecionado = pedidoVO;
		Pagamento pagamento = new Pagamento();
		pagamento.setDtPagamento(new Date());
		pagamento.setPedido(pedidoVO.getPedido());
		pagamento.setValorRestante(pedidoVO.getPedido().getValorTotal());
		this.pedidoVOSelecionado.setPagamento(pagamento);
		
	}

	public void efetuarPagamento() {
		try {
			pagamentoServico.salvar(this.pedidoVOSelecionado.getPagamento());
			adicionarMensagem(MsgConstantes.MSG_SUCESSO);
			RequestContext.getCurrentInstance().update("tabelaPedido");
			RequestContext.getCurrentInstance().execute("PF('efetuarPagamento').hide();");
		} catch (MusaExecao e) {
			logger.error(e.getMessage(), e);
			adicionarErro(e.getMessage());
		}
	}

	public List<PedidoVO> getPedidoVOlist() {
		return pedidoVOlist;
	}

	public void setPedidoVOlist(List<PedidoVO> pedidoVOlist) {
		this.pedidoVOlist = pedidoVOlist;
	}

	public List<Cliente> getClienteList() {
		return clienteList;
	}

	public void setClienteList(List<Cliente> clienteList) {
		this.clienteList = clienteList;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public PedidoVO getPedidoVOSelecionado() {
		return pedidoVOSelecionado;
	}

	public void setPedidoVOSelecionado(PedidoVO pedidoVOSelecionado) {
		this.pedidoVOSelecionado = pedidoVOSelecionado;
	}

}