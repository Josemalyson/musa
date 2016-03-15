package com.br.musa.repositorio;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import com.br.musa.dao.CustomGenericDAOImpl;
import com.br.musa.entidades.Pedido;
import com.br.musa.generics.GenericEntity;

@Stateless
public class PedidoRepositorio extends CustomGenericDAOImpl<Pedido> {

	private static final long serialVersionUID = 1L;

	@Override
	public Class<? extends GenericEntity> obterClasse() {
		return Pedido.class;
	}

	public Long obterNumeroDoProximoPedido() {
		StringBuilder consulta = new StringBuilder();
		consulta.append(" SELECT MAX(P.ID_PEDIDO) FROM TB_MUSA_PEDIDO P ");
		Query query = obterEntityManager().createNativeQuery(consulta.toString());
		
		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException | NonUniqueResultException e) {
			return null;
		}
		
	}


}