package br.com.logic.pendotiba.core.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "carro_programacao")
public class CarroProgramacao implements Serializable {
	
	private static final long serialVersionUID = -2502113234482597301L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	Long id;
	
	//@NotNull(message = "O programação é obrigatório")
	@ManyToOne
	@JoinColumn(name = "id_programacao")
	Programacao programacao;
	
	@ManyToOne
	@JoinColumn(name = "id_motivo_troca_carro")
	MotivoTrocaCarro motivoTrocaCarro;
	
	@Column(name = "observacao")
	String observacao;
	
	//@NotNull(message = "Carro é obrigatório")
	@ManyToOne
	@JoinColumn(name = "id_carro")
	Carro carro;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	Usuario usuario;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Column(name = "data_competencia")
	Date dataCompetencia;
	
	@Column(name = "roleta_inicial_1", precision = 5, scale = 0)
	BigInteger roletaInicial1;
	
	@Column(name = "roleta_final_1", precision = 5, scale = 0)
	BigInteger roletaFinal1;
	/*
	@Column(name = "roleta_inicial_2", precision = 5, scale = 0)
	BigInteger roletaInicial2;
	
	@Column(name = "roleta_inicial_3", precision = 5, scale = 0)
	BigInteger roletaInicial3;
	
	@Column(name = "roleta_final_2", precision = 5, scale = 0)
	BigInteger roletaFinal2;
	
	@Column(name = "roleta_final_3", precision = 5, scale = 0)
	BigInteger roletaFinal3;
	 */
	
	@Transient
	String roletaInicial1Str;
	
	@Transient
	String roletaFinal1Str;
	
	

	public CarroProgramacao() {
		
	}
	
	public CarroProgramacao(Programacao programacao, Usuario usuario) {
		this.programacao = programacao;
		this.carro = programacao.getCarroRealizado();
		this.dataCompetencia = new Date();
		this.roletaInicial1 = programacao.getCarroRealizado().getRoleta1();
		this.usuario = usuario;
	}
	
	public CarroProgramacao(Programacao programacao, Carro carro, Date dataCompetencia, BigInteger roletaInicial1, Usuario usuario) {
		this.programacao = programacao;
		this.carro = carro;
		this.dataCompetencia = dataCompetencia;
		this.roletaInicial1 = roletaInicial1;
		this.usuario = usuario;
	}



	public CarroProgramacao(Carro carro, Date dataCompetencia, BigInteger roletaInicial1, BigInteger roleta1, Usuario usuario) {
		this.carro = carro;
		this.dataCompetencia = dataCompetencia;
		this.roletaInicial1 = roletaInicial1;
		this.roletaFinal1 = roleta1;
		this.usuario = usuario;
	}
	
	

	//GETTERS E SETTERS
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Programacao getProgramacao() {
		return programacao;
	}

	public void setProgramacao(Programacao programacao) {
		this.programacao = programacao;
	}

	public Carro getCarro() {
		return carro;
	}

	public void setCarro(Carro carro) {
		this.carro = carro;
	}

	public Date getDataCompetencia() {
		return dataCompetencia;
	}
	
	public void setDataCompetencia(Date dataCompetencia) {
		this.dataCompetencia = dataCompetencia;
	}
	
	public BigInteger getRoletaInicial1() {
		return roletaInicial1;
	}

	public void setRoletaInicial1(BigInteger roletaInicial1) {
		this.roletaInicial1 = roletaInicial1;
	}

	public BigInteger getRoletaFinal1() {
		return roletaFinal1;
	}

	public void setRoletaFinal1(BigInteger roletaFinal1) {
		this.roletaFinal1 = roletaFinal1;
	}

	public MotivoTrocaCarro getMotivoTrocaCarro() {
		return motivoTrocaCarro;
	}
	
	public void setMotivoTrocaCarro(MotivoTrocaCarro motivoTrocaCarro) {
		this.motivoTrocaCarro = motivoTrocaCarro;
	}
	
	public String getObservacao() {
		return observacao;
	}
	
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	// TRANSIENT
	public String getRoletaInicial1Str() {
		if(roletaInicial1 != null)
			roletaInicial1Str = String.format(new Locale("pt"), "%,d", roletaInicial1);
		return roletaInicial1Str;
	}
	
	public void setRoletaInicial1Str(String roletaInicial1Str) {
		this.roletaInicial1Str = roletaInicial1Str;
	}
	
	public String getRoletaFinal1Str() {
		if(roletaFinal1 != null)
			roletaFinal1Str = String.format(new Locale("pt"), "%,d", roletaFinal1);
		return roletaFinal1Str;
	}
	
	public void setRoletaFinal1Str(String roletaFinal1Str) {
		this.roletaFinal1Str = roletaFinal1Str;
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
		CarroProgramacao other = (CarroProgramacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
