package br.com.logic.pendotiba.logicbus.vo;

import java.io.Serializable;

public class EscalaVO implements Serializable {
	
	static final long serialVersionUID = 5445816629760979970L;
	
	String dtSoltura;
	String hrInicioTrabalho;
	String hrInicioJornada;
	String nrLinha;
	String turno;
	String nrMatriculaMotorista;
	String nrMatriculaCobrador;
	String cdLocalPegada;
	String cdVeiculo;
	
	
	
	public EscalaVO() {
		super();
	}
	
	public EscalaVO(String dtSoltura, String hrInicioTrabalho, String hrInicioJornada, String nrLinha, String turno,
			String nrMatriculaMotorista, String nrMatriculaCobrador, String cdLocalPegada, String cdVeiculo) {
		super();
		this.dtSoltura = dtSoltura;
		this.hrInicioTrabalho = hrInicioTrabalho;
		this.hrInicioJornada = hrInicioJornada;
		this.nrLinha = nrLinha;
		this.turno = turno;
		this.nrMatriculaMotorista = nrMatriculaMotorista;
		this.nrMatriculaCobrador = nrMatriculaCobrador;
		this.cdLocalPegada = cdLocalPegada;
		this.cdVeiculo = cdVeiculo;
	}
	
	

	public String getDtSoltura() {
		return dtSoltura;
	}

	public void setDtSoltura(String dtSoltura) {
		this.dtSoltura = dtSoltura;
	}

	public String getHrInicioTrabalho() {
		return hrInicioTrabalho;
	}

	public void setHrInicioTrabalho(String hrInicioTrabalho) {
		this.hrInicioTrabalho = hrInicioTrabalho;
	}

	public String getHrInicioJornada() {
		return hrInicioJornada;
	}

	public void setHrInicioJornada(String hrInicioJornada) {
		this.hrInicioJornada = hrInicioJornada;
	}

	public String getNrLinha() {
		return nrLinha;
	}

	public void setNrLinha(String nrLinha) {
		this.nrLinha = nrLinha;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public String getNrMatriculaMotorista() {
		return nrMatriculaMotorista;
	}

	public void setNrMatriculaMotorista(String nrMatriculaMotorista) {
		this.nrMatriculaMotorista = nrMatriculaMotorista;
	}

	public String getNrMatriculaCobrador() {
		return nrMatriculaCobrador;
	}

	public void setNrMatriculaCobrador(String nrMatriculaCobrador) {
		this.nrMatriculaCobrador = nrMatriculaCobrador;
	}

	public String getCdLocalPegada() {
		return cdLocalPegada;
	}

	public void setCdLocalPegada(String cdLocalPegada) {
		this.cdLocalPegada = cdLocalPegada;
	}

	public String getCdVeiculo() {
		return cdVeiculo;
	}

	public void setCdVeiculo(String cdVeiculo) {
		this.cdVeiculo = cdVeiculo;
	}

}
