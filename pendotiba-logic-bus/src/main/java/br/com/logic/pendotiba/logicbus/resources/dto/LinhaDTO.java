package br.com.logic.pendotiba.logicbus.resources.dto;

import br.com.logic.pendotiba.core.model.Linha;

public class LinhaDTO {

	Long id;
	String codigo;
	String descricao;
	
	
	public LinhaDTO() {

	}
	
	public LinhaDTO(Linha linha) {
		this.id = linha.getId();
		this.codigo = linha.getCodigo();
		this.descricao = linha.getDescricao();
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
