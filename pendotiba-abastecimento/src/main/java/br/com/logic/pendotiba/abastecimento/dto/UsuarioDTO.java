package br.com.logic.pendotiba.abastecimento.dto;

import br.com.logic.pendotiba.core.model.Usuario;

public class UsuarioDTO {
	
	Long id;
	String senha;
	Long idFuncionario;
	Long idPerfil;
	String matricula;

	
	
	public UsuarioDTO() {

	}
	

	public UsuarioDTO(Usuario usuario) {
		this.id = usuario.getId();
		this.senha = usuario.getSenha();
		this.idFuncionario = usuario.getFuncionario().getId();
		this.idPerfil = usuario.getPerfil().getId();
		this.matricula = usuario.getFuncionario().getMatricula();
	}



	//GETTERS E SETTERS
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Long getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(Long idFuncionario) {
		this.idFuncionario = idFuncionario;
	}
	
	public Long getIdPerfil() {
		return idPerfil;
	}
	
	public void setIdPerfil(Long idPerfil) {
		this.idPerfil = idPerfil;
	}
	
	public String getMatricula() {
		return matricula;
	}
	
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

}
