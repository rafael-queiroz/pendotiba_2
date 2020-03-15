package br.com.logic.pendotiba.logicbus.resources.dto;

import java.io.Serializable;

public class RespostaViagemCadastroDTO implements Serializable{

	static final long serialVersionUID = -7666193894561232657L;

	Long id;
	Long idApp;
	
	
	
	public RespostaViagemCadastroDTO() {

	}
	
	public RespostaViagemCadastroDTO(Long id, Long idApp) {
		this.id = id;
		this.idApp = idApp;
	}
	


	public RespostaViagemCadastroDTO(Long id2, Integer idApp2) {
		// TODO Auto-generated constructor stub
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
