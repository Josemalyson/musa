package com.br.musa.dao;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.br.musa.generics.GenericEntity;

@SuppressWarnings("unchecked")
public abstract class GenericDAOImpl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8483222232120209205L;
	@PersistenceContext
	private EntityManager entityManager;

	
	public Collection<? extends GenericEntity> listar() {
		String query = "SELECT T FROM " + obterClasse().getSimpleName() + " T";
		return this.entityManager.createQuery(query).getResultList();
	}

	public GenericEntity salvar(GenericEntity objeto) {
		return ((GenericEntity) this.entityManager.merge(objeto));
	}

	public void excluir(GenericEntity objeto) {
		this.entityManager.remove(this.entityManager.getReference(
				obterClasse(), objeto.obterIdentificador()));
	}

	public GenericEntity consultarPorId(GenericEntity objeto) {
		return ((GenericEntity) this.entityManager.find(obterClasse(),
				objeto.obterIdentificador()));
	}

/*	public GenericEntity consultar(GenericEntity objeto) {
		try {
			return ((GenericEntity) this.entityManager.createQuery(
					GeradorJPQL.gerarSelect(objeto)).getSingleResult());
		} catch (Exception e) {
		}
		return null;
	}

	public Collection<? extends GenericEntity> listar(String ordem,
			String[] campos) {
		String query = "Select t from " + obterClasse().getSimpleName() + " t";
		query = GeradorJPQL.adicionarOrdenacaoConsulta(query, ordem, campos);
		return this.entityManager.createQuery(query).getResultList();
	}*/
/*
	public Collection<? extends GenericEntity> listar(GenericEntity objeto) {
		return listar(objeto, false);
	}
*/
//	public Collection<? extends GenericEntity> listar(GenericEntity objeto,
//			boolean ignoreCase) {
//		return listar(objeto, StringMatchingPattern.EXACT, ignoreCase);
//	}
/*
	public Collection<? extends GenericEntity> listarLike(GenericEntity objeto) {
		return listar(objeto, false);
	}

	public Collection<? extends GenericEntity> listarLike(GenericEntity objeto,
			boolean ignoreCase) {
		return listar(objeto, StringMatchingPattern.LIKE_BOTH, ignoreCase);
	}
*/
//	public Collection<? extends GenericEntity> listar(GenericEntity objeto,
//			StringMatchingPattern tipoCasamentoString, boolean ignoreCase) {
//		String query = GeradorJPQL.gerarSelect(objeto, tipoCasamentoString,
//				ignoreCase);
//		query = ordenarPorUltimoId(query);
//		return this.entityManager.createQuery(query).getResultList();
//	}
//
//	public Collection<? extends GenericEntity> listar(GenericEntity objeto,
//			Integer first, Integer max) {
//		String query = GeradorJPQL.gerarSelect(objeto);
//		query = ordenarPorUltimoId(query);
//		return this.entityManager.createQuery(query)
//				.setFirstResult(first.intValue()).setMaxResults(max.intValue())
//				.getResultList();
//	}
//
//	public Collection<? extends GenericEntity> listar(GenericEntity objeto,
//			String order, String[] campos) {
//		String query = GeradorJPQL.gerarSelect(objeto);
//		query = GeradorJPQL.adicionarOrdenacaoConsulta(query, order, campos);
//		return this.entityManager.createQuery(query).getResultList();
//	}
//
//	public Long count(GenericEntity objeto) {
//		String query = GeradorJPQL.gerarSelect(objeto);
//		query = query.replaceFirst("SELECT t ", "SELECT count(t) ");
//		return ((Long) this.entityManager.createQuery(query).getSingleResult());
//	}
//
//	private String ordenarPorUltimoId(String query) {
//		return GeradorJPQL.adicionarOrdenacaoConsulta(query, " DESC ",
//				new String[] { "id" });
//	}

	public abstract Class<? extends GenericEntity> obterClasse();

	public EntityManager obterEntityManager() {
		return this.entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}