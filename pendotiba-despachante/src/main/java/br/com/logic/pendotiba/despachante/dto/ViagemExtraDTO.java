package br.com.logic.pendotiba.despachante.dto;

import java.io.Serializable;
import java.math.BigInteger;

import br.com.logic.pendotiba.core.model.Viagem;

public class ViagemExtraDTO implements Serializable{

	private static final long serialVersionUID = 1292314502571674766L;
	
	Long id;
	Long idProgramacao;
	BigInteger ordemViagem;
	Long idPontoOrigem;	
	
	
	public ViagemExtraDTO() {

	}
	
	public ViagemExtraDTO(Viagem viagem) {
		this.id = viagem.getId();
		this.idProgramacao = viagem.getProgramacao().getId();
		this.ordemViagem = viagem.getOrdemViagem();
		this.idPontoOrigem = viagem.getPontoLinha().getPontoOrigem().getId();
	}



	//GETTERS E SETTERS
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getIdProgramacao() {
		return idProgramacao;
	}
	
	public void setIdProgramacao(Long idProgramacao) {
		this.idProgramacao = idProgramacao;
	}
	
	public BigInteger getOrdemViagem() {
		return ordemViagem;
	}
	
	public void setOrdemViagem(BigInteger ordemViagem) {
		this.ordemViagem = ordemViagem;
	}

	public Long getIdPontoOrigem() {
		return idPontoOrigem;
	}
	
	public void setIdPontoOrigem(Long idPontoOrigem) {
		this.idPontoOrigem = idPontoOrigem;
	}
}
