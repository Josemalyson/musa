package com.br.musa.servicos;

import java.util.List;

import javax.inject.Inject;

import com.br.musa.entidades.Cliente;
import com.br.musa.entidades.Contato;
import com.br.musa.repositorio.ContatoRepositorio;

public class ContatoServico {

	@Inject
	private ContatoRepositorio contatoRepositorio;
	
	public void salvar(Contato contato){
		contatoRepositorio.salvar(contato);
	}
	
	public List<Contato> listarContatosClienteNaoExcluido(Cliente cliente){
		return contatoRepositorio.listarContatosClienteNaoExcluido(cliente);
	}
}
