package br.com.logic.pendotiba.logicbus.filter;

import br.com.logic.pendotiba.core.model.Linha;
import br.com.logic.pendotiba.core.model.Ponto;

public class PontoLinhaFilter {
	
	Ponto pontoOrigem;
	Ponto pontoDestino;
	Linha linha;
	String sentido;
	
	
	
	public PontoLinhaFilter() {

	}

	
	
	//GETTERS E SETTERS
	public Ponto getPontoOrigem() {
		return pontoOrigem;
	}
	
	public void setPontoOrigem(Ponto pontoOrigem) {
		this.pontoOrigem = pontoOrigem;
	}
	
	public Ponto getPontoDestino() {
		return pontoDestino;
	}
	
	public void setPontoDestino(Ponto pontoDestino) {
		this.pontoDestino = pontoDestino;
	}
	
	public Linha getLinha() {
		return linha;
	}
	
	public void setLinha(Linha linha) {
		this.linha = linha;
	}
	
	public String getSentido() {
		return sentido;
	}
	
	public void setSentido(String sentido) {
		this.sentido = sentido;
	}

}
