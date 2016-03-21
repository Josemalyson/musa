package com.br.musa.repositorio;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import com.br.musa.dao.CustomGenericDAOImpl;
import com.br.musa.entidades.TipoPedido;
import com.br.musa.generics.GenericEntity;

@Stateless
public class TipoPedidoRepositorio extends CustomGenericDAOImpl<TipoPedido> {

	private static final long serialVersionUID = 1L;

	@Override
	public Class<? extends GenericEntity> obterClasse() {
		return TipoPedido.class;
	}

	public TipoPedido buscarPorCodigo(Long codigo) {
		StringBuilder consulta = new StringBuilder();
		consulta.append(" SELECT TP.* FROM TB_MUSA_TIPO_PEDIDO TP WHERE TP.ID_TIPO_PEDIDO = :codigo ");
		Query query = obterEntityManager().createNativeQuery(consulta.toString(), TipoPedido.class);
		query.setParameter("codigo", codigo);
		
		
		try {
			return (TipoPedido) query.getSingleResult();
		} catch (NoResultException | NonUniqueResultException e) {
			return null;
		}
	}


}