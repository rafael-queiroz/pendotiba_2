package br.com.logic.pendotiba.core.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "viagem_importada")
public class ViagemImportada implements Serializable {
	
	static final long serialVersionUID = -967393187624399970L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	Long id;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_competencia")
	Date dataCompetencia;
	
	@Temporal(TemporalType.TIME)
	@Column(name = "hora")
	Date hora;
	
	@Column(name = "linha")
	String linha;
	
	@Column(name = "sentido")
	String sentido;
	
	@Column(name = "ordem", precision = 2, scale = 0)
	BigInteger ordem;
	
	@ManyToOne
	@JoinColumn(name = "id_programacao_importada")
	ProgramacaoImportada programacaoImportada;
	

	
	public ViagemImportada() {
		
	}

	public ViagemImportada(Date hora, String linha, String sentido, BigInteger ordem, Date dataCompetencia) {
		this.hora = hora;
		this.linha = linha;
		this.sentido = sentido;
		this.ordem = ordem;
		this.dataCompetencia = dataCompetencia; 
	}




	//GETTERS E SETTERS
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Date getDataCompetencia() {
		return dataCompetencia;
	}
	
	public void setDataCompetencia(Date dataCompetencia) {
		this.dataCompetencia = dataCompetencia;
	}

	public Date getHora() {
		return hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public String getLinha() {
		return linha;
	}

	public void setLinha(String linha) {
		this.linha = linha;
	}

	public String getSentido() {
		return sentido;
	}

	public void setSentido(String sentido) {
		this.sentido = sentido;
	}

	public BigInteger getOrdem() {
		return ordem;
	}

	public void setOrdem(BigInteger ordem) {
		this.ordem = ordem;
	}

	public ProgramacaoImportada getProgramacaoImportada() {
		return programacaoImportada;
	}

	public void setProgramacaoImportada(ProgramacaoImportada programacaoImportada) {
		this.programacaoImportada = programacaoImportada;
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
		ViagemImportada other = (ViagemImportada) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
