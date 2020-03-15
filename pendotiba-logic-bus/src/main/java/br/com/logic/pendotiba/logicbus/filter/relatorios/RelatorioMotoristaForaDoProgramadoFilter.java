package br.com.logic.pendotiba.logicbus.filter.relatorios;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.logic.pendotiba.core.model.Funcionario;
import br.com.logic.pendotiba.core.util.DataUtil;

public class RelatorioMotoristaForaDoProgramadoFilter {

	@NotNull(message = "Data inicial é obrigatória")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	Date dataInicial = new Date();
	
	@NotNull(message = "Data final é obrigatória")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	Date dataFinal = new Date();
	
	Funcionario motorista;
	
	
	
	public RelatorioMotoristaForaDoProgramadoFilter() {
		
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
	
	public Funcionario getMotorista() {
		return motorista;
	}
	
	public void setMotorista(Funcionario motorista) {
		this.motorista = motorista;
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
		
		filtro.append(" e Motorista: ");
		if(motorista != null && motorista.getId() != null) 
			filtro.append(motorista.getMatricula().concat(" - ").concat(motorista.getNome()));
		else
			filtro.append("Todos");
	
		
		return filtro.toString();
	}
	
}
