package br.com.logic.pendotiba.logicbus.listener;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import br.com.logic.pendotiba.core.model.EntradaSaidaDeCarroDaGaragem;

public class EntradaSaidaDeCarroDaGaragemListener {
	
	@PrePersist
	@PreUpdate
	void prePestistPreUpdate(EntradaSaidaDeCarroDaGaragem obj) {
		obj.calcularDistanciaPercorrida();
		obj.setDataHoraCadastro(new Date());
	}
	

}
