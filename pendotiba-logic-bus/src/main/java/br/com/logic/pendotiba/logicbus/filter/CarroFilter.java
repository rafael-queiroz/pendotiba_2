package br.com.logic.pendotiba.logicbus.filter;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.logic.pendotiba.core.model.TipoCarro;
import br.com.logic.pendotiba.core.model.TipoChassi;

public class CarroFilter {
	
	String numeroDeOrdem;
	TipoCarro tipoCarro;
	TipoChassi tipoChassi;
	Boolean ativo = true;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	Date dataCompetencia;
	
	
	//GETTERS E SETTERS
	public String getNumeroDeOrdem() {
		return numeroDeOrdem;
	}

	public void setNumeroDeOrdem(String numeroDeOrdem) {
		this.numeroDeOrdem = numeroDeOrdem;
	}
	
	public TipoCarro getTipoCarro() {
		return tipoCarro;
	}
	
	public void setTipoCarro(TipoCarro tipoCarro) {
		this.tipoCarro = tipoCarro;
	}
	
	public Boolean getAtivo() {
		return ativo;
	}
	
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	
	public Date getDataCompetencia() {
		return dataCompetencia;
	}
	
	public void setDataCompetencia(Date dataMovimentacao) {
		this.dataCompetencia = dataMovimentacao;
	}
	
	public TipoChassi getTipoChassi() {
		return tipoChassi;
	}
	
	public void setTipoChassi(TipoChassi tipoChassi) {
		this.tipoChassi = tipoChassi;
	}

}
