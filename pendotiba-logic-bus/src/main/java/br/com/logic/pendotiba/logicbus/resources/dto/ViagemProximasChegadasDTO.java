package br.com.logic.pendotiba.logicbus.resources.dto;

import java.math.BigInteger;

import br.com.logic.pendotiba.core.model.Viagem;
import br.com.logic.pendotiba.core.util.DataUtil;

public class ViagemProximasChegadasDTO {
	
	Long id;
	String horaSaida;
	Long idProgramacao;
	Long idLinha;
	String linha;
	Long idCarro;
	String carro;
	BigInteger roletaFinal1;
	BigInteger roletaFinal2;
	BigInteger roletaFinal3;
	Long idTipoViagemPerdida;
	BigInteger ordemViagem;
	Long idPontoOrigem;
	Long idMotorista;
	
	
	public ViagemProximasChegadasDTO() {
		
	}

	public ViagemProximasChegadasDTO(Viagem viagem) {
		this.id = viagem.getId();
		this.horaSaida = DataUtil.getHoraMinuto(viagem.getHoraSaidaRealizada());
		this.idProgramacao =  viagem.getProgramacao().getId();
		this.idLinha =  viagem.getLinhaRealizada() != null ? viagem.getLinhaRealizada().getId() : null;
		this.linha = viagem.getLinhaRealizada() != null ? viagem.getLinhaRealizada().getCodigo() : null;
		this.idCarro =  viagem.getCarroRealizado() != null ? viagem.getCarroRealizado().getId() : null;
		this.carro = viagem.getCarroRealizado() != null ? viagem.getCarroRealizado().getNumeroDeOrdem() : null; 
		this.roletaFinal1 = viagem.getRoletaFinal1();
		this.roletaFinal2 = BigInteger.ZERO;
		this.roletaFinal3 = BigInteger.ZERO;
		this.idTipoViagemPerdida = viagem.getTipoViagemPerdida() != null ? viagem.getTipoViagemPerdida().getId() : null;
		this.idPontoOrigem = viagem.getPontoLinha() != null ? viagem.getPontoLinha().getPontoOrigem().getId() : null;
		this.ordemViagem = viagem.getOrdemViagem();
		this.idMotorista = viagem.getProgramacao().getMotorista().getId();
	}



	//GETTERS E SETTERS
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getHoraSaida() {
		return horaSaida;
	}
	
	public void setHoraSaida(String horaSaida) {
		this.horaSaida = horaSaida;
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
	
	public Long getIdProgramacao() {
		return idProgramacao;
	}

	public void setIdProgramacao(Long idProgramacao) {
		this.idProgramacao = idProgramacao;
	}

	public Long getIdLinha() {
		return idLinha;
	}

	public void setIdLinha(Long idLinha) {
		this.idLinha = idLinha;
	}
	
	public String getLinha() {
		return linha;
	}
	
	public void setLinha(String linha) {
		this.linha = linha;
	}
	
	public Long getIdCarro() {
		return idCarro;
	}

	public void setIdCarro(Long idCarro) {
		this.idCarro = idCarro;
	}
	
	public String getCarro() {
		return carro;
	}
	
	public void setCarro(String carro) {
		this.carro = carro;
	}
	
	public Long getIdTipoViagemPerdida() {
		return idTipoViagemPerdida;
	}
	
	public void setIdTipoViagemPerdida(Long idTipoViagemPerdida) {
		this.idTipoViagemPerdida = idTipoViagemPerdida;
	}

	public Long getIdPontoOrigem() {
		return idPontoOrigem;
	}
	
	public void setIdPontoOrigem(Long idPontoOrigem) {
		this.idPontoOrigem = idPontoOrigem;
	}
	
	public BigInteger getOrdemViagem() {
		return ordemViagem;
	}
	
	public void setOrdemViagem(BigInteger ordemViagem) {
		this.ordemViagem = ordemViagem;
	}
	
	public Long getIdMotorista() {
		return idMotorista;
	}
	
	public void setIdMotorista(Long idMotorista) {
		this.idMotorista = idMotorista;
	}
}
