package br.com.logic.pendotiba.logicbus.filter.relatorios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

import br.com.logic.pendotiba.core.model.Linha;
import br.com.logic.pendotiba.core.model.Turno;
import br.com.logic.pendotiba.core.util.DataUtil;

public class RelatorioEntradaSaidaPorHorarioFilter {
	
	Linha linha;
	Turno turno;
	
	String datasStr;
	
	List<Date> datas = new ArrayList<>();
	
	
	
	//GETTERS E SETTERS
	public Linha getLinha() {
		return linha;
	}
	
	public void setLinha(Linha linha) {
		this.linha = linha;
	}
	
	public Turno getTurno() {
		return turno;
	}
	
	public void setTurno(Turno turno) {
		this.turno = turno;
	}
	
	public String getDatasStr() {
		return datasStr;
	}
	
	public void setDatasStr(String datasStr) {
		this.datasStr = datasStr;
	}
	
	public List<Date> getDatas() {
		datas.clear();
		if(!StringUtils.isEmpty(datasStr)) {
			String dataStr[] = datasStr.split(",");
			for(int i = 0; i < dataStr.length; i++)
				datas.add(DataUtil.getDateDDMMYYYYReturnDDMMYYYY(dataStr[i]));
		}
		return datas;
	}
	
	public void setDatas(List<Date> datas) {
		this.datas = datas;
	}
	

	public String toString() {
		StringBuilder filtro = new StringBuilder();
		
		filtro.append("Linha: ");
		if(linha != null) 
			filtro.append(linha);
		else
			filtro.append("Todos");
		
		filtro.append(", Turno: ");
		if(turno != null) 
			filtro.append(turno.getDescricao());
		else
			filtro.append("Todos");
		
		filtro.append(" e Datas: ");
		if(!datasStr.isEmpty()) 
			filtro.append(datasStr);
		else
			filtro.append("Todas");
		
		return filtro.toString();
	}
	
}
