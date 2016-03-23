package com.br.musa.repositorio;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.br.musa.dao.CustomGenericDAOImpl;
import com.br.musa.entidades.Pagamento;
import com.br.musa.generics.GenericEntity;

@Stateless
@SuppressWarnings("unchecked")
public class PagamentoRepositorio extends CustomGenericDAOImpl<Pagamento> {

	private static final long serialVersionUID = 1L;

	@Override
	public Class<? extends GenericEntity> obterClasse() {
		return Pagamento.class;
	}

	public List<Pagamento> listarPagamentoPorPedido(Long idPedido) {
		StringBuilder consulta = new StringBuilder();
		consulta.append(" SELECT * FROM MUSA.TB_MUSA_PAGAMENTO WHERE FK_PEDIDO = :idPedido ");
		Query query = obterEntityManager().createNativeQuery(consulta.toString(), Pagamento.class);
		query.setParameter("idPedido", idPedido);
		return query.getResultList();
	}
}