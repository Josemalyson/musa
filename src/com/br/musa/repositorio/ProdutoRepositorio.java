package com.br.musa.repositorio;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.br.musa.dao.CustomGenericDAOImpl;
import com.br.musa.entidades.Produto;
import com.br.musa.generics.GenericEntity;

@Stateless
@SuppressWarnings("unchecked")
public class ProdutoRepositorio extends CustomGenericDAOImpl<Produto> {

	private static final long serialVersionUID = 1L;

	@Override
	public Class<? extends GenericEntity> obterClasse() {
		return Produto.class;
	}

	public List<Produto> listarProdutosAtivos() {
		StringBuilder consulta = new StringBuilder();
		consulta.append(" SELECT TP.* FROM TB_MUSA_PRODUTO TP WHERE TP.FL_EXCLUIDO=0 ");
		Query query = obterEntityManager().createNativeQuery(consulta.toString(), Produto.class);
		return query.getResultList();
	}

	public List<Produto> listarProdutosPorPededido(Long id) {
		StringBuilder consulta = new StringBuilder();
		consulta.append(" SELECT P.* FROM TB_MUSA_PRODUTO P INNER JOIN TB_MUSA_PRODUTO_PEDIDO PP ON P.ID_PRODUTO = PP.FK_PRODUTO WHERE PP.FK_PEDIDO = :id ");
		Query query = obterEntityManager().createNativeQuery(consulta.toString(), Produto.class);
		query.setParameter("id", id);
		return query.getResultList();
	}


}