package br.com.logic.pendotiba.logicbus.resources.dto;

public class ObservacaoDTO {
	
	Long idProgramacao;
	Long idTipoObservacao;
	String descricao;
	Long idCarro;
	Long idLinha;
	Long idUsuario;
	String hora;
	
	
	public ObservacaoDTO() {
		
	}


	
	//GETTERS E SETTERS
	public Long getIdProgramacao() {
		return idProgramacao;
	}

	public void setIdProgramacao(Long idProgramacao) {
		this.idProgramacao = idProgramacao;
	}

	public Long getIdTipoObservacao() {
		return idTipoObservacao;
	}
	
	public void setIdTipoObservacao(Long idTipoObservacao) {
		this.idTipoObservacao = idTipoObservacao;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
