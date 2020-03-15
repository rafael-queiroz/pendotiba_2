package br.com.logic.pendotiba.despachante.dto;

import br.com.logic.pendotiba.core.model.Turno;

public class TurnoDTO {

	Long id;
	String descricao;
	String agrupamento;

	
	
	public TurnoDTO() {

	}
	
	public TurnoDTO(Turno obj) {
		this.id = obj.getId();
		this.descricao = obj.getDescricao();
		this.agrupamento = obj.getAgrupamento();
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
	
	public String getAgrupamento() {
		return agrupamento;
	}
	
	public void setAgrupamento(String agrupamento) {
		this.agrupamento = agrupamento;
	}
}
