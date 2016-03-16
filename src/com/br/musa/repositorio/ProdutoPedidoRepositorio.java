package com.br.musa.repositorio;

import javax.ejb.Stateless;

import com.br.musa.dao.CustomGenericDAOImpl;
import com.br.musa.entidades.ProdutoPedido;
import com.br.musa.generics.GenericEntity;

@Stateless
public class ProdutoPedidoRepositorio extends CustomGenericDAOImpl<ProdutoPedido> {

	private static final long serialVersionUID = 1L;

	@Override
	public Class<? extends GenericEntity> obterClasse() {
		return ProdutoPedido.class;
	}

}