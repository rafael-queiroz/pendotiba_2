package br.com.logic.pendotiba.despachante.dto;

import br.com.logic.pendotiba.core.model.Viagem;

public class ViagemPerdidaDTO {
	
	Long id;
	Long idTipoViagemPerdida;
	Long idPontoOrigem;
	
	
	public ViagemPerdidaDTO() {
		
	}

	public ViagemPerdidaDTO(Viagem obj) {
		this.id = obj.getId();
		this.idTipoViagemPerdida = obj.getTipoViagemPerdida() != null ? obj.getTipoViagemPerdida().getId() : null;
		this.idPontoOrigem = obj.getPontoLinha().getPontoOrigem() != null ? obj.getPontoLinha().getPontoOrigem().getId() : null;
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
