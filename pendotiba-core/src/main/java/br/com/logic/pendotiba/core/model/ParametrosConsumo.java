package br.com.logic.pendotiba.core.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.springframework.util.StringUtils;

import br.com.logic.pendotiba.core.util.Constantes;
import br.com.logic.pendotiba.core.util.NumberUtil;



@Entity
@Table(name = "parametros_consumo")
public class ParametrosConsumo implements Serializable {
	
	static final long serialVersionUID = 4269450616996008619L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	Long id;
	
	@NotNull(message = "Tipo de chassi é obrigatório")
	@ManyToOne
	@JoinColumn(name = "id_tipo_chassi")
	TipoChassi tipoChassi;
	
	@NotNull(message = "Linha é obrigatória")
	@ManyToOne
	@JoinColumn(name = "id_linha")
	Linha linha;
	
	@Column(name = "meta_consumo_mes_01", precision = 3, scale = 2)
	BigDecimal metaConsumoMes01;
	
	@Column(name = "meta_consumo_mes_02", precision = 3, scale = 2)
	BigDecimal metaConsumoMes02;
	
	@Column(name = "meta_consumo_mes_03", precision = 3, scale = 2)
	BigDecimal metaConsumoMes03;
	
	@Column(name = "meta_consumo_mes_04", precision = 3, scale = 2)
	BigDecimal metaConsumoMes04;
	
	@Column(name = "meta_consumo_mes_05", precision = 3, scale = 2)
	BigDecimal metaConsumoMes05;
	
	@Column(name = "meta_consumo_mes_06", precision = 3, scale = 2)
	BigDecimal metaConsumoMes06;
	
	@Column(name = "meta_consumo_mes_07", precision = 3, scale = 2)
	BigDecimal metaConsumoMes07;
	
	@Column(name = "meta_consumo_mes_08", precision = 3, scale = 2)
	BigDecimal metaConsumoMes08;
	
	@Column(name = "meta_consumo_mes_09", precision = 3, scale = 2)
	BigDecimal metaConsumoMes09;
	
	@Column(name = "meta_consumo_mes_10", precision = 3, scale = 2)
	BigDecimal metaConsumoMes10;
	
	@Column(name = "meta_consumo_mes_11", precision = 3, scale = 2)
	BigDecimal metaConsumoMes11;
	
	@Column(name = "meta_consumo_mes_12", precision = 3, scale = 2)
	BigDecimal metaConsumoMes12;
	
	
	@Transient
	String metaConsumoMes01Str;
	
	@Transient
	String metaConsumoMes02Str;

	@Transient
	String metaConsumoMes03Str;
	
	@Transient
	String metaConsumoMes04Str;
	
	@Transient
	String metaConsumoMes05Str;
	
	@Transient
	String metaConsumoMes06Str;
	
	@Transient
	String metaConsumoMes07Str;
	
	@Transient
	String metaConsumoMes08Str;
	
	@Transient
	String metaConsumoMes09Str;
	
	@Transient
	String metaConsumoMes10Str;
	
	@Transient
	String metaConsumoMes11Str;
	
	@Transient
	String metaConsumoMes12Str;
	
	
	
	public ParametrosConsumo() {
		
	}


	
	//GETTERS E SETTERS
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public TipoChassi getTipoChassi() {
		return tipoChassi;
	}
	
	public void setTipoChassi(TipoChassi tipoChassi) {
		this.tipoChassi = tipoChassi;
	}
	
	public Linha getLinha() {
		return linha;
	}
	
	public void setLinha(Linha linha) {
		this.linha = linha;
	}
	
	public BigDecimal getMetaConsumoMes01() {
		return metaConsumoMes01;
	}

	public void setMetaConsumoMes01(BigDecimal metaConsumoMes01) {
		this.metaConsumoMes01 = metaConsumoMes01;
	}

	public BigDecimal getMetaConsumoMes02() {
		return metaConsumoMes02;
	}

	public void setMetaConsumoMes02(BigDecimal metaConsumoMes02) {
		this.metaConsumoMes02 = metaConsumoMes02;
	}

	public BigDecimal getMetaConsumoMes03() {
		return metaConsumoMes03;
	}

	public void setMetaConsumoMes03(BigDecimal metaConsumoMes03) {
		this.metaConsumoMes03 = metaConsumoMes03;
	}

	public BigDecimal getMetaConsumoMes04() {
		return metaConsumoMes04;
	}

	public void setMetaConsumoMes04(BigDecimal metaConsumoMes04) {
		this.metaConsumoMes04 = metaConsumoMes04;
	}

	public BigDecimal getMetaConsumoMes05() {
		return metaConsumoMes05;
	}

	public void setMetaConsumoMes05(BigDecimal metaConsumoMes05) {
		this.metaConsumoMes05 = metaConsumoMes05;
	}

	public BigDecimal getMetaConsumoMes06() {
		return metaConsumoMes06;
	}

