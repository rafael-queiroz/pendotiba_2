package br.com.logic.pendotiba.core.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import br.com.logic.pendotiba.core.enums.LocalDeAtuacaoEnum;

@Entity
@Table(name = "carro")
public class Carro implements Serializable {
	
	static final long serialVersionUID = -6441883549582384127L;

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	Long id;
	
	@NotBlank(message = "Número de ordem é obrigatório")
	@Column(name = "numero_de_ordem")
	String numeroDeOrdem;
	
	@Column(name = "placa")
	String placa;
	
	@Column(name = "renavam")
	String renavam;
	
	@Column(name = "chassi")
	String chassi;
	
	@Column(name = "ano")
	Long ano;
	
	@Column(name = "carroceria")
	String carroceria;
	
	@Column(name = "quantidade_roletas")
	Long quantidadeRoletas = 1L;
	
	@NotNull(message = "Roleta 1 é obrigatório")
	@Column(name = "roleta_1", precision = 5, scale = 0)
	BigInteger roleta1 = BigInteger.ZERO;
	
	@Column(name = "odometro", precision = 6, scale = 0)
	BigInteger odometro  = BigInteger.ZERO;
	
	@ManyToOne
	@JoinColumn(name = "id_tipo_carro")
	TipoCarro tipoCarro;
	
	@ManyToOne
	@JoinColumn(name = "id_tipo_chassi")
	TipoChassi tipoChassi;
	
	@Column(name = "ativo")
	Boolean ativo = true;
	
	@Column(name = "exporta_transoft")
	Boolean exportaTransoft = true; 

	@Column(name = "ultimo_abastecimento_diesel", precision = 7, scale = 1)
	BigDecimal ultimoAbastecimentoDiesel = BigDecimal.ZERO;
	
	@Column(name = "ultimo_abastecimento_arla", precision = 7, scale = 1)
	BigDecimal ultimoAbastecimentoArla = BigDecimal.ZERO;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "local_de_atuacao")
	LocalDeAtuacaoEnum localDeAtuacao = LocalDeAtuacaoEnum.RIO_DE_JANEIRO;
	
	
	
	@Transient
	String ultimoAbastecimentoDieselStr;
	
	@Transient
	String ultimoAbastecimentoArlaStr;
	
	@Transient
	String roletaStr;
	
	@Transient
	String odometroStr;
	
	
	
	public Carro() {

	}

	public Carro(String numeroDeOrdem, BigInteger roleta1, Long quantidadeRoletas) {
		this.numeroDeOrdem = numeroDeOrdem;
		this.roleta1 = roleta1;
		this.quantidadeRoletas = quantidadeRoletas;
	}


	
	//GETTERS E SETTERS
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroDeOrdem() {
		return numeroDeOrdem;
	}

	public void setNumeroDeOrdem(String numeroDeOrdem) {
		this.numeroDeOrdem = numeroDeOrdem;
	}
	
	public String getPlaca() {
		return placa;
	}
	
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	
	public String getRenavam() {
		return renavam;
	}
	
	public void setRenavam(String renavam) {
		this.renavam = renavam;
	}
	
	public String getChassi() {
		return chassi;
	}
	
	public void setChassi(String chassi) {
		this.chassi = chassi;
	}
	
	public Long getAno() {
		return ano;
	}
	
	public void setAno(Long ano) {
		this.ano = ano;
	}
	
	public String getCarroceria() {
		return carroceria;
	}
	
	public void setCarroceria(String carroceria) {
		this.carroceria = carroceria;
	}
	
	public Long getQuantidadeRoletas() {
		return quantidadeRoletas;
	}
	
	public void setQuantidadeRoletas(Long quantidadeRoletas) {
		this.quantidadeRoletas = quantidadeRoletas;
	}
	
	public BigInteger getRoleta1() {
		return roleta1;
	}
	
	public void setRoleta1(BigInteger roleta1) {
		this.roleta1 = roleta1;
	}
	
	public BigInteger getOdometro() {
		return odometro;
	}
	
	public void setOdometro(BigInteger odometro) {
		this.odometro = odometro;
	}

	public TipoCarro getTipoCarro() {
		return tipoCarro;
	}

	public void setTipoCarro(TipoCarro tipoCarro) {
		this.tipoCarro = tipoCarro;
	}

	public Boolean getAtivo() {
		return ativo;
	}
	
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	
	public Boolean getExportaTransoft() {
		return exportaTransoft;
	}
	
	public void setExportaTransoft(Boolean exportaTransoft) {
		this.exportaTransoft = exportaTransoft;
	}
	
	public BigDecimal getUltimoAbastecimentoDiesel() {
		return ultimoAbastecimentoDiesel;
	}
	
	public void setUltimoAbastecimentoDiesel(BigDecimal ultimoAbastecimentoDiesel) {
		this.ultimoAbastecimentoDiesel = ultimoAbastecimentoDiesel;
	}
	
	public BigDecimal getUltimoAbastecimentoArla() {
		return ultimoAbastecimentoArla;
	}
	
	public void setUltimoAbastecimentoArla(BigDecimal ultimoAbastecimentoArla) {
		this.ultimoAbastecimentoArla = ultimoAbastecimentoArla;
	}
	
	public TipoChassi getTipoChassi() {
		return tipoChassi;
	}
	
	public void setTipoChassi(TipoChassi tipoChassi) {
		this.tipoChassi = tipoChassi;
	}
	
	public LocalDeAtuacaoEnum getLocalDeAtuacao() {
		return localDeAtuacao;
	}
	
	public void setLocalDeAtuacao(LocalDeAtuacaoEnum localDeAtuacao) {
		this.localDeAtuacao = localDeAtuacao;
	}
	
	
	
	// TRANSIENTS
	public String getUltimoAbastecimentoDieselStr() {
		if(ultimoAbastecimentoDiesel != null)
			ultimoAbastecimentoDieselStr = ultimoAbastecimentoDiesel.toString();
		return ultimoAbastecimentoDieselStr;
	}
	
	public void setUltimoAbastecimentoDieselStr(String ultimoAbastecimentoDieselStr) {
		this.ultimoAbastecimentoDieselStr = ultimoAbastecimentoDieselStr;
	}
	
	public String getUltimoAbastecimentoArlaStr() {
		if(ultimoAbastecimentoArla != null)
			ultimoAbastecimentoArlaStr = ultimoAbastecimentoArla.toString();
		return ultimoAbastecimentoArlaStr;
	}
	
	public void setUltimoAbastecimentoArlaStr(String ultimoAbastecimentoArlaStr) {
		this.ultimoAbastecimentoArlaStr = ultimoAbastecimentoArlaStr;
	}
	
	public String getRoletaStr() {
		if(roleta1 != null)
			roletaStr = String.format(new Locale("pt"), "%,d", roleta1);
		return roletaStr;
	}
	
	public void setRoletaStr(String roletaStr) {
		this.roletaStr = roletaStr;
	}
	
	public String getOdometroStr() {
		if(odometro != null)
			odometroStr = String.format(new Locale("pt"), "%,d", odometro);
		return odometroStr;
	}
	
	public void setOdometroStr(String odometroStr) {
		this.odometroStr = odometroStr;
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
		Carro other = (Carro) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	@Override
	public String toString() {
		return numeroDeOrdem;
	}
	
	
	public boolean isNovo(){
		return id == null;
	}
	
}
