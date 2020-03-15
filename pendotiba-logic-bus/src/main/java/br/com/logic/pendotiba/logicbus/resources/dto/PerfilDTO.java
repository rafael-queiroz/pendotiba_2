package br.com.logic.pendotiba.logicbus.resources.dto;

import br.com.logic.pendotiba.core.model.Perfil;

public class PerfilDTO {

	Long id;
	String nome;
	
	
	public PerfilDTO() {

	}
	
	public PerfilDTO(Perfil perfil) {
		this.id = perfil.getId();
		this.nome = perfil.getNome();
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
	
}
