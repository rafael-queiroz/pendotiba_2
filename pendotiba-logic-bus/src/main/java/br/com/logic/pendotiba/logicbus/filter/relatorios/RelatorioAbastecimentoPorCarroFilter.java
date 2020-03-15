package br.com.logic.pendotiba.logicbus.filter.relatorios;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.logic.pendotiba.core.model.Carro;
import br.com.logic.pendotiba.core.util.DataUtil;

public class RelatorioAbastecimentoPorCarroFilter {

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	Date dataInicial = new Date();
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	Date dataFinal = new Date();
	
	Carro carro;
	
	
	
	public RelatorioAbastecimentoPorCarroFilter() {
		
	}
	
	
	
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
	
	
	
	public String toString() {
		StringBuilder filtro = new StringBuilder();
		
		filtro.append("Data inicial: ");
		if(dataInicial != null) 
			filtro.append(DataUtil.getDataStringDDMMYYYY(dataInicial));
		else
			filtro.append("Todos");
		
		filtro.append(", Data final: ");
		if(dataFinal != null)
			filtro.append(DataUtil.getDataStringDDMMYYYY(dataFinal));
		else
			filtro.append("Todos");
		
		filtro.append(" e Carro: ");
		if(carro != null) 
			filtro.append(carro.getNumeroDeOrdem());
		else
			filtro.append("Todos");
	
		
		return filtro.toString();
	}
	
}
