package br.com.logic.pendotiba.despachante.dto;

import java.math.BigInteger;

import br.com.logic.pendotiba.core.model.Viagem;
import br.com.logic.pendotiba.core.util.DataUtil;

public class ViagemDTO {
	
	static final long serialVersionUID = -1508012999458939831L;
	
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
	Long idPontoDestino;
	Long idLinhaProgramada;
	Long idLinhaRealizada;
	Long idCarroRealizado;
	Long idTipoViagemPerdida;
	Long idMotivoPuloViagem;
	Long idUsuarioResponsavelChegada;
	Long idUsuarioResponsavelSaida;
	String obsViagemPerdida;
	String obsViagemPulada;
	Boolean atualizada = false;
	
	BigInteger flagEditada;
	Long idLinhaTrocada;
	
	Long idApp;
	

	Long flagSD;
	String horaSD;
	
	
	public ViagemDTO() {
		
	}

	public ViagemDTO(Viagem viagem) {
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
		this.idPontoDestino =  viagem.getPontoLinha() != null ? viagem.getPontoLinha().getPontoDestino().getId() : null;
		this.idLinhaProgramada =  viagem.getLinhaProgramada() != null ? viagem.getLinhaProgramada().getId() : null;
		this.idLinhaRealizada =  viagem.getLinhaRealizada() != null ? viagem.getLinhaRealizada().getId() : null;
		this.idCarroRealizado =  viagem.getCarroRealizado() != null ? viagem.getCarroRealizado().getId() : null;
		this.idTipoViagemPerdida =  viagem.getTipoViagemPerdida() != null ?  viagem.getTipoViagemPerdida().getId() : null;
		this.idMotivoPuloViagem=  viagem.getMotivoPuloViagem() != null ?  viagem.getMotivoPuloViagem().getId() : null;
		this.idUsuarioResponsavelChegada =  viagem.getUsuarioResponsavelChegada() != null ? viagem.getUsuarioResponsavelChegada().getId() : null;
		this.idUsuarioResponsavelSaida =  viagem.getUsuarioResponsavelSaida() != null ?  viagem.getUsuarioResponsavelSaida().getId() : null;
		this.obsViagemPerdida =  viagem.getObsViagemPerdida();
		this.obsViagemPulada =  viagem.getObsViagemPulada();
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
	
	public BigInteger getFlagEditada() {
		return flagEditada;
	}
	
	public void setFlagEditada(BigInteger flagEditada) {
		this.flagEditada = flagEditada;
	}
	
	public Long getIdApp() {
		return idApp;
	}
	
	public void setIdApp(Long idApp) {
		this.idApp = idApp;
	}
	
	public Long getIdMotivoPuloViagem() {
		return idMotivoPuloViagem;
	}
	
	public void setIdMotivoPuloViagem(Long idMotivoPuloViagem) {
		this.idMotivoPuloViagem = idMotivoPuloViagem;
	}
	
	public String getObsViagemPulada() {
		return obsViagemPulada;
	}
	
	public void setObsViagemPulada(String obsViagemPulada) {
		this.obsViagemPulada = obsViagemPulada;
	}
	
	public Long getFlagSD() {
		return flagSD;
	}
	
	public void setFlagSD(Long flagSD) {
		this.flagSD = flagSD;
	}
	
	public String getHoraSD() {
		return horaSD;
	}
	
	public void setHoraSD(String horaSD) {
		this.horaSD = horaSD;
	}
}
