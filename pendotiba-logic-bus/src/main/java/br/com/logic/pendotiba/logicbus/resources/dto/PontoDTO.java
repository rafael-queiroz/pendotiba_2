package br.com.logic.pendotiba.logicbus.resources.dto;

import br.com.logic.pendotiba.core.model.Ponto;
import br.com.logic.pendotiba.core.util.DataUtil;

public class PontoDTO {

	Long id;
	String codigo;
	String descricao;
	String tempoPrestaConta;
	Long idTerminal;

	
	
	public PontoDTO() {

	}
	
	public PontoDTO(Ponto ponto) {
		this.id = ponto.getId();
		this.codigo = ponto.getCodigo();
		this.descricao = ponto.getDescricao();
		this.tempoPrestaConta = DataUtil.getHoraMinuto(ponto.getTempoPrestaConta());
		this.idTerminal = ponto.getTerminal() != null ? ponto.getTerminal().getId() : null;
	}
	
	
	
	//GETTERS E SETTERS
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getTempoPrestaConta() {
		return tempoPrestaConta;
	}
	
	public void setTempoPrestaConta(String tempoPrestaConta) {
		this.tempoPrestaConta = tempoPrestaConta;
	}
	
	public Long getIdTerminal() {
		return idTerminal;
	}
	
	public void setIdTerminal(Long idTerminal) {
		this.idTerminal = idTerminal;
	}
}
