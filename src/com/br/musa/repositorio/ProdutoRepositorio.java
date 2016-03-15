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


}