package com.br.musa.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Query;

import com.br.musa.generics.GenericEntity;

public abstract class CustomGenericDAOImpl<T extends GenericEntity> extends GenericDAOImpl{

	private static final long serialVersionUID = 1L;
	private static final String UNCHECKED = "unchecked";
	
	@Override
	@SuppressWarnings(UNCHECKED)
	public List<T> listar() {
		return (List<T>) super.listar();
	}

	@Override
	@SuppressWarnings(UNCHECKED)
	public T salvar(GenericEntity objeto) {
		return (T) super.salvar(objeto);
	}


	@Override
	@SuppressWarnings(UNCHECKED)
	public T consultarPorId(GenericEntity objeto) {
		return (T) super.consultarPorId(objeto);
	}

//	@Override
//	@SuppressWarnings(UNCHECKED)
//	public T consultar(GenericEntity objeto) {
//		return (T) super.consultar(objeto);
//	}
//
//	@Override
//	@SuppressWarnings(UNCHECKED)
//	public List<T> listar(String ordem, String... campos) {
//		return (List<T>) super.listar(ordem, campos);
//	}
//
//	@Override
//	@SuppressWarnings(UNCHECKED)
//	public List<T> listar(GenericEntity objeto) {
//		return (List<T>) super.listar(objeto);
//	}
//
//	@Override
//	@SuppressWarnings(UNCHECKED)
//	public List<T> listar(GenericEntity objeto, Integer first, Integer max) {
//		return (List<T>) super.listar(objeto, first, max);
//	}
//
//	@Override
//	@SuppressWarnings(UNCHECKED)
//	public List<T> listar(GenericEntity objeto, String order, String... campos) {
//		return (List<T>) super.listar(objeto, order, campos);
//	}
	
	public Long buscarProximaSequence(String nomeSequence) {

		String sql = new String("SELECT " + nomeSequence + ".nextval from dual");

		Query query = obterEntityManager().createNativeQuery(sql);

		BigDecimal proximoSeqPessoaUnica = (BigDecimal) query.getSingleResult();

		return proximoSeqPessoaUnica.longValue();

	}



}
