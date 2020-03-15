package br.com.logic.pendotiba.logicbus.filter;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.logic.pendotiba.core.model.Carro;
import br.com.logic.pendotiba.core.model.Funcionario;
import br.com.logic.pendotiba.core.model.Garagem;
import br.com.logic.pendotiba.core.model.Linha;
import br.com.logic.pendotiba.core.model.Ponto;
import br.com.logic.pendotiba.core.model.Status;
import br.com.logic.pendotiba.core.model.TipoCarro;
import br.com.logic.pendotiba.core.model.Turno;

public class ProgramacaoFilter {
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	Date dataInicial = new Date();
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	Date dataFinal = new Date();
	
	String codigoEscala; 
	Ponto ponto;
	Carro carro;
	Funcionario motorista;
	Linha linha;
	Status status;
	Turno turno;
	Boolean completa = true;
	Garagem garagem;
	List<TipoCarro> tiposCarro;
	
	
	
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
	
	public Ponto getPonto() {
		return ponto;
	}
	
	public void setPonto(Ponto ponto) {
		this.ponto = ponto;
	}
	
	public Carro getCarro() {
		return carro;
	}
	
	public void setCarro(Carro carro) {
		this.carro = carro;
	}
	
	public Funcionario getMotorista() {
		return motorista;
	}
	
	public void setMotorista(Funcionario motorista) {
		this.motorista = motorista;
	}

	public Linha getLinha() {
		return linha;
	}

	public void setLinha(Linha linha) {
		this.linha = linha;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
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
	
	public Garagem getGaragem() {
		return garagem;
	}
	
	public void setGaragem(Garagem garagem) {
		this.garagem = garagem;
	}
	
	public List<TipoCarro> getTiposCarro() {
		return tiposCarro;
	}
	
	public void setTiposCarro(List<TipoCarro> tiposCarro) {
		this.tiposCarro = tiposCarro;
	}
	
}
