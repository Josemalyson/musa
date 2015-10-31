package com.br.musa.controlador;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;

import com.br.musa.constantes.Constantes;
import com.br.musa.constantes.MsgConstantes;
import com.br.musa.entidades.Cliente;
import com.br.musa.entidades.Contato;
import com.br.musa.servicos.ClienteServico;
import com.br.musa.servicos.ContatoServico;
import com.br.musa.util.MascaraUtil;

@ManagedBean
@ViewScoped
public class EditarClienteControlador extends CoreControlador {

	private static final long serialVersionUID = 921184405757324590L;

	
	//SERVICOS
	@Inject
	private ClienteServico clienteServico;
	@Inject
	private ContatoServico contatoServico;
	
	// OBJETOS
	private Cliente clienteSelecionado;
	private Contato contato;
	private Date dataMaxima;
	
	//LISTA
	private List<Contato> contatoList;

	@PostConstruct
	public void init() {
		clienteSelecionado = (Cliente) obterAtributoFlash("cliente");
		dataMaxima = new Date();
		contato = new Contato();
		contatoList = contatoServico.listarContatosClienteNaoExcluido(clienteSelecionado);
	}




	public void adiconarContato(){
		
		
		if (contato.getId() == null && contato.getCliente() == null) {
			contato.setCliente(clienteSelecionado);
			contato.setFlExcluido(false);
			contatoList.add(contato);
		}else {
			Contato contatoAux = contato;
			if (contatoList.contains(contato)) {
				contatoList.remove(contato);
				contatoList.add(contatoAux);
			}
		}
		
		contato = new Contato();
		adicionarMensagem(MsgConstantes.MSG_SUCESSO);
		RequestContext.getCurrentInstance().update("tabelaContato");
		RequestContext.getCurrentInstance().execute("PF('incluirContato').hide()");
	
	}
	
	
	public void limparModalContato(){
		contato = new Contato();
		RequestContext.getCurrentInstance().update("incluirContato");
	}
	
	
	public void editarContato(Contato contatoSelecionado){
		contato = new Contato();
		contato = contatoSelecionado;
		RequestContext.getCurrentInstance().execute("PF('incluirContato').show()");
		RequestContext.getCurrentInstance().update("incluirContato");
	}
	
	//TODO VERIFICAR METODO EXLUCIR NAO ESTA FUNCIONADO
	
	public void excluirContato(){

		
		if (contato.getId() != null) {
			contato.setFlExcluido(true);
			clienteSelecionado.getContatoList().add(contato);
		}

		contatoList.remove(contato);
		
		adicionarMensagem(MsgConstantes.MSG_EXCLUSAO_SUCESSO);
		RequestContext.getCurrentInstance().update("tabelaContato");
	}
	
	public String salvarCliente() {

		clienteSelecionado.setCpf(MascaraUtil.removerMascara(clienteSelecionado.getCpf()));
		clienteSelecionado.setRg(MascaraUtil.removerMascara(clienteSelecionado.getRg()));
		
		if (!contatoList.isEmpty()) {
			clienteSelecionado.getContatoList().addAll(contatoList);
		}
		
		clienteServico.salvar(clienteSelecionado);
		
		adicionarMensagem(MsgConstantes.MSG_SUCESSO);
		RequestContext.getCurrentInstance().update("tabelaCliente");
		return sendRedirect(Constantes.PAGINA_LISTAR_CLIENTES);
		
	}

	public Cliente getClienteSelecionado() {
		return clienteSelecionado;
	}


	public void setClienteSelecionado(Cliente clienteSelecionado) {
		this.clienteSelecionado = clienteSelecionado;
	}


	public Date getDataMaxima() {
		return dataMaxima;
	}


	public void setDataMaxima(Date dataMaxima) {
		this.dataMaxima = dataMaxima;
	}


	public Contato getContato() {
		return contato;
	}


	public void setContato(Contato contato) {
		this.contato = contato;
	}


	public List<Contato> getContatoList() {
		return contatoList;
	}


	public void setContatoList(List<Contato> contatoList) {
		this.contatoList = contatoList;
	}

}
