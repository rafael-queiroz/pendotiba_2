package br.com.logic.pendotiba.logicbus.resources.dto;

public class RespostaMensagemDTO {
	
	
	String status;
	String mensagem;
	
	
	public RespostaMensagemDTO(String status, String mensagem) {
		this.status = status;
		this.mensagem = mensagem;
	}
	
	
	public String getMensagem() {
		return mensagem;
	}
	
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

}
