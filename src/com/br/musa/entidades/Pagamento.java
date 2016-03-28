package com.br.musa.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.br.musa.generics.GenericEntity;

@Entity
@Table(name = "TB_MUSA_PAGAMENTO")
public class Pagamento extends GenericEntity {

	private static final long serialVersionUID = -2536110461810243535L;

	// ATRIBUTOS

	@Id
	@Column(name = "ID_PAGAMENTO")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DT_PAGAMENTO")
	private Date dtPagamento;

	@Column(name = "NU_VALOR_PAGO")
	private BigDecimal valorPago;

	@Column(name = "NU_VALOR_RESTANTE")
	private BigDecimal valorRestante;
	
	@Column(name = "NU_VALOR_TOTAL_PEDIDO")
	private BigDecimal valorTotalPedido;

	@Column(name = "DS_OBSERVACAO")
	private String observacao;

	// RELACIONAMENTOS

	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="FK_PEDIDO", referencedColumnName="ID_PEDIDO")
	private Pedido pedido;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDtPagamento() {
		return dtPagamento;
	}

	public void setDtPagamento(Date dtPagamento) {
		this.dtPagamento = dtPagamento;
	}

	public BigDecimal getValorPago() {
		return valorPago;
	}

	public void setValorPago(BigDecimal valorPago) {
		this.valorPago = valorPago;
	}

	public BigDecimal getValorRestante() {
		return valorRestante;
	}

	public void setValorRestante(BigDecimal valorRestante) {
		this.valorRestante = valorRestante;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public Serializable obterIdentificador() {
		return id;
	}

	public Pagamento(Long id, Date dtPagamento, BigDecimal valorPago,
			BigDecimal valorRestante, String observacao, Pedido pedido) {
		super();
		this.id = id;
		this.dtPagamento = dtPagamento;
		this.valorPago = valorPago;
		this.valorRestante = valorRestante;
		this.observacao = observacao;
		this.pedido = pedido;
	}

	public Pagamento() {
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
		Pagamento other = (Pagamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public BigDecimal getValorTotalPedido() {
		return valorTotalPedido;
	}

	public void setValorTotalPedido(BigDecimal valorTotalPedido) {
		this.valorTotalPedido = valorTotalPedido;
	}

}
