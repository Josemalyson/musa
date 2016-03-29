package com.br.musa.repositorio;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.br.musa.dao.CustomGenericDAOImpl;
import com.br.musa.entidades.Cliente;
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
		consulta.append(" SELECT P.* FROM MUSA.TB_MUSA_PAGAMENTO P WHERE P.FK_PEDIDO = :idPedido ORDER BY P.DT_PAGAMENTO DESC");
		Query query = obterEntityManager().createNativeQuery(consulta.toString(), Pagamento.class);
		query.setParameter("idPedido", idPedido);
		return query.getResultList();
	}

	public List<Pagamento> obterPagamentosPorCliente(Cliente cliente) {
		StringBuilder consulta = new StringBuilder();
		consulta.append(" SELECT PA.* FROM MUSA.TB_MUSA_PAGAMENTO PA INNER JOIN MUSA.tb_musa_pedido PE ")
				.append(" INNER JOIN MUSA.tb_musa_cliente C WHERE PA.FK_PEDIDO=PE.ID_PEDIDO AND C.ID_CLIENTE=PE.FK_CLIENTE ")
				.append(" AND C.ID_CLIENTE = :cliente");
		Query query = obterEntityManager().createNativeQuery(consulta.toString(), Pagamento.class);
		query.setParameter("cliente", cliente.getId());
		return query.getResultList();
	}

	public List<Pagamento> obterPagamentosPorClienteEData(Cliente cliente, java.util.Date data) {
		StringBuilder consulta = new StringBuilder();
		consulta.append(" SELECT PA.*  FROM MUSA.TB_MUSA_PAGAMENTO PA INNER JOIN MUSA.tb_musa_pedido PE")
				.append(" INNER JOIN MUSA.tb_musa_cliente C WHERE PA.FK_PEDIDO=PE.ID_PEDIDO ")
				.append(" AND C.ID_CLIENTE=PE.FK_CLIENTE AND DATE_FORMAT(PA.DT_PAGAMENTO,'%d/%m/%Y') = :data AND C.ID_CLIENTE = :cliente");
		Query query = obterEntityManager().createNativeQuery(consulta.toString(), Pagamento.class);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		query.setParameter("data", dateFormat.format(data));
		query.setParameter("cliente", cliente.getId());
		return query.getResultList();
	}

	public List<Pagamento> obterPagamentosPorData(Date data) {
		StringBuilder consulta = new StringBuilder();
		consulta.append(" SELECT PA.* FROM MUSA.TB_MUSA_PAGAMENTO PA WHERE DATE_FORMAT(PA.DT_PAGAMENTO,'%d/%m/%Y') = :data ");
		Query query = obterEntityManager().createNativeQuery(consulta.toString(), Pagamento.class);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		query.setParameter("data", dateFormat.format(data));
		return query.getResultList();
	}
}