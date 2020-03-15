package br.com.logic.pendotiba.logicbus.filter;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.logic.pendotiba.core.model.Carro;
import br.com.logic.pendotiba.core.model.Turno;

public class ViagemFilter {
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	Date dataInicial = new Date();
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	Date dataFinal = new Date();
	
	String codigoEscala; 
	Carro carro;
	Turno turno;
	Boolean completa = true;
	
	
	
	//GETTERS E SETTERS
	public Date getDataInicial() {
		return dataInicial;
	}
	
	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}
	
	public Date getDataFinal() {
		return dataFinal;
	}
	
	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public String getCodigoEscala() {
		return codigoEscala;
	}
	
	public void setCodigoEscala(String codigoEscala) {
		this.codigoEscala = codigoEscala;
	}
	
	public Carro getCarro() {
		return carro;
	}
	
	public void setCarro(Carro carro) {
		this.carro = carro;
	}
	
	public Turno getTurno() {
		return turno;
	}
	
	public void setTurno(Turno turno) {
		this.turno = turno;
	}
	
	public Boolean getCompleta() {
		return completa;
	}
	
	public void setCompleta(Boolean completa) {
		this.completa = completa;
	}
	
}
