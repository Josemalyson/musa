package com.br.musa.servicos;

import java.util.List;

import javax.inject.Inject;

import com.br.musa.entidades.Cidade;
import com.br.musa.entidades.Estado;
import com.br.musa.repositorio.CidadeRepositorio;

public class CidadeServico {

	@Inject
	private CidadeRepositorio cidadeRepositorio;

	public List<Cidade> listarCidadesPorEstados(Estado estado) {
		return cidadeRepositorio.listarCidadesPorEstados(estado);
	}

}
