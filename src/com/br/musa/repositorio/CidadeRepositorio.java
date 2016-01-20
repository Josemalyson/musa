package com.br.musa.repositorio;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.br.musa.dao.CustomGenericDAOImpl;
import com.br.musa.entidades.Cidade;
import com.br.musa.entidades.Estado;
import com.br.musa.generics.GenericEntity;

@Stateless
@SuppressWarnings("unchecked")
public class CidadeRepositorio extends CustomGenericDAOImpl<Cidade> {

	private static final long serialVersionUID = 1L;

	@Override
	public Class<? extends GenericEntity> obterClasse() {
		return Cidade.class;
	}

	public List<Cidade> listarCidadesPorEstados(Estado estado) {

		StringBuilder consulta = new StringBuilder();
		consulta.append(" SELECT C.* FROM TB_MUSA_CIDADE C WHERE C.ESTADO_ID=:estado ORDER BY C.DS_DESCRICAO ");
		Query query = obterEntityManager().createNativeQuery(consulta.toString(), Cidade.class);
		query.setParameter("estado", estado.getId());
		return query.getResultList();
	}

}