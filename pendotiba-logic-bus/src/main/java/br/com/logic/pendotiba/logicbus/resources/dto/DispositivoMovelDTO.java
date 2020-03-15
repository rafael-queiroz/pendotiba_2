package br.com.logic.pendotiba.logicbus.resources.dto;

import br.com.logic.pendotiba.core.model.DispositivoMovel;

public class DispositivoMovelDTO {

	Long id;
	String imei;
	Long idPonto;
	
	
	public DispositivoMovelDTO() {

	}
	
	public DispositivoMovelDTO(DispositivoMovel dispositivoMovel) {
		this.id = dispositivoMovel.getId();
		this.imei = dispositivoMovel.getImei();
		this.idPonto = dispositivoMovel.getPonto().getId();
	}

	
	
	//GETTERS E SETTERS
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getImei() {
		return imei;
	}
	
	public void setImei(String imei) {
		this.imei = imei;
	}
	
	public Long getIdPonto() {
		return idPonto;
	}
	
	public void setIdPonto(Long idPonto) {
		this.idPonto = idPonto;
	}
	
}
