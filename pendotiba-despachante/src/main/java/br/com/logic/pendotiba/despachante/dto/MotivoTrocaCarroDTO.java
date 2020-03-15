package br.com.logic.pendotiba.despachante.dto;

import br.com.logic.pendotiba.core.model.MotivoTrocaCarro;

public class MotivoTrocaCarroDTO {

	Long id;
	String descricao;

	
	
	public MotivoTrocaCarroDTO() {

	}
	
	public MotivoTrocaCarroDTO(MotivoTrocaCarro obj) {
		this.id = obj.getId();
		this.descricao = obj.getDescricao();
	}



	//GETTERS E SETTERS
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
}
