package br.com.logic.pendotiba.logicbus.resources.dto;

import br.com.logic.pendotiba.core.model.Garagem;

public class GaragemDTO {

	Long id;
	String codigo;
	String descricao;

	
	
	public GaragemDTO() {

	}
	
	public GaragemDTO(Garagem garagem) {
		this.id = garagem.getId();
		this.codigo = garagem.getCodigo();
		this.descricao = garagem.getDescricao();
	}



	//GETTERS E SETTERS
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
