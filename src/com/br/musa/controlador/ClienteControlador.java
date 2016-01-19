package com.br.musa.controlador;

import java.util.ArrayList;
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
import com.br.musa.entidades.Endereco;
import com.br.musa.entidades.Estado;
import com.br.musa.servicos.ClienteServico;
import com.br.musa.servicos.EstadoServico;
import com.br.musa.util.MascaraUtil;

@ManagedBean
@ViewScoped
public class ClienteControlador extends CoreControlador {
	private static final long serialVersionUID = 4425238557135997397L;

	// SERVICOS
	@Inject
	private ClienteServico clienteServico;
	@Inject
	private EstadoServico estadoServico;

	// OBJETOS
	private Cliente novocliente;
	private Date dataMaxima;
	private Contato contato;

	// LISTA
	private List<Estado> estadoList;

	@PostConstruct
	public void init() {
		novocliente = new Cliente();
		Endereco endereco = new Endereco();
		novocliente.setEndereco(endereco);
		novocliente.setContatoList(new ArrayList<Contato>());
		dataMaxima = new Date();
		contato = new Contato();
		listarEstados();
	}

	private void listarEstados() {
		estadoList = estadoServico.listarEstados();

	}

	public String salvarCliente() {

		novocliente.setCpf(MascaraUtil.removerMascara(novocliente.getCpf()));
		novocliente.setRg(MascaraUtil.removerMascara(novocliente.getRg()));
		novocliente.setFlExcluido(false);
		clienteServico.salvar(novocliente);

		adicionarMensagem(MsgConstantes.MSG_SUCESSO);
		RequestContext.getCurrentInstance().update("tabelaCliente");
		return sendRedirect(Constantes.PAGINA_LISTAR_CLIENTES);

	}

	public void adiconarContato() {
		novocliente.getContatoList().add(contato);
		contato = new Contato();
		adicionarMensagem(MsgConstantes.MSG_SUCESSO);
		RequestContext.getCurrentInstance().update("contatosCliente");
		RequestContext.getCurrentInstance().execute("PF('incluirContato').hide()");

	}

	public void limparModalContato() {
		contato = new Contato();
		RequestContext.getCurrentInstance().update("incluirContato");
	}

	public void editarContato(Contato contatoSelecionado) {
		contato = new Contato();
		contato = contatoSelecionado;
		RequestContext.getCurrentInstance().execute("PF('incluirContato').show()");
		RequestContext.getCurrentInstance().update("incluirContato");
	}

	public void excluirContato() {
		novocliente.getContatoList().remove(contato);
		adicionarMensagem(MsgConstantes.MSG_EXCLUSAO_SUCESSO);
		RequestContext.getCurrentInstance().update("tabelaContato");
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

	public Contato getContato() {
		return contato;
	}

	public void setContato(Contato contato) {
		this.contato = contato;
	}

	public List<Estado> getEstadoList() {
		return estadoList;
	}

	public void setEstadoList(List<Estado> estadoList) {
		this.estadoList = estadoList;
	}
}
