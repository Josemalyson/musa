package com.br.musa.repositorio;

import javax.ejb.Stateless;

import com.br.musa.dao.CustomGenericDAOImpl;
import com.br.musa.entidades.Pagamento;
import com.br.musa.generics.GenericEntity;

@Stateless
public class PagamentoRepositorio extends CustomGenericDAOImpl<Pagamento> {

	private static final long serialVersionUID = 1L;

	@Override
	public Class<? extends GenericEntity> obterClasse() {
		return Pagamento.class;
	}

}