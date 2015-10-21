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
@Table(name = "TB_MUSA_CONTATO")
public class Contato extends GenericEntity {

	private static final long serialVersionUID = -4654883203557183016L;

	// ATRIBUTOS

	@Id
	@Column(name = "ID_CONTATO")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "NU_DDD")
	private String ddd;

	@Column(name = "NU_TELEFONE")
	private String telefone;

	@Column(name = "DS_EMAIL")
	private String email;

	@Column(name = "FL_EXCLUIDO")
	private Boolean flExcluido;

	@Override
	public Serializable obterIdentificador() {
		return id;
	}

	// RELACIONAMENTOS

	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="FK_CLIENTE", referencedColumnName="ID_CLIENTE")
	private Cliente cliente;

	// CONSTRUTORES

	public Contato() {
	}

	public Contato(Long id, String ddd, String telefone, String email,
			Boolean flExcluido, Cliente cliente) {
		super();
		this.id = id;
		this.ddd = ddd;
		this.telefone = telefone;
		this.email = email;
		this.flExcluido = flExcluido;
		this.cliente = cliente;
	}

	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getFlExcluido() {
		return flExcluido;
	}

	public void setFlExcluido(Boolean flExcluido) {
		this.flExcluido = flExcluido;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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
		Contato other = (Contato) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
