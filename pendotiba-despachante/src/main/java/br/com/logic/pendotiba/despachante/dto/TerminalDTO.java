package br.com.logic.pendotiba.despachante.dto;

import br.com.logic.pendotiba.core.model.Terminal;

public class TerminalDTO {

	Long id;
	String codigo;
	String descricao;

	
	
	public TerminalDTO() {

	}
	
	public TerminalDTO(Terminal obj) {
		this.id = obj.getId();
		this.codigo = obj.getCodigo();
		this.descricao = obj.getDescricao();
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
