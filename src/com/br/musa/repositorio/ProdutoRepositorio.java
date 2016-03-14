package com.br.musa.repositorio;

import javax.ejb.Stateless;

import com.br.musa.dao.CustomGenericDAOImpl;
import com.br.musa.entidades.Produto;
import com.br.musa.generics.GenericEntity;

@Stateless
public class ProdutoRepositorio extends CustomGenericDAOImpl<Produto> {

	private static final long serialVersionUID = 1L;

	@Override
	public Class<? extends GenericEntity> obterClasse() {
		return Produto.class;
	}


}