package com.br.musa.repositorio;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.jboss.logging.Logger;

import com.br.musa.dao.CustomGenericDAOImpl;
import com.br.musa.entidades.Pedido;
import com.br.musa.generics.GenericEntity;

@Stateless
@SuppressWarnings("unchecked")
public class PedidoRepositorio extends CustomGenericDAOImpl<Pedido> {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(PedidoRepositorio.class);
	
	@Override
	public Class<? extends GenericEntity> obterClasse() {
		return Pedido.class;
	}

	public BigInteger obterNumeroDoProximoPedido() {
		StringBuilder consulta = new StringBuilder();
		consulta.append(" SELECT MAX(P.ID_PEDIDO) FROM TB_MUSA_PEDIDO P ");
		Query query = obterEntityManager().createNativeQuery(consulta.toString());
		
		try {
			return (BigInteger) query.getSingleResult();
		} catch (NoResultException | NonUniqueResultException e) {
			logger.warn(e.getMessage(),e);
			return null;
		}
		
	}

	public List<Pedido> buscarPedidoPorProduto(Long codigoProdudo) {
		StringBuilder consulta = new StringBuilder();
		consulta.append(" SELECT PP.* FROM MUSA.TB_MUSA_PRODUTO_PEDIDO PP WHERE PP.FK_PRODUTO = :codigoProdudo ");
		Query query = obterEntityManager().createNativeQuery(consulta.toString(), Pedido.class);
		query.setParameter("codigoProdudo", codigoProdudo);
		return query.getResultList();
	}

	public List<Pedido> listarPedidosPorCliente(Long id) {
		StringBuilder consulta = new StringBuilder();
		consulta.append(" SELECT P.* FROM MUSA.TB_MUSA_PEDIDO P WHERE P.FK_CLIENTE = :id ");
		Query query = obterEntityManager().createNativeQuery(consulta.toString(), Pedido.class);
		query.setParameter("id", id);
		return query.getResultList();
	}

	public List<Pedido> listarPedidosSemCliente() {
		StringBuilder consulta = new StringBuilder();
		consulta.append(" SELECT P.* FROM TB_MUSA_PEDIDO P WHERE P.FK_CLIENTE IS NULL ");
		Query query = obterEntityManager().createNativeQuery(consulta.toString(), Pedido.class);
		return query.getResultList();
	}

	public List<Pedido> listarNaoExcluidos() {
		StringBuilder consulta = new StringBuilder();
		consulta.append(" SELECT P.* FROM MUSA.TB_MUSA_PEDIDO P WHERE P.FL_EXCLUIDO = 0 ORDER BY P.DT_PEDIDO DESC ");
		Query query = obterEntityManager().createNativeQuery(consulta.toString(), Pedido.class);
		return query.getResultList();
	}


}