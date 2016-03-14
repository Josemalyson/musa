package com.br.musa.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.br.musa.generics.GenericEntity;

@Entity
@Table(name = "TB_MUSA_PEDIDO")
public class Pedido extends GenericEntity {

	private static final long serialVersionUID = -944983935335821114L;

	// ATRIBUTOS

	@Id
	@Column(name = "ID_PEDIDO")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "FL_EXCLUIDO")
	private Boolean flExcluido;

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_PEDIDO")
	private Date dtPedido;

	// RELACIONAMENTOS

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "FK_CLIENTE", referencedColumnName = "ID_CLIENTE")
	private Cliente cliente;

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private List<Pagamento> pagamentoList;

	// CONSTRUTORES

	public Pedido() {
	}

	@Override
	public Serializable obterIdentificador() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getFlExcluido() {
		return flExcluido;
	}

	public void setFlExcluido(Boolean flExcluido) {
		this.flExcluido = flExcluido;
	}

	public Date getDtPedido() {
		return dtPedido;
	}

	public void setDtPedido(Date dtPedido) {
		this.dtPedido = dtPedido;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Pagamento> getPagamentoList() {
		return pagamentoList;
	}

	public void setPagamentoList(List<Pagamento> pagamentoList) {
		this.pagamentoList = pagamentoList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
