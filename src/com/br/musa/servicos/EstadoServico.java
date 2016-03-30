package com.br.musa.servicos;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.br.musa.entidades.Estado;
import com.br.musa.repositorio.EstadosRepositorio;

public class EstadoServico implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6626545302188048032L;
	@Inject
	private EstadosRepositorio estadosRepositorio;

	public List<Estado> listarEstados() {
		return estadosRepositorio.listar();
	}

	public Estado buscarPorCodigo(Long codigo){
		return listarEstados().stream().filter(estado -> estado.getId().equals(codigo)).findFirst().orElse(null);
	}
}
