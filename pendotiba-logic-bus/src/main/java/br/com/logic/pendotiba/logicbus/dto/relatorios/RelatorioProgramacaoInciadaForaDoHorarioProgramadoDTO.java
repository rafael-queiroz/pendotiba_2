package br.com.logic.pendotiba.logicbus.dto.relatorios;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

public class RelatorioProgramacaoInciadaForaDoHorarioProgramadoDTO {
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	Date dataCompetencia;
	
	String pontoPegadaMotorista;
	String matriculaMotorista;
	String nomeMotorista;
	String turno;
	
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	Date horaInicioTrabalhoProgramado;
	
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	Date horaInicioTrabalhoRealizado;
	
	String carroRealizado;
	
	public RelatorioProgramacaoInciadaForaDoHorarioProgramadoDTO() {
		
	}

	
	
	//GETTERS E SETTERS
	public Date getDataCompetencia() {
		return dataCompetencia;
	}

	public void setDataCompetencia(Date dataCompetencia) {
		this.dataCompetencia = dataCompetencia;
	}

	public String getPontoPegadaMotorista() {
		return pontoPegadaMotorista;
	}

	public void setPontoPegadaMotorista(String pontoPegadaMotorista) {
		this.pontoPegadaMotorista = pontoPegadaMotorista;
	}

	public String getMatriculaMotorista() {
		return matriculaMotorista;
	}

	public void setMatriculaMotorista(String matriculaMotorista) {
		this.matriculaMotorista = matriculaMotorista;
	}

	public String getNomeMotorista() {
		return nomeMotorista;
	}

	public void setNomeMotorista(String nomeMotorista) {
		this.nomeMotorista = nomeMotorista;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public Date getHoraInicioTrabalhoProgramado() {
		return horaInicioTrabalhoProgramado;
	}

	public void setHoraInicioTrabalhoProgramado(Date horaInicioTrabalhoProgramado) {
		this.horaInicioTrabalhoProgramado = horaInicioTrabalhoProgramado;
	}

	public Date getHoraInicioTrabalhoRealizado() {
		return horaInicioTrabalhoRealizado;
	}

	public void setHoraInicioTrabalhoRealizado(Date horaInicioTrabalhoRealizado) {
		this.horaInicioTrabalhoRealizado = horaInicioTrabalhoRealizado;
	}

	public String getCarroRealizado() {
		return carroRealizado;
	}

	public void setCarroRealizado(String carroRealizado) {
		this.carroRealizado = carroRealizado;
	}
	
	public String getMotorista() {
		return matriculaMotorista.concat(" - ").concat(nomeMotorista);
	}

}
