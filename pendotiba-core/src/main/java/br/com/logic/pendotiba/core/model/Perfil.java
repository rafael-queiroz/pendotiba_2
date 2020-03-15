package br.com.logic.pendotiba.core.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "perfil")
public class Perfil implements Serializable {
	
	static final long serialVersionUID = 3675906173423633670L;
	
	public static final long LOGIC = 1;
	public static final long APP_ABASTECIMENTO_ODOMETRO = 7;
	public static final long APP_ROLETA = 8;
	public static final long APP_BOMBA_ABASTECIMENTO = 9;
	public static final long APP_ABASTECIMENTO_E_ENCERRANTE = 10;
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	Long id;
	
	@NotBlank(message = "Nome é obrigatório")
	String nome;
	
	@Column(name = "hierarquia")
	Integer hierarquia;

	@ManyToMany
	@JoinTable(name = "perfil_permissao", 
			   joinColumns = @JoinColumn(name = "id_perfil"), 
			   inverseJoinColumns = @JoinColumn(name = "id_permissao"))
	List<Permissao> permissoes;

	
	
	public Perfil(){
		
	}
	
	
	@PrePersist
	void prePersist() {
		this.hierarquia = 3;
	}
	
	
	//GETTERS E SETTERS
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
	
	public List<Permissao> getPermissoes() {
		return permissoes;
	}
	
	public void setPermissoes(List<Permissao> permissoes) {
		this.permissoes = permissoes;
	}
	
	public Integer getHierarquia() {
		return hierarquia;
	}
	
	public void setHierarquia(Integer hierarquia) {
		this.hierarquia = hierarquia;
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
		Perfil other = (Perfil) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	public boolean isNovo(){
		return id == null;
	}
	
	
	@Override
	public String toString() {
		return nome;
	}

}
