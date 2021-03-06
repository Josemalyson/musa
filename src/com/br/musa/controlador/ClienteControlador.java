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
import com.br.musa.entidades.Cidade;
import com.br.musa.entidades.Cliente;
import com.br.musa.entidades.Contato;
import com.br.musa.entidades.Endereco;
import com.br.musa.entidades.Estado;
import com.br.musa.exeption.MusaExecao;
import com.br.musa.servicos.CidadeServico;
import com.br.musa.servicos.ClienteServico;
import com.br.musa.servicos.EstadoServico;

@ManagedBean
@ViewScoped
public class ClienteControlador extends CoreControlador {
	private static final long serialVersionUID = 4425238557135997397L;

	// SERVICOS
	@Inject
	private ClienteServico clienteServico;
	@Inject
	private EstadoServico estadoServico;
	@Inject
	private CidadeServico cidadeServico;

	// OBJETOS
	private Cliente cliente;
	private Date dataMaxima;
	private Contato contato;

	// LISTA
	private List<Estado> estadoList;
	private List<Cidade> cidadeList;
	
	private static final Logger logger = Logger.getLogger(ClienteControlador.class);

	@PostConstruct
	public void init() {
		
		cliente = (Cliente) obterAtributoFlash("cliente");

		if (cliente == null) {
			montarNovoCliente();
		}

		dataMaxima = new Date();
		cidadeList = new ArrayList<>();
		listarEstados();
		listarCidadesPorEstados();
		listarContatosdoCliente();
	}

	private void listarContatosdoCliente() {

		if (cliente.getContatoList() == null || cliente.getContatoList().isEmpty()) {
			cliente.setContatoList(new ArrayList<Contato>());
		}

	}

	private void montarNovoCliente() {
		cliente = new Cliente();
		Endereco endereco = new Endereco();
		contato = new Contato();

		cliente.setEndereco(endereco);
		cliente.getEndereco().setCliente(cliente);

		cliente.setContatoList(new ArrayList<Contato>());
		dataMaxima = new Date();
		listarEstados();
	}

	private void listarEstados() {
		estadoList = estadoServico.listarEstados();
	}

	public String salvarCliente() {

		try {
			clienteServico.salvar(cliente);
			RequestContext.getCurrentInstance().update("tabelaCliente");
			adicionarMensagem(MsgConstantes.MSG_SUCESSO);
			return sendRedirect(Constantes.PAGINA_LISTAR_CLIENTES);

		} catch (MusaExecao e) {
			logger.error(e.getMessage(),e);
			adicionarErro(e.getMessage());
		}
		return Constantes.STRING_VAZIA;
	}

	public void adiconarContato() {
		try {
			clienteServico.adicionarContatos(contato, cliente);
			contato = new Contato();
			RequestContext.getCurrentInstance().update("contatosCliente");
			RequestContext.getCurrentInstance().execute("PF('incluirContato').hide()");
			adicionarMensagem(MsgConstantes.MSG_SUCESSO);
		} catch (MusaExecao e) {
			logger.error(e.getMessage(),e);
			adicionarErro(e.getMessage());
			return;
		}
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
		clienteServico.excluirContato(cliente, contato);
		adicionarMensagem(MsgConstantes.MSG_EXCLUSAO_SUCESSO);
		RequestContext.getCurrentInstance().update("contatosCliente");
	}

	public void listarCidadesPorEstados() {
		clienteServico.montarEstadoECidadePadrao(cliente);
		cidadeList = cidadeServico.listarCidadesPorEstados(cliente.getEndereco().getEstado());
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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

	public List<Cidade> getCidadeList() {
		return cidadeList;
	}

	public void setCidadeList(List<Cidade> cidadeList) {
		this.cidadeList = cidadeList;
	}
}