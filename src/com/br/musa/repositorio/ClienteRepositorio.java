package com.br.musa.repositorio;

import javax.ejb.Stateless;

import com.br.musa.dao.CustomGenericDAOImpl;
import com.br.musa.entidades.Cliente;
import com.br.musa.generics.GenericEntity;

@Stateless
public class ClienteRepositorio extends CustomGenericDAOImpl<Cliente>{

	private static final long serialVersionUID = 1L;

	@Override
	public Class<? extends GenericEntity> obterClasse() {
		return Cliente.class;
	}

}