	public void setMetaConsumoMes06(BigDecimal metaConsumoMes06) {
		this.metaConsumoMes06 = metaConsumoMes06;
	}

	public BigDecimal getMetaConsumoMes07() {
		return metaConsumoMes07;
	}

	public void setMetaConsumoMes07(BigDecimal metaConsumoMes07) {
		this.metaConsumoMes07 = metaConsumoMes07;
	}

	public BigDecimal getMetaConsumoMes08() {
		return metaConsumoMes08;
	}

	public void setMetaConsumoMes08(BigDecimal metaConsumoMes08) {
		this.metaConsumoMes08 = metaConsumoMes08;
	}

	public BigDecimal getMetaConsumoMes09() {
		return metaConsumoMes09;
	}

	public void setMetaConsumoMes09(BigDecimal metaConsumoMes09) {
		this.metaConsumoMes09 = metaConsumoMes09;
	}

	public BigDecimal getMetaConsumoMes10() {
		return metaConsumoMes10;
	}

	public void setMetaConsumoMes10(BigDecimal metaConsumoMes10) {
		this.metaConsumoMes10 = metaConsumoMes10;
	}

	public BigDecimal getMetaConsumoMes11() {
		return metaConsumoMes11;
	}

	public void setMetaConsumoMes11(BigDecimal metaConsumoMes11) {
		this.metaConsumoMes11 = metaConsumoMes11;
	}

	public BigDecimal getMetaConsumoMes12() {
		return metaConsumoMes12;
	}

	public void setMetaConsumoMes12(BigDecimal metaConsumoMes12) {
		this.metaConsumoMes12 = metaConsumoMes12;
	}

	
	
	// TRANSIENTS
	public String getMetaConsumoMes01Str() {
		if(metaConsumoMes01 != null)
			metaConsumoMes01Str = metaConsumoMes01.toString();
		return metaConsumoMes01Str;
	}

	public void setMetaConsumoMes01Str(String metaConsumoMes01Str) {
		this.metaConsumoMes01Str = metaConsumoMes01Str;
	}

	public String getMetaConsumoMes02Str() {
		if(metaConsumoMes02 != null)
			metaConsumoMes02Str = metaConsumoMes02.toString();
		return metaConsumoMes02Str;
	}

	public void setMetaConsumoMes02Str(String metaConsumoMes02Str) {
		this.metaConsumoMes02Str = metaConsumoMes02Str;
	}

	public String getMetaConsumoMes03Str() {
		if(metaConsumoMes03 != null)
			metaConsumoMes03Str = metaConsumoMes03.toString();
		return metaConsumoMes03Str;
	}

	public void setMetaConsumoMes03Str(String metaConsumoMes03Str) {
		this.metaConsumoMes03Str = metaConsumoMes03Str;
	}

	public String getMetaConsumoMes04Str() {
		if(metaConsumoMes04 != null)
			metaConsumoMes04Str = metaConsumoMes04.toString();
		return metaConsumoMes04Str;
	}

	public void setMetaConsumoMes04Str(String metaConsumoMes04Str) {
		this.metaConsumoMes04Str = metaConsumoMes04Str;
	}

	public String getMetaConsumoMes05Str() {
		if(metaConsumoMes05 != null)
			metaConsumoMes05Str = metaConsumoMes05.toString();
		return metaConsumoMes05Str;
	}

	public void setMetaConsumoMes05Str(String metaConsumoMes05Str) {
		this.metaConsumoMes05Str = metaConsumoMes05Str;
	}

	public String getMetaConsumoMes06Str() {
		if(metaConsumoMes06 != null)
			metaConsumoMes06Str = metaConsumoMes06.toString();
		return metaConsumoMes06Str;
	}

	public void setMetaConsumoMes06Str(String metaConsumoMes06Str) {
		this.metaConsumoMes06Str = metaConsumoMes06Str;
	}

	public String getMetaConsumoMes07Str() {	
		if(metaConsumoMes07 != null)
		metaConsumoMes07Str = metaConsumoMes07.toString();
		return metaConsumoMes07Str;
	}

	public void setMetaConsumoMes07Str(String metaConsumoMes07Str) {
		this.metaConsumoMes07Str = metaConsumoMes07Str;
	}

	public String getMetaConsumoMes08Str() {	
		if(metaConsumoMes08 != null)
		metaConsumoMes08Str = metaConsumoMes08.toString();
		return metaConsumoMes08Str;
	}

	public void setMetaConsumoMes08Str(String metaConsumoMes08Str) {
		this.metaConsumoMes08Str = metaConsumoMes08Str;
	}

	public String getMetaConsumoMes09Str() {	
		if(metaConsumoMes09 != null)
		metaConsumoMes09Str = metaConsumoMes09.toString();
		return metaConsumoMes09Str;
	}

	public void setMetaConsumoMes09Str(String metaConsumoMes09Str) {
		this.metaConsumoMes09Str = metaConsumoMes09Str;
	}

	public String getMetaConsumoMes10Str() {	
		if(metaConsumoMes10 != null)
		metaConsumoMes10Str = metaConsumoMes10.toString();
		return metaConsumoMes10Str;
	}

