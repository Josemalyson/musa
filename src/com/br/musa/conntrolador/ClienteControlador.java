package com.br.musa.conntrolador;


import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.br.musa.constantes.Constantes;
import com.br.musa.constantes.MsgConstantes;
import com.br.musa.entidades.Cliente;
import com.br.musa.util.MascaraUtil;

@ManagedBean
@ViewScoped
public class ClienteControlador extends ManterClienteControlador {
	private static final long serialVersionUID = 4425238557135997397L;
	
	
	private Cliente novocliente;
	private Date dataMaxima;
	
	@PostConstruct
	public void init(){
		novocliente = new Cliente();
		dataMaxima = new Date();
	}
	
	

	public String salvarCliente() {

		novocliente.setCpf(MascaraUtil.removerMascara(novocliente.getCpf()));
		novocliente.setRg(MascaraUtil.removerMascara(novocliente.getRg()));
		novocliente.setFlExcluido(false);
		
		getClienteServico().salvar(novocliente);
		
		adicionarMensagem(MsgConstantes.MSG_SUCESSO);
		RequestContext.getCurrentInstance().update("tabelaCliente");
		return sendRedirect(Constantes.PAGINA_LISTAR_CLIENTES);
		
	}



	public Cliente getNovocliente() {
		return novocliente;
	}



	public void setNovocliente(Cliente novocliente) {
		this.novocliente = novocliente;
	}



	public Date getDataMaxima() {
		return dataMaxima;
	}



	public void setDataMaxima(Date dataMaxima) {
		this.dataMaxima = dataMaxima;
	}
}
