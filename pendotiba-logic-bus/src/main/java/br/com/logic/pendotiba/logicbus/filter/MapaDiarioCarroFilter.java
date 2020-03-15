package br.com.logic.pendotiba.logicbus.filter;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.logic.pendotiba.core.model.BombaAbastecimento;
import br.com.logic.pendotiba.core.model.Carro;
import br.com.logic.pendotiba.core.model.TipoCarro;
import br.com.logic.pendotiba.core.util.DataUtil;

public class MapaDiarioCarroFilter {
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	Date dataInicial = new Date();
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	Date dataFinal = new Date();
	
	Carro carro;
	BombaAbastecimento bombaAbastecimento;
	TipoCarro tipoCarro;
	
	// 0 - Linha, 1 - Tipo de chassi, 2 - Carro
	Long agrupamento = 2L;
	
	
	
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
	
	public BombaAbastecimento getBombaAbastecimento() {
		return bombaAbastecimento;
	}
	
	public void setBombaAbastecimento(BombaAbastecimento bombaAbastecimento) {
		this.bombaAbastecimento = bombaAbastecimento;
	}
	
	public TipoCarro getTipoCarro() {
		return tipoCarro;
	}
	
	public void setTipoCarro(TipoCarro tipoCarro) {
		this.tipoCarro = tipoCarro;
	}
	
	public Long getAgrupamento() {
		return agrupamento;
	}
	
	public void setAgrupamento(Long agrupamento) {
		this.agrupamento = agrupamento;
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
		
		filtro.append(", Bomba de abastecimento: ");
		if(bombaAbastecimento != null) 
			filtro.append(bombaAbastecimento);
		else
			filtro.append("Todos");
		
		filtro.append(", Carro: ");
		if(carro != null) 
			filtro.append(carro);
		else
			filtro.append("Todos");
		
		filtro.append(", Agrupado por: ");
		if(agrupamento == 0L) 
			filtro.append("Linha");
		else
			filtro.append("Tipo de chassi");
	
		
		return filtro.toString();
	}
	
}
