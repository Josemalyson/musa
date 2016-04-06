package com.br.musa.controlador;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.br.musa.constantes.MsgConstantes;
import com.br.musa.entidades.RegrasUsuario;
import com.br.musa.entidades.Usuario;
import com.br.musa.exeption.MusaExecao;
import com.br.musa.servicos.RegrasUsuarioServico;
import com.br.musa.servicos.UsuarioServico;

@ManagedBean
@ViewScoped
public class UsuarioControlador extends CoreControlador {

	private static final long serialVersionUID = -3716601852233779221L;

	// SERVICOS
	@Inject
	private RegrasUsuarioServico regrasUsuarioServico;
	@Inject
	private UsuarioServico usuarioServico;

	// OBJETOS
	private Usuario usuario;
	
	//LISTAS
	private List<RegrasUsuario> regrasUsuarioList;

	private static final Logger logger = Logger.getLogger(UsuarioControlador.class);

	@PostConstruct
	public void init() {
		listarRegrasUsuario();
		usuario = new Usuario();
		usuario.setRegrasUsuario(regrasUsuarioList.get(0));
	}

	private void listarRegrasUsuario() {
		regrasUsuarioList = new ArrayList<>();
		regrasUsuarioList = regrasUsuarioServico.listar();
	}
	
	public void salvar(){
		try {
			usuarioServico.salvar(usuario);
			adicionarMensagem(MsgConstantes.MSG_SUCESSO);
		} catch (MusaExecao e) {
			logger.error(e.getMessage(),e);
			adicionarErro(e.getMessage());
		}
	}

	public List<RegrasUsuario> getRegrasUsuarioList() {
		return regrasUsuarioList;
	}

	public void setRegrasUsuarioList(List<RegrasUsuario> regrasUsuarioList) {
		this.regrasUsuarioList = regrasUsuarioList;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}