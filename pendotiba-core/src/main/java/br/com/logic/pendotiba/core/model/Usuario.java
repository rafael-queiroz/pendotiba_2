package br.com.logic.pendotiba.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Email;

import br.com.logic.pendotiba.core.validation.AtributoConfirmacao;

@AtributoConfirmacao(atributo = "senha", atributoConfirmacao = "confirmacaoSenha", message = "Confirmação da senha não confere")
@DynamicUpdate
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {
	
	private static final long serialVersionUID = -5719083451838952081L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	Long id;
	
	@Email(message = "E-mail inválido")
	String email;

	String senha;
	
	@Transient
	String confirmacaoSenha;

	Boolean ativo = true;

	/*
	@Size(min = 1,  message = "Selecione pelo menos um perfil")
	@ManyToMany
	@JoinTable(name = "usuario_perfil", 
				joinColumns = @JoinColumn(name = "id_usuario"),
				inverseJoinColumns = @JoinColumn(name = "id_perfil"))	
	List<Perfil> perfis;
	*/
	
	@ManyToOne
	@JoinColumn(name = "id_perfil")
	Perfil perfil;
	
	@ManyToOne
	@JoinColumn(name = "id_funcionario")
	Funcionario funcionario;
	
	
	
	public Usuario(){
		
	}
	
	
	
	@PreUpdate
	private void preUpdate() {
		this.confirmacaoSenha = senha;
	}
	
	
	
	//GETTERS E SETTERS
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Perfil getPerfil() {
		return perfil;
	}
	
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	
	public Funcionario getFuncionario() {
		return funcionario;
	}
	
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	
	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}
	
	public void setConfirmacaoSenha(String confirmacaoSenha) {
		this.confirmacaoSenha = confirmacaoSenha;
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
		Usuario other = (Usuario) obj;
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
		return funcionario.getMatricula().concat(" - ").concat(funcionario.getNome());
	}
	
	public boolean podeAlterar(Usuario usuario) {
		if(usuario.getPerfil().getHierarquia() > perfil.getHierarquia() || perfil.getHierarquia() == 1)
			return true;
		return false;
	}
	
	
	public boolean podeAlterarPerfil(Perfil p) {
		if(p.getHierarquia() > perfil.getHierarquia() || perfil.getHierarquia() == 1 )
			return true;
		return false;
	}



	public boolean isAppAbastecimentoOdometroRoleta() {
		if (perfil.getId().equals(Perfil.APP_ABASTECIMENTO_ODOMETRO) || perfil.getId().equals(Perfil.APP_ROLETA) 
				|| perfil.getId().equals(Perfil.APP_BOMBA_ABASTECIMENTO) || perfil.getId().equals(Perfil.APP_ABASTECIMENTO_E_ENCERRANTE))
			return true;
		return false;
	}
	
}
