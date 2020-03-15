package br.com.logic.pendotiba.logicbus.filter;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.logic.pendotiba.core.model.BombaAbastecimento;
import br.com.logic.pendotiba.core.util.DataUtil;

public class MapaDiarioBombaAbastecimentoFilter {
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	Date dataInicial = new Date();
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	Date dataFinal = new Date();
	
	BombaAbastecimento bombaAbastecimento;
	
	
	
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
	
	public BombaAbastecimento getBombaAbastecimento() {
		return bombaAbastecimento;
	}
	
	public void setBombaAbastecimento(BombaAbastecimento bombaAbastecimento) {
		this.bombaAbastecimento = bombaAbastecimento;
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
		
		filtro.append(" e Bomba de abastecimento: ");
		if(bombaAbastecimento != null) 
			filtro.append(bombaAbastecimento.getDescricao());
		else
			filtro.append("Todos");
	
		
		return filtro.toString();
	}
	
}