	public void setMetaConsumoMes10Str(String metaConsumoMes10Str) {
		this.metaConsumoMes10Str = metaConsumoMes10Str;
	}

	public String getMetaConsumoMes11Str() {	
		if(metaConsumoMes11 != null)
		metaConsumoMes11Str = metaConsumoMes11.toString();
		return metaConsumoMes11Str;
	}

	public void setMetaConsumoMes11Str(String metaConsumoMes11Str) {
		this.metaConsumoMes11Str = metaConsumoMes11Str;
	}

	public String getMetaConsumoMes12Str() {	
		if(metaConsumoMes12 != null)
		metaConsumoMes12Str = metaConsumoMes12.toString();
		return metaConsumoMes12Str;
	}

	public void setMetaConsumoMes12Str(String metaConsumoMes12Str) {
		this.metaConsumoMes12Str = metaConsumoMes12Str;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParametrosConsumo other = (ParametrosConsumo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	public boolean isNovo(){
		return id == null;
	}
	
	@Override
	public String toString() {
		return this.tipoChassi + " - " + this.linha;
	}
	
	public void atualizarValores() {
		if(!StringUtils.isEmpty(metaConsumoMes01Str))
			this.metaConsumoMes01 = NumberUtil.novoBigDecimal(metaConsumoMes01Str, Constantes.ODOMETRO_CASAS_DECIMAIS);
		
		if(!StringUtils.isEmpty(metaConsumoMes02Str))
			this.metaConsumoMes02 = NumberUtil.novoBigDecimal(metaConsumoMes02Str, Constantes.ODOMETRO_CASAS_DECIMAIS);
		
		if(!StringUtils.isEmpty(metaConsumoMes03Str))
			this.metaConsumoMes03 = NumberUtil.novoBigDecimal(metaConsumoMes03Str, Constantes.ODOMETRO_CASAS_DECIMAIS);
		
		if(!StringUtils.isEmpty(metaConsumoMes04Str))
			this.metaConsumoMes04 = NumberUtil.novoBigDecimal(metaConsumoMes04Str, Constantes.ODOMETRO_CASAS_DECIMAIS);
		
		if(!StringUtils.isEmpty(metaConsumoMes05Str))
			this.metaConsumoMes05 = NumberUtil.novoBigDecimal(metaConsumoMes05Str, Constantes.ODOMETRO_CASAS_DECIMAIS);
		
		if(!StringUtils.isEmpty(metaConsumoMes06Str))
			this.metaConsumoMes06 = NumberUtil.novoBigDecimal(metaConsumoMes06Str, Constantes.ODOMETRO_CASAS_DECIMAIS);
		
		if(!StringUtils.isEmpty(metaConsumoMes07Str))
			this.metaConsumoMes07 = NumberUtil.novoBigDecimal(metaConsumoMes07Str, Constantes.ODOMETRO_CASAS_DECIMAIS);
		
		if(!StringUtils.isEmpty(metaConsumoMes08Str))
			this.metaConsumoMes08 = NumberUtil.novoBigDecimal(metaConsumoMes08Str, Constantes.ODOMETRO_CASAS_DECIMAIS);
		
		if(!StringUtils.isEmpty(metaConsumoMes09Str))
			this.metaConsumoMes09 = NumberUtil.novoBigDecimal(metaConsumoMes09Str, Constantes.ODOMETRO_CASAS_DECIMAIS);
		
		if(!StringUtils.isEmpty(metaConsumoMes10Str))
			this.metaConsumoMes10 = NumberUtil.novoBigDecimal(metaConsumoMes10Str, Constantes.ODOMETRO_CASAS_DECIMAIS);
		
		if(!StringUtils.isEmpty(metaConsumoMes11Str))
			this.metaConsumoMes11 = NumberUtil.novoBigDecimal(metaConsumoMes11Str, Constantes.ODOMETRO_CASAS_DECIMAIS);
		
		if(!StringUtils.isEmpty(metaConsumoMes12Str))
			this.metaConsumoMes12 = NumberUtil.novoBigDecimal(metaConsumoMes12Str, Constantes.ODOMETRO_CASAS_DECIMAIS);
	}



	public BigDecimal getMetaConsumoDieselPorMes(int mes) {
		switch (mes) {
        case 0:
            return metaConsumoMes01; 
        case 1:
            return metaConsumoMes02;
        case 2:
            return metaConsumoMes03; 
        case 3:
            return metaConsumoMes04; 
        case 4:
            return metaConsumoMes05; 
        case 5:
            return metaConsumoMes06; 
        case 6:
            return metaConsumoMes07; 
        case 7:
            return metaConsumoMes08; 
        case 8:
            return metaConsumoMes09; 
        case 9:
            return metaConsumoMes10; 
        case 10:
            return metaConsumoMes11; 
        case 11:
            return metaConsumoMes12;
		}
		
		return null;
	}
	
}
