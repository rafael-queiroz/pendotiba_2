package br.com.logic.pendotiba.logicbus.resources.dto;

import java.math.BigInteger;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.logic.pendotiba.core.model.Programacao;

public class TrocaCarroProgramacaoDTO {
	
	Long idProgramacao;
	Long idUsuario;
	String observacao;
	String hora;
	BigInteger roletaInicial1CarroAtual;
	BigInteger roletaInicial2CarroAtual;
	BigInteger roletaInicial3CarroAtual;
	String codigoEscala;
	String carroNumeroDeOrdem;
	Long idCarroSubstituido;
	

	@NotNull(message = "O campo motivo da troca é obrigatório")
	Long idTipoMotivoTroca;
	
	@NotNull(message = "O campo carro novo é obrigatório")
	Long idCarro;
	
	@NotNull(message = "O campo roleta final é obrigatório")
	BigInteger roletaFinal1;
	
	BigInteger roletaFinal2;
	
	BigInteger roletaFinal3; 
	
	@NotNull(message = "O campo roleta inicial 1 é obrigatório")
	BigInteger roletaInicial1;
	
	BigInteger roletaInicial2;
	
	BigInteger roletaInicial3;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy") 
	Date dataCompetencia;
	
	@NotNull(message = "O campo tipo de movimentação é obrigatório")
	Long idTipoMovimentacao;
	
	
	public TrocaCarroProgramacaoDTO() {
		
	}
	
	public TrocaCarroProgramacaoDTO(Programacao programacao) {
		this.idProgramacao = programacao.getId();
		this.roletaInicial1CarroAtual = programacao.carroAtual().getRoletaInicial1();
		this.roletaInicial2CarroAtual = BigInteger.ZERO;
		this.roletaInicial3CarroAtual = BigInteger.ZERO;
		this.dataCompetencia = programacao.getDataCompetencia();
		//this.codigoEscala = programacao.getCodigoEscala();
		this.carroNumeroDeOrdem = programacao.getCarroRealizado().getNumeroDeOrdem();
		this.idCarroSubstituido = programacao.getCarroRealizado().getId();
	}



	//GETTERS E SETTERS
	public Long getIdProgramacao() {
		return idProgramacao;
	}

	public void setIdProgramacao(Long idProgramacao) {
		this.idProgramacao = idProgramacao;
	}

	public Long getIdTipoMotivoTroca() {
		return idTipoMotivoTroca;
	}
	
	public void setIdTipoMotivoTroca(Long idTipoMotivoTroca) {
		this.idTipoMotivoTroca = idTipoMotivoTroca;
	}
	
	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Long getIdCarro() {
		return idCarro;
	}

	public void setIdCarro(Long idCarro) {
		this.idCarro = idCarro;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
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

	public BigInteger getRoletaInicial1CarroAtual() {
		return roletaInicial1CarroAtual;
	}

	public void setRoletaInicial1CarroAtual(BigInteger roletaInicial1CarroAtual) {
		this.roletaInicial1CarroAtual = roletaInicial1CarroAtual;
	}

	public BigInteger getRoletaInicial2CarroAtual() {
		return roletaInicial2CarroAtual;
	}

	public void setRoletaInicial2CarroAtual(BigInteger roletaInicial2CarroAtual) {
		this.roletaInicial2CarroAtual = roletaInicial2CarroAtual;
	}

	public BigInteger getRoletaInicial3CarroAtual() {
		return roletaInicial3CarroAtual;
	}

	public void setRoletaInicial3CarroAtual(BigInteger roletaInicial3CarroAtual) {
		this.roletaInicial3CarroAtual = roletaInicial3CarroAtual;
	}
	
	public Date getDataCompetencia() {
		return dataCompetencia;
	}
	
	public void setDataCompetencia(Date dataCompetencia) {
		this.dataCompetencia = dataCompetencia;
	}
	
	public String getCodigoEscala() {
		return codigoEscala;
	}
	
	public void setCodigoEscala(String codigoEscala) {
		this.codigoEscala = codigoEscala;
	}
	
	public String getCarroNumeroDeOrdem() {
		return carroNumeroDeOrdem;
	}
	
	public void setCarroNumeroDeOrdem(String carroNumeroDeOrdem) {
		this.carroNumeroDeOrdem = carroNumeroDeOrdem;
	}
	
	public boolean isNova() {
		return idProgramacao == null;
	}
	
	public Long getIdTipoMovimentacao() {
		return idTipoMovimentacao;
	}
	
	public void setIdTipoMovimentacao(Long idTipoMovimentacao) {
		this.idTipoMovimentacao = idTipoMovimentacao;
	}
	
	public Long getIdCarroSubstituido() {
		return idCarroSubstituido;
	}
	
	public void setIdCarroSubstituido(Long idCarroSubstituido) {
		this.idCarroSubstituido = idCarroSubstituido;
	}
	
}
