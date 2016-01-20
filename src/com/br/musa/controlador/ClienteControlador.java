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
import com.br.musa.entidades.Cidade;
import com.br.musa.entidades.Cliente;
import com.br.musa.entidades.Contato;
import com.br.musa.entidades.Endereco;
import com.br.musa.entidades.Estado;
import com.br.musa.servicos.CidadeServico;
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
	@Inject
	private CidadeServico cidadeServico;

	// OBJETOS
	private Cliente cliente;
	private Date dataMaxima;
	private Contato contato;

	// LISTA
	private List<Estado> estadoList;
	private List<Cidade> cidadeList;

	@PostConstruct
	public void init() {

		cliente = (Cliente) obterAtributoFlash("cliente");

		if (cliente == null) {
			montarNovoCliente();
		}

		dataMaxima = new Date();
		cidadeList = new ArrayList<Cidade>();
		listarEstados();
		listarCidadesPorEstados();
		listarContatosdoCliente();
	}

	private void listarContatosdoCliente() {

		if (cliente.getContatoList() == null || cliente.getContatoList().size() == 0) {
			cliente.setContatoList(new ArrayList<Contato>());
		}

	}

	private void montarNovoCliente() {
		cliente = new Cliente();
		Endereco endereco = new Endereco();
		cliente.setEndereco(endereco);
		cliente.setContatoList(new ArrayList<Contato>());
		dataMaxima = new Date();
		contato = new Contato();
		listarEstados();
	}

	private void listarEstados() {
		estadoList = estadoServico.listarEstados();

	}

	public String salvarCliente() {

		cliente.setCpf(MascaraUtil.removerMascara(cliente.getCpf()));
		cliente.setRg(MascaraUtil.removerMascara(cliente.getRg()));
		cliente.setFlExcluido(false);
		clienteServico.salvar(cliente);

		adicionarMensagem(MsgConstantes.MSG_SUCESSO);
		RequestContext.getCurrentInstance().update("tabelaCliente");
		return sendRedirect(Constantes.PAGINA_LISTAR_CLIENTES);

	}

	public void adiconarContato() {
		contato.setCliente(cliente);
		contato.setFlExcluido(false);

		if (!cliente.getContatoList().contains(contato)) {
			cliente.getContatoList().add(contato);
		}

		cliente.getContatoList().add(contato);
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
		cliente.getContatoList().remove(contato);
		cliente.getContatoList().remove(contato);
		adicionarMensagem(MsgConstantes.MSG_EXCLUSAO_SUCESSO);
		RequestContext.getCurrentInstance().update("contatosCliente");
	}

	public void listarCidadesPorEstados() {
		if (cliente.getEndereco().getEstado() == null) {
			Estado estado = new Estado();
			estado.setId(new Long(16));
			cliente.getEndereco().setEstado(estado);
		}

		cidadeList = cidadeServico.listarCidadesPorEstados(cliente.getEndereco().getEstado());
		
		if (cliente.getEndereco().getCidade() == null) {
			cliente.getEndereco().setCidade(cidadeList.stream().filter(c -> c.getId().equals(Constantes.ID_JOAO_PESSOA)).findFirst().get());
		}
		
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