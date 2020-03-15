package br.com.logic.pendotiba.logicbus.dto.relatorios;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

public class RelatorioEntradaSaidaPorHorarioDTO implements Comparable<RelatorioEntradaSaidaPorHorarioDTO>{
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	Date dataCompetencia;
	
	
	int quantidadeFaixa00;
	int quantidadeFaixa01;
	int quantidadeFaixa02;
	int quantidadeFaixa03;
	int quantidadeFaixa04;
	int quantidadeFaixa05;
	int quantidadeFaixa06;
	int quantidadeFaixa07;
	int quantidadeFaixa08;
	int quantidadeFaixa09;
	int quantidadeFaixa10;
	int quantidadeFaixa11;
	int quantidadeFaixa12;
	int quantidadeFaixa13;
	int quantidadeFaixa14;
	int quantidadeFaixa15;
	int quantidadeFaixa16;
	int quantidadeFaixa17;
	int quantidadeFaixa18;
	int quantidadeFaixa19;
	int quantidadeFaixa20;
	int quantidadeFaixa21;
	int quantidadeFaixa22;
	int quantidadeFaixa23;
	
	
	public RelatorioEntradaSaidaPorHorarioDTO() {
		
	}



	public RelatorioEntradaSaidaPorHorarioDTO(Date dataCompetencia) {
		this.dataCompetencia = dataCompetencia;
	}
	
	
	public void adicionarQuantidadePorHora(String hora) {
		switch (hora) {        
            case ("00"):
                quantidadeFaixa00 = quantidadeFaixa00+1;
                break;
     
            case ("01"):
                quantidadeFaixa01 = quantidadeFaixa01+1;
                break;
                
            case ("02"):
                quantidadeFaixa02 = quantidadeFaixa02+1;
                break;
                
            case ("03"):
                quantidadeFaixa03 = quantidadeFaixa03+1;
                break;
                
            case ("04"):
                quantidadeFaixa04 = quantidadeFaixa04+1;
                break;
     
            case ("05"):
                quantidadeFaixa05 = quantidadeFaixa05+1;
                break;
                
            case ("06"):
                quantidadeFaixa06 = quantidadeFaixa06+1;
                break;
                
            case ("07"):
                quantidadeFaixa07 = quantidadeFaixa07+1;
                break;
                
            case ("08"):
                quantidadeFaixa08 = quantidadeFaixa08+1;
                break;
     
            case ("09"):
                quantidadeFaixa09 = quantidadeFaixa09+1;
                break;
                
            case ("10"):
                quantidadeFaixa10 = quantidadeFaixa10+1;
                break;
                
            case ("11"):
                quantidadeFaixa11 = quantidadeFaixa11+1;
                break;
                
            case ("12"):
                quantidadeFaixa12 = quantidadeFaixa12+1;
                break;
     
            case ("13"):
                quantidadeFaixa13 = quantidadeFaixa13+1;
                break;
                
            case ("14"):
                quantidadeFaixa14 = quantidadeFaixa14+1;
                break;
                
            case ("15"):
                quantidadeFaixa15 = quantidadeFaixa15+1;
                break;
                
            case ("16"):
                quantidadeFaixa16 = quantidadeFaixa16+1;
                break;
     
            case ("17"):
                quantidadeFaixa17 = quantidadeFaixa17+1;
                break;
                
            case ("18"):
                quantidadeFaixa18 = quantidadeFaixa18+1;
                break;
                
            case ("19"):
                quantidadeFaixa19 = quantidadeFaixa19+1;
                break;
                
            case ("20"):
                quantidadeFaixa20 = quantidadeFaixa20+1;
                break;
     
            case ("21"):
                quantidadeFaixa21 = quantidadeFaixa21+1;
                break;
                
            case ("22"):
                quantidadeFaixa22 = quantidadeFaixa22+1;
                break;
                
            case ("23"):
                quantidadeFaixa23 = quantidadeFaixa23+1;
                break;
            
            default:
            	
		}
	}



	//GETTERS E SETTERS
	public Date getDataCompetencia() {
		return dataCompetencia;
	}
	
	public void setDataCompetencia(Date dataCompetencia) {
		this.dataCompetencia = dataCompetencia;
	}

	public int getQuantidadeFaixa00() {
		return quantidadeFaixa00;
	}

	public int getQuantidadeFaixa01() {
		return quantidadeFaixa01;
	}

	public int getQuantidadeFaixa02() {
		return quantidadeFaixa02;
	}

	public int getQuantidadeFaixa03() {
		return quantidadeFaixa03;
	}

	public int getQuantidadeFaixa04() {
		return quantidadeFaixa04;
	}

	public int getQuantidadeFaixa05() {
		return quantidadeFaixa05;
	}

	public int getQuantidadeFaixa06() {
		return quantidadeFaixa06;
	}

	public int getQuantidadeFaixa07() {
		return quantidadeFaixa07;
	}

	public int getQuantidadeFaixa08() {
		return quantidadeFaixa08;
	}

	public int getQuantidadeFaixa09() {
		return quantidadeFaixa09;
	}

	public int getQuantidadeFaixa10() {
		return quantidadeFaixa10;
	}

	public int getQuantidadeFaixa11() {
		return quantidadeFaixa11;
	}

	public int getQuantidadeFaixa12() {
		return quantidadeFaixa12;
	}

	public int getQuantidadeFaixa13() {
		return quantidadeFaixa13;
	}

	public int getQuantidadeFaixa14() {
		return quantidadeFaixa14;
	}

	public int getQuantidadeFaixa15() {
		return quantidadeFaixa15;
	}

	public int getQuantidadeFaixa16() {
		return quantidadeFaixa16;
	}

	public int getQuantidadeFaixa17() {
		return quantidadeFaixa17;
	}

	public int getQuantidadeFaixa18() {
		return quantidadeFaixa18;
	}

	public int getQuantidadeFaixa19() {
		return quantidadeFaixa19;
	}

	public int getQuantidadeFaixa20() {
		return quantidadeFaixa20;
	}

	public int getQuantidadeFaixa21() {
		return quantidadeFaixa21;
	}

	public int getQuantidadeFaixa22() {
		return quantidadeFaixa22;
	}

	public int getQuantidadeFaixa23() {
		return quantidadeFaixa23;
	}

	@Override
	public int compareTo(RelatorioEntradaSaidaPorHorarioDTO a) {
		return this.dataCompetencia.compareTo(a.getDataCompetencia());
	}
	
}
