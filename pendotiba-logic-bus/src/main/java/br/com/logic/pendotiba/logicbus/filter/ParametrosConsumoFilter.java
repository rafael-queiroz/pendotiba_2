package br.com.logic.pendotiba.logicbus.filter;

import br.com.logic.pendotiba.core.model.Linha;
import br.com.logic.pendotiba.core.model.TipoChassi;

public class ParametrosConsumoFilter {
	
	Linha linha;
	TipoChassi tipoChassi;

	
	
	//GETTERS E SETTERS
	public Linha getLinha() {
		return linha;
	}
	
	public void setLinha(Linha linha) {
		this.linha = linha;
	}
	
	public TipoChassi getTipoChassi() {
		return tipoChassi;
	}
	
	public void setTipoChassi(TipoChassi tipoChassi) {
		this.tipoChassi = tipoChassi;
	}

}
