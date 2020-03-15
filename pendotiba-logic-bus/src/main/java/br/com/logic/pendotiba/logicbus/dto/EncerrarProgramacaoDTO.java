package br.com.logic.pendotiba.logicbus.dto;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.logic.pendotiba.core.model.Programacao;
import br.com.logic.pendotiba.core.model.Status;
import br.com.logic.pendotiba.core.model.Viagem;

public class EncerrarProgramacaoDTO {
	
	Long id;
	Date dataCompetencia;
	//String codigoEscala;
	
	@NotNull(message = "O campo término de trabalho é obrigatório")
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	Date terminoTrabalho;
	
	@NotNull(message = "O campo término de jornada é obrigatório")
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	Date terminoJornada;
	
	BigInteger roletaInicial1;
	BigInteger roletaInicial2;
	BigInteger roletaInicial3;
	BigInteger roletaFinal1;
	BigInteger roletaFinal2;
	BigInteger roletaFinal3;
	Status status;
	List<Viagem> viagens;
	
	boolean encerrado;
	
	
	
	public EncerrarProgramacaoDTO() {
		
	}


	public EncerrarProgramacaoDTO(Programacao programacao) {
		this.id = programacao.getId();
		this.dataCompetencia = programacao.getDataCompetencia();
		//this.codigoEscala = programacao.getCodigoEscala();
		this.terminoTrabalho = programacao.getTerminoTrabalho();
		this.terminoJornada = programacao.getTerminoJornada();
		this.roletaInicial1 = programacao.carroAtual().getRoletaInicial1();
		this.roletaInicial2 = BigInteger.ZERO;
		this.roletaInicial3 = BigInteger.ZERO;
		this.roletaFinal1 = programacao.carroAtual().getRoletaFinal1();
		this.roletaFinal2 = BigInteger.ZERO;
		this.roletaFinal3 = BigInteger.ZERO;
		this.status = programacao.getStatus();
		this.viagens = programacao.getViagens();
	}



	//GETTERS E SETTERS
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Date getDataCompetencia() {
		return dataCompetencia;
	}
	
	public void setDataCompetencia(Date dataCompetencia) {
		this.dataCompetencia = dataCompetencia;
	}
	/*
	public String getCodigoEscala() {
		return codigoEscala;
	}
	
	public void setCodigoEscala(String codigoEscala) {
		this.codigoEscala = codigoEscala;
	}
	*/
	public Date getTerminoTrabalho() {
		return terminoTrabalho;
	}

	public void setTerminoTrabalho(Date terminoTrabalho) {
		this.terminoTrabalho = terminoTrabalho;
	}

	public Date getTerminoJornada() {
		return terminoJornada;
	}

	public void setTerminoJornada(Date terminoJornada) {
		this.terminoJornada = terminoJornada;
	}

	public BigInteger getRoletaFinal1() {
		return roletaFinal1;
	}

	public void setRoletaFinal1(BigInteger roletaFinal1) {
		this.roletaFinal1 = roletaFinal1;
	}

	public BigInteger getRoletaFinal2() {
		return roletaFinal2;
	}

	public void setRoletaFinal2(BigInteger roletaFinal2) {
		this.roletaFinal2 = roletaFinal2;
	}

	public BigInteger getRoletaFinal3() {
		return roletaFinal3;
	}

	public void setRoletaFinal3(BigInteger roletaFinal3) {
		this.roletaFinal3 = roletaFinal3;
	}
	
	public BigInteger getRoletaInicial1() {
		return roletaInicial1;
	}
	
	public void setRoletaInicial1(BigInteger roletaInicial1) {
		this.roletaInicial1 = roletaInicial1;
	}
	
	public BigInteger getRoletaInicial2() {
		return roletaInicial2;
	}
	
	public void setRoletaInicial2(BigInteger roletaInicial2) {
		this.roletaInicial2 = roletaInicial2;
	}
	
	public BigInteger getRoletaInicial3() {
		return roletaInicial3;
	}
	
	public void setRoletaInicial3(BigInteger roletaInicial3) {
		this.roletaInicial3 = roletaInicial3;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public List<Viagem> getViagens() {
		return viagens;
	}
	
	public void setViagens(List<Viagem> viagens) {
		this.viagens = viagens;
	}
	
	public boolean isNova(){
		return id == null;
	}
	
	public boolean encerrado(){
		return status != null && status.getId().equals(Status.ENCERRADO);
	}

}
