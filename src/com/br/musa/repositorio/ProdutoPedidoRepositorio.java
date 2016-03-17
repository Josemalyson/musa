package com.br.musa.repositorio;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.br.musa.dao.CustomGenericDAOImpl;
import com.br.musa.entidades.ProdutoPedido;
import com.br.musa.generics.GenericEntity;

@Stateless
@SuppressWarnings("unchecked")
public class ProdutoPedidoRepositorio extends CustomGenericDAOImpl<ProdutoPedido> {

	private static final long serialVersionUID = 1L;

	@Override
	public Class<? extends GenericEntity> obterClasse() {
		return ProdutoPedido.class;
	}

	public List<ProdutoPedido> buscarPedidoPorProduto(Long codigoProdudo) {
		StringBuilder consulta = new StringBuilder();
		consulta.append(" SELECT PP.* FROM MUSA.TB_MUSA_PRODUTO_PEDIDO PP WHERE PP.FK_PRODUTO=:codigoProdudo ");
		Query query = obterEntityManager().createNativeQuery(consulta.toString(), ProdutoPedido.class);
		return query.getResultList();
	}
}