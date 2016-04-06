package com.br.musa.servicos;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import org.apache.shiro.authc.credential.DefaultPasswordService;

import com.br.musa.constantes.Constantes;
import com.br.musa.constantes.MsgConstantes;
import com.br.musa.entidades.Usuario;
import com.br.musa.exeption.MusaExecao;
import com.br.musa.repositorio.UsuarioRepositorio;

public class UsuarioServico implements Serializable {

	private static final long serialVersionUID = 2899387627659814092L;

	@Inject
	private UsuarioRepositorio usuarioRepositorio;

	public List<Usuario> listar() {
		return usuarioRepositorio.listar();
	}

	public void salvar(Usuario usuario) {
		validarSalvar(usuario);
		usuarioRepositorio.salvar(usuario);

	}

	private void validarSalvar(Usuario usuario) {
		camposObrigatorios(usuario);
		usuario.setNome(removerEspacosDuplicados(usuario.getNome()));
		verifiacarSeLoginDuplicado(usuario);
		criptografarSenhar(usuario);
	}

	private void verifiacarSeLoginDuplicado(Usuario usuario) {
		Usuario usuarioBD = usuarioRepositorio.consultarUsuarioPorNome(usuario.getNome());
		if (isUsuarioValido(usuarioBD)) {
			throw new MusaExecao(MsgConstantes.LOGIN_DUPLICADO);
		}
	}

	private String removerEspacosDuplicados(String palavra) {
		if (palavra != null && !palavra.isEmpty()) {
			return palavra.trim().toLowerCase().replaceAll("\\s+ ", " ");
		}
		return palavra;

	}

	private void criptografarSenhar(Usuario usuario) {
		usuario.setSenha(new DefaultPasswordService().encryptPassword(usuario.getSenha()));
	}

	private void camposObrigatorios(Usuario usuario) {

		StringBuilder erro = new StringBuilder();

		if (isUsuarioValido(usuario)) {

			if (usuario.getNome() == null || usuario.getNome().isEmpty()) {
				erro.append("Preencher o campo Nome").append(Constantes.TAG_BR);
			}

			if (usuario.getSenha() == null || usuario.getSenha().isEmpty()) {
				erro.append("Preencher o campo Senha").append(Constantes.TAG_BR);
			}

			if (usuario.getRegrasUsuario() == null) {
				erro.append("Preencher o campo Perfil do usu�rio").append(Constantes.TAG_BR);
			}
		}

		if (!erro.toString().isEmpty()) {
			throw new MusaExecao(erro.toString());
		}
	}

	private boolean isUsuarioValido(Usuario usuario) {
		return usuario != null;
	}

}