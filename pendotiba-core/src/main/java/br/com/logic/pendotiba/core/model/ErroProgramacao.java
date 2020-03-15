package br.com.logic.pendotiba.core.model;

import java.io.Serializable;
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
@Table(name = "erro_programacao")
public class ErroProgramacao implements Serializable {

	private static final long serialVersionUID = -4125893423620662393L;
	
	public static final String PROGRAMACAO_SEM_VIAGENS = "Programação sem viagens";

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	Long id;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_competencia")
	Date dataCompetencia;
	
	@Column(name = "descricao")
	String descricao;
	
	@ManyToOne
	@JoinColumn(name = "id_programacao")
	Programacao programacao;
	
	
	public ErroProgramacao() {

	}

	public ErroProgramacao(String descricao, Date dataCompetencia) {
		this.descricao = descricao;
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

	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Programacao getProgramacao() {
		return programacao;
	}
	
	public void setProgramacao(Programacao programacao) {
		this.programacao = programacao;
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
		ErroProgramacao other = (ErroProgramacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
