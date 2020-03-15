package br.com.logic.pendotiba.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "turno")
public class Turno implements Serializable {
	
	static final long serialVersionUID = -3390256052790536263L;
	
	public static final Long MANHA = 1L;
	public static final Long TARDE = 2L;
	public static final Long NOITE = 3L;
	public static final Long PRIMERA_PEGADA_TU = 4L;
	public static final Long SEGUNDA_PEGADA_TU = 5L;
	
	public static final String PRIMEIRO_TURNO = "1º Turno";
	public static final String SEGUNDO_TURNO = "2º Turno";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	Long id;
	
	@Column(name = "descricao")
	String descricao;
	
	@Column(name = "agrupamento")
	String agrupamento;
	
	
	
	public Turno() {
		
	}



	//GETTERS E SETTERS
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getAgrupamento() {
		return agrupamento;
	}
	
	public void setAgrupamento(String agrupamento) {
		this.agrupamento = agrupamento;
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
		Turno other = (Turno) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return descricao;
	}

}
