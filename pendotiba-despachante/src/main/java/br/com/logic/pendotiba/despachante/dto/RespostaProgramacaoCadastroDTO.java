package br.com.logic.pendotiba.despachante.dto;

import java.io.Serializable;

public class RespostaProgramacaoCadastroDTO implements Serializable{

	private static final long serialVersionUID = 1292314502571674766L;
	
	Long id;
	Long idApp;
	
	
	
	public RespostaProgramacaoCadastroDTO() {

	}
	
	public RespostaProgramacaoCadastroDTO(Long id, Long idApp) {
		this.id = id;
		this.idApp = idApp;
	}


	//GETTERS E SETTERS
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getIdApp() {
		return idApp;
	}
	
	public void setIdApp(Long idApp) {
		this.idApp = idApp;
	}

}
