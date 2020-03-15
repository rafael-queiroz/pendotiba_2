package br.com.logic.pendotiba.despachante.dto;

import br.com.logic.pendotiba.core.model.PontoLinha;

public class PontoLinhaDTO {

	Long id;
	Long idPontoOrigem;
	Long idPontoDestino;
	Long idLinha;
	String sentido;
	String descricao;
	
	
	public PontoLinhaDTO() {

	}
	
	
	public PontoLinhaDTO(PontoLinha pontoLinha) {
		this.id = pontoLinha.getId();
		this.idPontoOrigem = pontoLinha.getPontoOrigem().getId();
		this.idPontoDestino = pontoLinha.getPontoDestino().getId();
		this.idLinha = pontoLinha.getLinha().getId();
		this.sentido = pontoLinha.getSentido();
		this.descricao = pontoLinha.toString();
	}
	
	

	//GETTERS E SETTERS
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getIdPontoOrigem() {
		return idPontoOrigem;
	}
	
	public void setIdPontoOrigem(Long idPontoOrigem) {
		this.idPontoOrigem = idPontoOrigem;
	}
	
	public Long getIdPontoDestino() {
		return idPontoDestino;
	}
	
	public void setIdPontoDestino(Long idPontoDestino) {
		this.idPontoDestino = idPontoDestino;
	}
	
	public Long getIdLinha() {
		return idLinha;
	}
	
	public void setIdLinha(Long idLinha) {
		this.idLinha = idLinha;
	}
	
	public String getSentido() {
		return sentido;
	}

	public void setSentido(String sentido) {
		this.sentido = sentido;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
