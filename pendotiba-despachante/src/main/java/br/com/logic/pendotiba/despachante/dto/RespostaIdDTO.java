package br.com.logic.pendotiba.despachante.dto;

import java.io.Serializable;

public class RespostaIdDTO implements Serializable{

	private static final long serialVersionUID = 1292314502571674766L;
	
	Long id;
	
	
	
	public RespostaIdDTO() {

	}
	
	public RespostaIdDTO(Long id) {
		this.id = id;
	}
	


	//GETTERS E SETTERS
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
