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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.br.musa.generics.GenericEntity;

@Entity
@Table(name = "TB_MUSA_CLIENTE")
public class Cliente extends GenericEntity {

	private static final long serialVersionUID = 6091984940990541385L;

	// ATRIBUTOS

	@Id
	@Column(name = "ID_CLIENTE")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "DS_NOME")
	private String nome;

	@Column(name = "NU_CPF")
	private String cpf;

	@Column(name = "NU_RG")
	private String rg;

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_NASCIMENTO")
	private Date dtNascimento;

	@Column(name = "FL_EXCLUIDO")
	private Boolean flExcluido;

	// RELACIONAMENTOS

	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private List<Contato> contatoList;

	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Pedido> pedidoList;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "cliente", fetch = FetchType.EAGER)
	private Endereco endereco;

	public Cliente() {
		super();
	}

	// GET E SET

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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public Date getDtNascimento() {
		return dtNascimento;
	}

	public void setDtNascimento(Date dtNascimento) {
		this.dtNascimento = dtNascimento;
	}

	public Boolean getFlExcluido() {
		return flExcluido;
	}

	public void setFlExcluido(Boolean flExcluido) {
		this.flExcluido = flExcluido;
	}

	public List<Contato> getContatoList() {
		return contatoList;
	}

	public void setContatoList(List<Contato> contatoList) {
		this.contatoList = contatoList;
	}

	public List<Pedido> getPedidoList() {
		return pedidoList;
	}

	public void setPedidoList(List<Pedido> pedidoList) {
		this.pedidoList = pedidoList;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", rg=" + rg + ", dtNascimento=" + dtNascimento
				+ ", flExcluido=" + flExcluido + ", contatoList=" + contatoList + ", pedidoList=" + pedidoList
				+ ", endereco=" + endereco + "]";
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
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Cliente other = (Cliente) obj;

		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

}
