package br.com.logic.pendotiba.logicbus.resources.dto;

import br.com.logic.pendotiba.core.model.Viagem;

public class ViagemPerdidaDTO {
	
	Long id;
	Long idTipoViagemPerdida;
	Long idPontoOrigem;
	
	
	public ViagemPerdidaDTO() {
		
	}

	public ViagemPerdidaDTO(Viagem viagem) {
		this.id = viagem.getId();
		this.idTipoViagemPerdida = viagem.getTipoViagemPerdida() != null ? viagem.getTipoViagemPerdida().getId() : null;
		this.idPontoOrigem = viagem.getPontoLinha().getPontoOrigem() != null ? viagem.getPontoLinha().getPontoOrigem().getId() : null;
	}



	//GETTERS E SETTERS
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getIdPontoOrigem() {
		return idPontoOrigem;
	}
	
	public void setIdPontoOrigem(Long idPontoOrigem) {
		this.idPontoOrigem = idPontoOrigem;
	}
	
	public Long getIdTipoViagemPerdida() {
		return idTipoViagemPerdida;
	}
	
	public void setIdTipoViagemPerdida(Long idTipoViagemPerdida) {
		this.idTipoViagemPerdida = idTipoViagemPerdida;
	}

}
