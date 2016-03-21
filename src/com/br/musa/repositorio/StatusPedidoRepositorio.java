package com.br.musa.repositorio;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import com.br.musa.dao.CustomGenericDAOImpl;
import com.br.musa.entidades.StatusPedido;
import com.br.musa.generics.GenericEntity;

@Stateless
public class StatusPedidoRepositorio extends CustomGenericDAOImpl<StatusPedido> {

	private static final long serialVersionUID = 1L;

	@Override
	public Class<? extends GenericEntity> obterClasse() {
		return StatusPedido.class;
	}

	public StatusPedido buscarPorCodigo(Long codigo) {
		StringBuilder consulta = new StringBuilder();
		consulta.append(" SELECT SP.* FROM MUSA.TB_MUSA_STATUS_PEDIDO SP WHERE SP.ID_STATUS = :codigo");
		Query query = obterEntityManager().createNativeQuery(consulta.toString(), StatusPedido.class);
		query.setParameter("codigo", codigo);
		
		try {
			return (StatusPedido) query.getSingleResult();
		} catch (NoResultException | NonUniqueResultException e) {
			return null;
		}
		
	}

}