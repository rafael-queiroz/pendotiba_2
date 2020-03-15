package br.com.logic.pendotiba.logicbus.resources.dto;

public class ReclamacaoDTO {
	
	Long idProgramacao;
	Long idTipoReclamacao;
	String observacao;
	Long idCarro;
	Long idLinha;
	Long idUsuario;
	String hora;
	
	
	
	public ReclamacaoDTO() {
		
	}


	
	//GETTERS E SETTERS
	public Long getIdProgramacao() {
		return idProgramacao;
	}

	public void setIdProgramacao(Long idProgramacao) {
		this.idProgramacao = idProgramacao;
	}

	public Long getIdTipoReclamacao() {
		return idTipoReclamacao;
	}

	public void setIdTipoReclamacao(Long idTipoReclamacao) {
		this.idTipoReclamacao = idTipoReclamacao;
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

	public Long getIdLinha() {
		return idLinha;
	}

	public void setIdLinha(Long idLinha) {
		this.idLinha = idLinha;
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
	
}
