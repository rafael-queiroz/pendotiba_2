package br.com.logic.pendotiba.logicbus.dto.relatorios;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

public class RelatorioMotoristaForaDoProgramadoDTO {
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	Date dataCompetencia;
	
	String matriculaMotorista;
	String nomeMotorista;
	
	String carroProgramado;
	
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	Date horaInicioTrabalhoProgramado;
	
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	Date horaTerminoTrabalhoProgramado;
	
	String carroRealizado;
	
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	Date horaInicioTrabalhoRealizado;
	
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	Date horaTerminoTrabalhoRealizado;
	
	
	
	public RelatorioMotoristaForaDoProgramadoDTO() {
		
	}
	



	//GETTERS E SETTERS
	public Date getDataCompetencia() {
		return dataCompetencia;
	}

	public void setDataCompetencia(Date dataCompetencia) {
		this.dataCompetencia = dataCompetencia;
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
	
	public String getCarroProgramado() {
		return carroProgramado;
	}
	
	public void setCarroProgramado(String carroProgramado) {
		this.carroProgramado = carroProgramado;
	}

	public Date getHoraInicioTrabalhoProgramado() {
		return horaInicioTrabalhoProgramado;
	}

	public void setHoraInicioTrabalhoProgramado(Date horaInicioTrabalhoProgramado) {
		this.horaInicioTrabalhoProgramado = horaInicioTrabalhoProgramado;
	}

	public Date getHoraTerminoTrabalhoProgramado() {
		return horaTerminoTrabalhoProgramado;
	}

	public void setHoraTerminoTrabalhoProgramado(Date horaTerminoTrabalhoProgramado) {
		this.horaTerminoTrabalhoProgramado = horaTerminoTrabalhoProgramado;
	}

	public String getCarroRealizado() {
		return carroRealizado;
	}
	
	public void setCarroRealizado(String carroRealizado) {
		this.carroRealizado = carroRealizado;
	}

	public Date getHoraInicioTrabalhoRealizado() {
		return horaInicioTrabalhoRealizado;
	}

	public void setHoraInicioTrabalhoRealizado(Date horaInicioTrabalhoRealizado) {
		this.horaInicioTrabalhoRealizado = horaInicioTrabalhoRealizado;
	}

	public Date getHoraTerminoTrabalhoRealizado() {
		return horaTerminoTrabalhoRealizado;
	}

	public void setHoraTerminoTrabalhoRealizado(Date horaTerminoTrabalhoRealizado) {
		this.horaTerminoTrabalhoRealizado = horaTerminoTrabalhoRealizado;
	}
	
	public String getMotorista() {
		return matriculaMotorista.concat(" - ").concat(nomeMotorista);
	}

}
