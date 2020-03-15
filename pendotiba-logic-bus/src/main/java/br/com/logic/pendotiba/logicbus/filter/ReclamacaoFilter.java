package br.com.logic.pendotiba.logicbus.filter;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.logic.pendotiba.core.model.Carro;
import br.com.logic.pendotiba.core.model.Funcionario;
import br.com.logic.pendotiba.core.model.Linha;
import br.com.logic.pendotiba.core.model.TipoReclamacao;

public class ReclamacaoFilter {
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	Date dataInicial = new Date();
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	Date dataFinal = new Date();
	
	Carro carro;
	Funcionario motorista;
	Linha linha;
	TipoReclamacao tipoReclamacao;
	
	
	//GETTERS E SETTERS
	public Date getDataInicial() {
		return dataInicial;
	}
	
	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}
	
	public Date getDataFinal() {
		return dataFinal;
	}
	
	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public Carro getCarro() {
		return carro;
	}
	
	public void setCarro(Carro carro) {
		this.carro = carro;
	}
	
	public Funcionario getMotorista() {
		return motorista;
	}
	
	public void setMotorista(Funcionario motorista) {
		this.motorista = motorista;
	}

	public Linha getLinha() {
		return linha;
	}

	public void setLinha(Linha linha) {
		this.linha = linha;
	}
	
	public TipoReclamacao getTipoReclamacao() {
		return tipoReclamacao;
	}
	
	public void setTipoReclamacao(TipoReclamacao tipoReclamacao) {
		this.tipoReclamacao = tipoReclamacao;
	}
	
}
