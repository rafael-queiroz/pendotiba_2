package br.com.logic.pendotiba.logicbus.dto;

import java.math.BigInteger;

import br.com.logic.pendotiba.core.model.Viagem;
import br.com.logic.pendotiba.core.util.DataUtil;

public class CadastroViagemDTO {
	
	Long id;
	String dataCompetencia;
	String horaSaidaProgramada;
	String horaSaidaRealizada;
	String horaChegada;
	BigInteger roletaFinal1;
	BigInteger roletaFinal2;
	BigInteger roletaFinal3;
	BigInteger ordemViagem;
	Long idProgramacao;
	Long idPontoOrigem;
	String pontoOrigem;
	Long idPontoDestino;
	String pontoDestino;
	Long idLinhaProgramada;
	String linhaProgramada;
	Long idLinhaRealizada;
	String linhaRealizada;
	Long idCarroRealizado;
	String carroRealizado;
	Long idTipoViagemPerdida;
	Long idUsuarioResponsavelChegada;
	Long idUsuarioResponsavelSaida;
	String sentido;
	String obsViagemPerdida;
	Boolean atualizada = false;
	
	
	public CadastroViagemDTO() {
		
	}

	public CadastroViagemDTO(Viagem viagem) {
		this.id = viagem.getId();
		this.dataCompetencia = DataUtil.getDataStringYYYYMMDD(viagem.getDataCompetencia());
		this.horaSaidaProgramada = DataUtil.getHoraMinuto(viagem.getHoraSaidaProgramada());
		this.horaSaidaRealizada = DataUtil.getHoraMinuto(viagem.getHoraSaidaRealizada());
		this.horaChegada = DataUtil.getHoraMinuto(viagem.getHoraChegada());
		this.roletaFinal1 = viagem.getRoletaFinal1();
		this.roletaFinal2 = BigInteger.ZERO;
		this.roletaFinal3 = BigInteger.ZERO;
		this.ordemViagem =  viagem.getOrdemViagem();
		this.idProgramacao =  viagem.getProgramacao().getId();
		this.idPontoOrigem =  viagem.getPontoLinha() != null ? viagem.getPontoLinha().getPontoOrigem().getId() : null;
		this.pontoOrigem =  viagem.getPontoLinha() != null ? viagem.getPontoLinha().getPontoOrigem().toString() : null;
		this.idPontoDestino =  viagem.getPontoLinha() != null ? viagem.getPontoLinha().getPontoDestino().getId() : null;
		this.pontoDestino =  viagem.getPontoLinha() != null ? viagem.getPontoLinha().getPontoDestino().toString() : null;
		this.idLinhaProgramada =  viagem.getLinhaProgramada() != null ? viagem.getLinhaProgramada().getId() : null;
		this.linhaProgramada =  viagem.getLinhaProgramada() != null ? viagem.getLinhaProgramada().toString() : null;
		this.idLinhaRealizada =  viagem.getLinhaRealizada() != null ? viagem.getLinhaRealizada().getId() : null;
		this.linhaRealizada =  viagem.getLinhaRealizada() != null ? viagem.getLinhaRealizada().toString() : null;
		this.idCarroRealizado =  viagem.getCarroRealizado() != null ? viagem.getCarroRealizado().getId() : null;
		this.carroRealizado =  viagem.getCarroRealizado() != null ? viagem.getCarroRealizado().toString() : null;
		this.idTipoViagemPerdida =  viagem.getTipoViagemPerdida() != null ?  viagem.getTipoViagemPerdida().getId() : null;
		this.idUsuarioResponsavelChegada =  viagem.getUsuarioResponsavelChegada() != null ? viagem.getUsuarioResponsavelChegada().getId() : null;
		this.idUsuarioResponsavelSaida =  viagem.getUsuarioResponsavelSaida() != null ?  viagem.getUsuarioResponsavelSaida().getId() : null;
		this.sentido = viagem.getPontoLinha() != null ? viagem.getPontoLinha().getSentido() : null;
		this.obsViagemPerdida =  viagem.getObsViagemPerdida();
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

	public String getHoraSaidaProgramada() {
		return horaSaidaProgramada;
	}

	public void setHoraSaidaProgramada(String horaSaidaProgramada) {
		this.horaSaidaProgramada = horaSaidaProgramada;
	}

	public String getHoraSaidaRealizada() {
		return horaSaidaRealizada;
	}

	public void setHoraSaidaRealizada(String horaSaidaRealizada) {
		this.horaSaidaRealizada = horaSaidaRealizada;
	}
	
	public String getHoraChegada() {
		return horaChegada;
	}
	
	public void setHoraChegada(String horaChegada) {
		this.horaChegada = horaChegada;
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
	
	public BigInteger getOrdemViagem() {
		return ordemViagem;
	}
	
	public void setOrdemViagem(BigInteger ordemViagem) {
		this.ordemViagem = ordemViagem;
	}

	public String getObsViagemPerdida() {
		return obsViagemPerdida;
	}
	
	public void setObsViagemPerdida(String obsViagemPerdida) {
		this.obsViagemPerdida = obsViagemPerdida;
	}

	public Long getIdProgramacao() {
		return idProgramacao;
	}

	public void setIdProgramacao(Long idProgramacao) {
		this.idProgramacao = idProgramacao;
	}

	public Long getIdPontoOrigem() {
		return idPontoOrigem;
	}
	
	public void setIdPontoOrigem(Long idPontoOrigem) {
		this.idPontoOrigem = idPontoOrigem;
	}
	
	public Long getIdPontoDestino() {
		return idPontoDestino;
	}
	
	public void setIdPontoDestino(Long idPontoDestino) {
		this.idPontoDestino = idPontoDestino;
	}
	
	public Long getIdLinhaProgramada() {
		return idLinhaProgramada;
	}

	public void setIdLinhaProgramada(Long idLinhaProgramada) {
		this.idLinhaProgramada = idLinhaProgramada;
	}

	public Long getIdLinhaRealizada() {
		return idLinhaRealizada;
	}

	public void setIdLinhaRealizada(Long idLinhaRealizada) {
		this.idLinhaRealizada = idLinhaRealizada;
	}
	public Long getIdCarroRealizado() {
		return idCarroRealizado;
	}

	public void setIdCarroRealizado(Long idCarroRealizado) {
		this.idCarroRealizado = idCarroRealizado;
	}

	public Long getIdTipoViagemPerdida() {
		return idTipoViagemPerdida;
	}

	public void setIdTipoViagemPerdida(Long idTipoViagemPerdida) {
		this.idTipoViagemPerdida = idTipoViagemPerdida;
	}

	public Long getIdUsuarioResponsavelChegada() {
		return idUsuarioResponsavelChegada;
	}

	public void setIdUsuarioResponsavelChegada(Long idUsuarioResponsavelChegada) {
		this.idUsuarioResponsavelChegada = idUsuarioResponsavelChegada;
	}

	public Long getIdUsuarioResponsavelSaida() {
		return idUsuarioResponsavelSaida;
	}

	public void setIdUsuarioResponsavelSaida(Long idUsuarioResponsavelSaida) {
		this.idUsuarioResponsavelSaida = idUsuarioResponsavelSaida;
	}
	
	public Boolean getAtualizada() {
		return atualizada;
	}
	
	public void setAtualizada(Boolean atualizada) {
		this.atualizada = atualizada;
	}
	
	public String getSentido() {
		return sentido;
	}
	
	public void setSentido(String sentido) {
		this.sentido = sentido;
	}

	public String getPontoOrigem() {
		return pontoOrigem;
	}

	public void setPontoOrigem(String pontoOrigem) {
		this.pontoOrigem = pontoOrigem;
	}

	public String getPontoDestino() {
		return pontoDestino;
	}

	public void setPontoDestino(String pontoDestino) {
		this.pontoDestino = pontoDestino;
	}

	public String getLinhaProgramada() {
		return linhaProgramada;
	}

	public void setLinhaProgramada(String linhaProgramada) {
		this.linhaProgramada = linhaProgramada;
	}

	public String getLinhaRealizada() {
		return linhaRealizada;
	}

	public void setLinhaRealizada(String linhaRealizada) {
		this.linhaRealizada = linhaRealizada;
	}

	public String getCarroRealizado() {
		return carroRealizado;
	}

	public void setCarroRealizado(String carroRealizado) {
		this.carroRealizado = carroRealizado;
	}
	
	@Override
	public String toString() {
		return horaSaidaProgramada;
	}
}
