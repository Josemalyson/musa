package com.br.musa.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.br.musa.generics.GenericEntity;

@Entity
@Table(name = "TB_MUSA_ENDERECO")
public class Endereco extends GenericEntity {

	// ATRIBUTOS
	private static final long serialVersionUID = -5384037149637358424L;

	@Id
	@Column(name = "ID_ENDERECO")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "DS_RUA")
	private String rua;

	@Column(name = "DS_BAIRRO")
	private String bairro;

	@Column(name = "NU_NUMERO")
	private Integer numero;

	@Column(name = "NU_CEP")
	private Integer cep;

	@ManyToOne
	@JoinColumn(name = "FK_ESTADO", referencedColumnName = "ID_ESTADO")
	private Estado estado;

	@ManyToOne
	@JoinColumn(name = "FK_CIDADE", referencedColumnName = "ID_CIDADE")
	private Cidade cidade;

	public Endereco() {
	}

	public Endereco(Long id, String rua, String bairro, Integer numero, Integer cep, Estado estado, Cidade cidade) {
		super();
		this.id = id;
		this.rua = rua;
		this.bairro = bairro;
		this.numero = numero;
		this.cep = cep;
		this.estado = estado;
		this.cidade = cidade;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Integer getCep() {
		return cep;
	}

	public void setCep(Integer cep) {
		this.cep = cep;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	@Override
	public Serializable obterIdentificador() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		return false;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

}
