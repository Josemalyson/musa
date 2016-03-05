package com.br.musa.servicos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.br.musa.entidades.Cidade;
import com.br.musa.entidades.Estado;
import com.br.musa.repositorio.CidadeRepositorio;

public class CidadeServico {

	@Inject
	private CidadeRepositorio cidadeRepositorio;

	private Map<Long, Cidade> cidadeMap;

	public void listar() {

		if (cidadeMap == null || cidadeMap.isEmpty()) {
			cidadeMap = new HashMap<>();
			cidadeRepositorio.listar().forEach(cidade -> cidadeMap.put(cidade.getId(), cidade));
		}
	}

	public Cidade buscarCidadePorCodigo(Long codigo) {
		listar();
		return cidadeMap.get(codigo) != null ? cidadeMap.get(codigo) : null;
	}

	public List<Cidade> listarCidadesPorEstados(Estado estado) {
		return cidadeRepositorio.listarCidadesPorEstados(estado);
	}

	public Cidade buscarCidadePorCodigoEstado(Long codigo, Estado estado) {
		return listarCidadesPorEstados(estado).stream().filter(cidade -> cidade.getId().equals(codigo)).findFirst()
				.orElse(null);
	}
}