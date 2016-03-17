package com.br.musa.controlador;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.br.musa.entidades.Cliente;
import com.br.musa.entidades.Pedido;
import com.br.musa.entidades.Vo.PedidoVO;
import com.br.musa.entidades.Vo.ProdutoVO;
import com.br.musa.enums.TipoPedidoEnum;
import com.br.musa.exeption.MusaExecao;
import com.br.musa.servicos.ClienteServico;
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
	// OBJETOS
	private Cliente cliente;
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
		pedidolist = pedidoServico.listar();
	}

	private void montarPedidosVO() {
		pedidoVOlist = new ArrayList<PedidoVO>();
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

	}

	public List<Cliente> autoCompleteCliente(String query) {
		List<Cliente> clienteFiltradosList = new ArrayList<Cliente>();

		for (int i = 0; i < clienteList.size(); i++) {
			Cliente cliente = clienteList.get(i);
			if (cliente.getNome().toLowerCase().startsWith(query)) {
				clienteFiltradosList.add(cliente);
			}
		}

		return clienteFiltradosList;
	}

	public void buscarPedidosPorCliente(){
		try {
			pedidolist = new ArrayList<Pedido>();
			
			if (cliente != null) {
				pedidolist = pedidoServico.listarPedidosPorCliente(cliente.getId());
			}else {
				listarPedido();
			}
			montarPedidosVO();
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

}