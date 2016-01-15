package com.br.musa.servicos;

import java.util.List;

import javax.inject.Inject;

import com.br.musa.entidades.Estado;
import com.br.musa.repositorio.EstadosRepositorio;

public class EstadoServico {

	@Inject
	private EstadosRepositorio estadosRepositorio;

	public List<Estado> listarEstados() {
		return estadosRepositorio.listar();
	}

}
