package br.com.logic.pendotiba.despachante.dto;

public class TrocaMotoristaProgramacaoDTO {
	
	Long idProgramacao;
	Long idMotorista;

	
	
	public TrocaMotoristaProgramacaoDTO() {
		
	}
	

	
	//GETTERS E SETTERS
	public Long getIdProgramacao() {
		return idProgramacao;
	}

	public void setIdProgramacao(Long idProgramacao) {
		this.idProgramacao = idProgramacao;
	}

	public Long getIdMotorista() {
		return idMotorista;
	}
	
	public void setIdMotorista(Long idMotorista) {
		this.idMotorista = idMotorista;
	}
	
}
