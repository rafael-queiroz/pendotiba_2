package br.com.logic.pendotiba.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "funcao")
public class Funcao implements Serializable {
	
	private static final long serialVersionUID = -7726995334769392209L;

	@Id
	@Column(name = "id")
	Long id;
	
	@Column(name = "codigo")
	String codigo;
	
	@Column(name = "descricao")
	String descricao;

	@Column(name = "motorista")
	Boolean motorista;
	
	@Column(name = "despachante")
	Boolean despachante;
	
	@Column(name = "tecnologia")
	Boolean tecnologia;
	
	@Column(name = "cobrador")
	Boolean cobrador;
	
	
	
	public Funcao() {

	}



	//GETTERS E SETTERS
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCodigo() {
		return codigo;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Boolean getMotorista() {
		return motorista;
	}

	public void setMotorista(Boolean motorista) {
		this.motorista = motorista;
	}
	
	public Boolean getDespachante() {
		return despachante;
	}
	
	public void setDespachante(Boolean despachante) {
		this.despachante = despachante;
	}

	public Boolean getTecnologia() {
		return tecnologia;
	}
	
	public void setTecnologia(Boolean tecnologia) {
		this.tecnologia = tecnologia;
	}

	public Boolean getCobrador() {
		return cobrador;
	}
	
	public void setCobrador(Boolean cobrador) {
		this.cobrador = cobrador;
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
		Funcao other = (Funcao) obj;
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



	public void identificarTipoFuncao() {
		if(descricao.startsWith("MOT")) {
			motorista = true;
			despachante = false;
			tecnologia = false;
		} else if(descricao.startsWith("DES")) {
			motorista = false;
			despachante = true;
			tecnologia = false;
		} else {
			motorista = false;
			despachante = false;
			tecnologia = true;
		}
	}
	
}
