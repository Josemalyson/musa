package com.br.musa.conntrolador;

import java.text.ParseException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;

import com.br.musa.constantes.MsgConstantes;
import com.br.musa.entidades.Cliente;
import com.br.musa.servicos.ClienteServico;
import com.br.musa.util.MascaraUtil;
import com.br.musa.util.ObjetoUtil;

@ManagedBean
@ViewScoped
public class ClienteControlador extends CoreControlador {

	private static final long serialVersionUID = 921184405757324590L;

	//Servicos
	@Inject
	private ClienteServico clienteServico;
	
	//LISTAS
	private List<Cliente> clientesList;
	
	//OBJETOS
	
	private Cliente cliente;
	
	@PostConstruct
	public void init() {
		cliente = new Cliente();
		listarTodosOsClientes();
		
	}

	private void listarTodosOsClientes() {
		clientesList = clienteServico.listarTodosClientes();
	}
	
	public String adiconarMascaraCpf(Cliente cliente){
		try {
			return MascaraUtil.adicionarMascara(cliente.getCpf(), MascaraUtil.CPF);
		} catch (ParseException e) {
			e.printStackTrace();
			return new String();
		}
	}
	public String adiconarMascaraRg(Cliente cliente){
		try {
			return MascaraUtil.adicionarMascara(cliente.getRg(), "###.###-#");
		} catch (ParseException e) {
			e.printStackTrace();
			return new String();
		}
	}

	
	public void editar(Cliente cliente){
		clienteServico.salvar(cliente);
		adicionarMensagem(MsgConstantes.MSG_ALTERACAO_SUCESSO);
		listarTodosOsClientes();
		RequestContext.getCurrentInstance().update("listarClientes");
	}

	public void excluir(){
	
		if (ObjetoUtil.notBlank(cliente)) {
			clienteServico.excluir(cliente);
			adicionarMensagem(MsgConstantes.MSG_ALTERACAO_SUCESSO);
			listarTodosOsClientes();
			RequestContext.getCurrentInstance().update("@form");
		}else {
			adicionarErro(MsgConstantes.MSG_ERRO);
		}
		
	}

	public void salvarCliente(){
		cliente.setCpf(MascaraUtil.removerMascara(cliente.getCpf()));
		cliente.setRg(MascaraUtil.removerMascara(cliente.getRg()));
		clienteServico.salvar(cliente);
		adicionarMensagem(MsgConstantes.MSG_SUCESSO);
		cliente = new Cliente();
		listarTodosOsClientes();
		RequestContext.getCurrentInstance().update("tabelaCliente");
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

}
