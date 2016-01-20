package com.br.musa.entidades;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.br.musa.generics.GenericEntity;

@Entity
@Table(name = "TB_MUSA_ESTADO")
public class Estado extends GenericEntity {

	private static final long serialVersionUID = -4654883203557183016L;

	// ATRIBUTOS

	@Id
	@Column(name = "ID_ESTADO")
	private Long id;

	@Column(name = "DS_DESCRICAO")
	private String descricao;

	@Column(name = "SI_SIGLA")
	private String sigla;

	@OneToMany(mappedBy = "estado")
	private List<Endereco> enderecoList;

	@OneToMany(mappedBy = "estado")
	private List<Cidade> cidadeList;

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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public List<Endereco> getEnderecoList() {
		return enderecoList;
	}

	public void setEnderecoList(List<Endereco> enderecoList) {
		this.enderecoList = enderecoList;
	}

	public List<Cidade> getCidadeList() {
		return cidadeList;
	}

	public void setCidadeList(List<Cidade> cidadeList) {
		this.cidadeList = cidadeList;
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
		Estado other = (Estado) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
