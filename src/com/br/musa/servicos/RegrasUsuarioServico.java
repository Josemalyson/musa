package com.br.musa.servicos;

import java.util.List;

import javax.inject.Inject;

import com.br.musa.entidades.RegrasUsuario;
import com.br.musa.repositorio.RegraUsuarioRepositorio;

public class RegrasUsuarioServico {

	@Inject
	private RegraUsuarioRepositorio regraUsuarioRepositorio;
	
	
	public List<RegrasUsuario> listar(){
		return regraUsuarioRepositorio.listar();
	}
}
