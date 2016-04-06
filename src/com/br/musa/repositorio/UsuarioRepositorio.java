package com.br.musa.repositorio;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.jboss.logging.Logger;

import com.br.musa.dao.CustomGenericDAOImpl;
import com.br.musa.entidades.Usuario;
import com.br.musa.generics.GenericEntity;

@Stateless
public class UsuarioRepositorio extends CustomGenericDAOImpl<Usuario> {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(UsuarioRepositorio.class);

	@Override
	public Class<? extends GenericEntity> obterClasse() {
		return Usuario.class;
	}

	public Usuario consultarUsuarioPorNome(String nome) {
		StringBuilder consulta = new StringBuilder();
		consulta.append("SELECT * FROM tb_musa_usuario where nome = :nome ");
		Query query = obterEntityManager().createNativeQuery(consulta.toString(), Usuario.class);
		query.setParameter("nome", nome);

		try {
			return (Usuario) query.getSingleResult();
		} catch (NoResultException | NonUniqueResultException e) {
			logger.warn(e.getMessage(),e);
			return null;
		}

	}

}