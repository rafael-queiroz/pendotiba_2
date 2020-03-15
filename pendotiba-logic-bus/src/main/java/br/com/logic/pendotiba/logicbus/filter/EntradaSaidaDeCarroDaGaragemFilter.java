package br.com.logic.pendotiba.logicbus.filter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import br.com.logic.pendotiba.core.model.Carro;
import br.com.logic.pendotiba.core.model.Linha;
import br.com.logic.pendotiba.core.model.Turno;
import br.com.logic.pendotiba.core.util.DataUtil;

public class EntradaSaidaDeCarroDaGaragemFilter {
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	Date dataInicial = new Date();
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	Date dataFinal = new Date();
	
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	Date horaInicial;
	
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	Date horaFinal;
	
	Carro carro;
	Linha linha;
	Turno turno;
	
	String datasStr;
	
	List<Date> datas = new ArrayList<>();
	
	
	
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
	
	public Date getHoraInicial() {
		return horaInicial;
	}

	public void setHoraInicial(Date horaInicial) {
		this.horaInicial = horaInicial;
	}

	public Date getHoraFinal() {
		return horaFinal;
	}

	public void setHoraFinal(Date horaFinal) {
		this.horaFinal = horaFinal;
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
		
		filtro.append(", Hora inicial: ");
		if(horaInicial != null) 
			filtro.append(DataUtil.getHoraMinuto(horaInicial));
		else
			filtro.append("Todos");
		
		filtro.append(", Hora final: ");
		if(horaFinal != null)
			filtro.append(DataUtil.getHoraMinuto(horaFinal));
		else
			filtro.append("Todos");
		
		filtro.append(", Linha: ");
		if(linha != null) 
			filtro.append(linha);
		else
			filtro.append("Todos");
		
		filtro.append(", Turno: ");
		if(turno != null) 
			filtro.append(turno.getDescricao());
		else
			filtro.append("Todos");
		
		filtro.append(" e Carro: ");
		if(carro != null) 
			filtro.append(carro);
		else
			filtro.append("Todos");
	
		
		return filtro.toString();
	}
	
}
