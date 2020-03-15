package br.com.logic.pendotiba.logicbus.filter;

import br.com.logic.pendotiba.core.model.Perfil;

public class UsuarioFilter {
	
	String nome;
	String email;
	Perfil perfil;

	
	//GETTERS E SETTERS
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Perfil getPerfil() {
		return perfil;
	}
	
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

}
