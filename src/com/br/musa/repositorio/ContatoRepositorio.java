package com.br.musa.repositorio;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.br.musa.dao.CustomGenericDAOImpl;
import com.br.musa.entidades.Cliente;
import com.br.musa.entidades.Contato;
import com.br.musa.generics.GenericEntity;

@Stateless
@SuppressWarnings("unchecked")
public class ContatoRepositorio extends CustomGenericDAOImpl<Contato> {

	private static final long serialVersionUID = 1L;

	@Override
	public Class<? extends GenericEntity> obterClasse() {
		return Contato.class;
	}

	public List<Contato> listarContatosClienteNaoExcluido(Cliente cliente) {

		StringBuilder consulta = new StringBuilder();

		consulta.append(" SELECT C.* FROM TB_MUSA_CONTATO C WHERE C.FK_CLIENTE=:cliente AND C.FL_EXCLUIDO=0 ");

		Query query = obterEntityManager().createNativeQuery(consulta.toString(), Contato.class);
		query.setParameter("cliente", cliente.getId());

		return query.getResultList();
	}

}
