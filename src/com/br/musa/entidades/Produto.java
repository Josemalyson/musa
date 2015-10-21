package com.br.musa.entidades;

import java.io.Serializable;

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

import com.br.musa.generics.GenericEntity;

@Entity
@Table(name = "TB_MUSA_PRODUTO")
public class Produto extends GenericEntity {

	private static final long serialVersionUID = 8027778977155972234L;

	// ATRIBUTOS

	@Id
	@Column(name = "ID_PRODUTO")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "DS_PRODUTO")
	private String descricaoProduto;

	@Column(name = "NU_PRECO_VENDA")
	private Float precoVenda;

	@Column(name = "NU_PRECO_CUSTO")
	private Float precoCusto;

	@Column(name = "FL_EXCLUIDO")
	private Boolean flExcluido;

	// RELACIONAMENTOS

	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="FK_PEDIDO", referencedColumnName="ID_PEDIDO")
	private Pedido pedido;

	// CONSTRUTORES

	public Produto() {
	}

	public Produto(Long id, String descricaoProduto, Float precoVenda,
			Float precoCusto, Boolean flExcluido, Pedido pedido) {
		super();
		this.id = id;
		this.descricaoProduto = descricaoProduto;
		this.precoVenda = precoVenda;
		this.precoCusto = precoCusto;
		this.flExcluido = flExcluido;
		this.pedido = pedido;
	}

	// GETTRS E SETTRS

	@Override
	public Serializable obterIdentificador() {
		return id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricaoProduto() {
		return descricaoProduto;
	}

	public void setDescricaoProduto(String descricaoProduto) {
		this.descricaoProduto = descricaoProduto;
	}

	public Float getPrecoVenda() {
		return precoVenda;
	}

	public void setPrecoVenda(Float precoVenda) {
		this.precoVenda = precoVenda;
	}

	public Float getPrecoCusto() {
		return precoCusto;
	}

	public void setPrecoCusto(Float precoCusto) {
		this.precoCusto = precoCusto;
	}

	public Boolean getFlExcluido() {
		return flExcluido;
	}

	public void setFlExcluido(Boolean flExcluido) {
		this.flExcluido = flExcluido;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
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
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
