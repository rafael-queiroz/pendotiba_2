package br.com.logic.pendotiba.logicbus.resources.dto;

import java.math.BigInteger;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.logic.pendotiba.core.model.Programacao;
import br.com.logic.pendotiba.core.model.Status;
import br.com.logic.pendotiba.core.util.DataUtil;

public class ProgramacaoDTO {
	
	Long id;
	String dataCompetencia;
	String codigoEscala;
	String inicioTrabalho;
	String terminoTrabalho;
	String inicioJornada;
	String terminoJornada;
	String horaLiberacao;
	Long idPontoPegadaMotorista;
	Long idMotorista;
	Long idParceiro;
	Long idLinha;
	Long idTurno;
	Long idCarroProgramado;
	Long idCarroRealizado;
	Boolean ativo;
	Long liberadoGaragem;
	BigInteger roletaInicial1;
	BigInteger roletaInicial2;
	BigInteger roletaInicial3;
	BigInteger roletaFinal1;
	BigInteger roletaFinal2;
	BigInteger roletaFinal3;
	Long idUsuario;
	
	Integer mudouPontoPegada; //20190102
	Long idApp;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	Date dataCompetenciaDate;
	
	String carroStr;
	

	
	public ProgramacaoDTO() {
		
	}
	
	public ProgramacaoDTO(Programacao programacao) {
		this.id = programacao.getId();
		this.dataCompetencia = DataUtil.getDataStringYYYYMMDD(programacao.getDataCompetencia());
		//this.codigoEscala = programacao.getCodigoEscala();
		this.inicioTrabalho = DataUtil.getHoraMinuto(programacao.getInicioTrabalho());
		this.terminoTrabalho = DataUtil.getHoraMinuto(programacao.getTerminoTrabalho());
		this.inicioJornada =DataUtil.getHoraMinuto( programacao.getInicioJornada());
		this.terminoJornada =  DataUtil.getHoraMinuto(programacao.getTerminoJornada());
		this.horaLiberacao =  DataUtil.getHoraMinuto(programacao.getHoraLiberacao());
		this.idPontoPegadaMotorista = programacao.getPontoPegadaMotorista().getId();
		this.idMotorista = programacao.getMotorista() != null ? programacao.getMotorista().getId() : null;
		this.idParceiro = programacao.getParceiro() != null ? programacao.getParceiro().getId() : null;
		this.idLinha = programacao.getLinha() != null ? programacao.getLinha().getId() : null;
		this.idTurno = programacao.getTurno() != null ? programacao.getTurno().getId() : null;
		this.idCarroProgramado = programacao.getCarroProgramado() != null ? programacao.getCarroProgramado().getId() : null;
		this.idCarroRealizado = programacao.getCarroRealizado() != null ? programacao.getCarroRealizado().getId() : null;
		this.ativo = programacao.getStatus().getId().equals(Status.LIBERADO) ? true : false;
		this.roletaInicial1 = programacao.carroAtual() != null ? programacao.carroAtual().getRoletaInicial1() : null;
		this.roletaInicial2 = programacao.carroAtual() != null ? BigInteger.ZERO : null;
		this.roletaInicial3 = programacao.carroAtual() != null ? BigInteger.ZERO : null;
		this.mudouPontoPegada = programacao.getMudouPontoPegada();

		this.dataCompetenciaDate = programacao.getDataCompetencia();
		this.carroStr = programacao.getCarroRealizado() != null ? programacao.getCarroRealizado().getNumeroDeOrdem() : null;
	}



	//GETTERS E SETTERS
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDataCompetencia() {
		return dataCompetencia;
	}
	
	public void setDataCompetencia(String dataCompetencia) {
		this.dataCompetencia = dataCompetencia;
	}

	public String getCodigoEscala() {
		return codigoEscala;
	}

	public void setCodigoEscala(String codigoEscala) {
		this.codigoEscala = codigoEscala;
	}
	
	public String getInicioTrabalho() {
		return inicioTrabalho;
	}
	
	public void setInicioTrabalho(String inicioTrabalho) {
		this.inicioTrabalho = inicioTrabalho;
	}
	
	public String getTerminoTrabalho() {
		return terminoTrabalho;
	}
	
	public void setTerminoTrabalho(String terminoTrabalho) {
		this.terminoTrabalho = terminoTrabalho;
	}
	
	public String getInicioJornada() {
		return inicioJornada;
	}
	
	public void setInicioJornada(String inicioJornada) {
		this.inicioJornada = inicioJornada;
	}
	
	public String getTerminoJornada() {
		return terminoJornada;
	}
	
	public void setTerminoJornada(String terminoJornada) {
		this.terminoJornada = terminoJornada;
	}
	
	public String getHoraLiberacao() {
		return horaLiberacao;
	}
	
	public void setHoraLiberacao(String horaLiberacao) {
		this.horaLiberacao = horaLiberacao;
	}

	public Long getIdPontoPegadaMotorista() {
		return idPontoPegadaMotorista;
	}
	
	public void setIdPontoPegadaMotorista(Long idPontoPegadaMotorista) {
		this.idPontoPegadaMotorista = idPontoPegadaMotorista;
	}

	public Long getIdMotorista() {
		return idMotorista;
	}

	public void setIdMotorista(Long idMotorista) {
		this.idMotorista = idMotorista;
	}
	
	public Long getIdParceiro() {
		return idParceiro;
	}
	
	public void setIdParceiro(Long idParceiro) {
		this.idParceiro = idParceiro;
	}

	public Long getIdLinha() {
		return idLinha;
	}

	public void setIdLinha(Long idLinha) {
		this.idLinha = idLinha;
	}

	public Long getIdTurno() {
		return idTurno;
	}

	public void setIdTurno(Long idTurno) {
		this.idTurno = idTurno;
	}

	public Long getIdCarroProgramado() {
		return idCarroProgramado;
	}

	public void setIdCarroProgramado(Long idCarroProgramado) {
		this.idCarroProgramado = idCarroProgramado;
	}

	public Long getIdCarroRealizado() {
		return idCarroRealizado;
	}

	public void setIdCarroRealizado(Long idCarroRealizado) {
		this.idCarroRealizado = idCarroRealizado;
	}

	public Boolean getAtivo() {
		return ativo;
	}
	
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	
	public Long getLiberadoGaragem() {
		return liberadoGaragem;
	}
	
	public void setLiberadoGaragem(Long liberadoGaragem) {
		this.liberadoGaragem = liberadoGaragem;
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
	
	public Integer getMudouPontoPegada() {
		return mudouPontoPegada;
	}
	
	public void setMudouPontoPegada(Integer mudouPontoPegada) {
		this.mudouPontoPegada = mudouPontoPegada;
	}
	
	public Long getIdApp() {
		return idApp;
	}
	
	public void setIdApp(Long idApp) {
		this.idApp = idApp;
	}
		
	public Date getDataCompetenciaDate() {
		return dataCompetenciaDate;
	}
	
	public String getCarroStr() {
		return carroStr;
	}
	
	public Long getIdUsuario() {
		return idUsuario;
	}
	
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	
}
