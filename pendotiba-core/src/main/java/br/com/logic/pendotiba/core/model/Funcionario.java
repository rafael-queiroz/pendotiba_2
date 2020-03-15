package br.com.logic.pendotiba.core.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

@Entity
@Table(name = "funcionario")
public class Funcionario implements Serializable {
	
	static final long serialVersionUID = -4540974047146002639L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	Long id;
	
	//@NotBlank(message = "Matrícula é obrigatório")
	@Column(name = "matricula")
	String matricula;

	//@NotBlank(message = "Nome é obrigatório")
	@Column(name = "nome")
	String nome;

	@Column(name = "ativo")
	Boolean ativo = true;
	
	@Column(name = "cartao")
	String cartao;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "data_atualizacao")
	Date dataAtualizacao;
	
	@ManyToOne
	@JoinColumn(name = "id_funcao")
	Funcao funcao;
	
	
	
	
	public Funcionario() {

	}



	//GETTERS E SETTERS
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public String getCartao() {
		return cartao;
	}
	
	public void setCartao(String cartao) {
		this.cartao = cartao;
	}
	
	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}
	
	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}
	
	public Funcao getFuncao() {
		return funcao;
	}
	
	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
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
		Funcionario other = (Funcionario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
	@Override
	public String toString() {
		if(!StringUtils.isEmpty(nome))
			return matricula.concat(" - ").concat(nome);
		return matricula;
	}
	
	
	public boolean isNovo(){
		return id == null;
	}
	
}
