package br.com.logic.pendotiba.core.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "permissao")
public class Permissao implements Serializable {
	
	private static final long serialVersionUID = 5701277034567788458L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	String role;
	
	@NotBlank(message = "Nome é obrigatório")
	String nome;
	
	@NotBlank(message = "Url é obrigatório")
	String url;
	
	@ManyToOne
	@JoinColumn(name = "id_modulo_acesso")
	ModuloAcesso moduloAcesso;
	
	
	
	public Permissao(){
		
	}



	//GETTES E SETTES
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
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public ModuloAcesso getModuloAcesso() {
		return moduloAcesso;
	}
	
	public void setModuloAcesso(ModuloAcesso moduloAcesso) {
		this.moduloAcesso = moduloAcesso;
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
		Permissao other = (Permissao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	@Override
	public String toString() {
		return nome;
	}
	
	
	public boolean isNova(){
		return id == null;
	}

}
