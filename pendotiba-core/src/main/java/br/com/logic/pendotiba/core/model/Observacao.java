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

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "observacao")
public class Observacao implements Serializable {
	
	static final long serialVersionUID = 5571882465533237774L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	Long id;
	
	@ManyToOne
	@JoinColumn(name = "id_programacao")
	Programacao programacao;
	
	@ManyToOne
	@JoinColumn(name = "id_tipo_observacao")
	TipoObservacao tipoObservacao;
	
	@Column(name = "descricao")
	String descricao;
	
	@ManyToOne
	@JoinColumn(name = "id_carro")
	Carro carro;
	
	@ManyToOne
	@JoinColumn(name = "id_linha")
	Linha linha;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	Usuario usuario;
	
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	@Column(name = "hora")
	Date hora;
	

	
	public Observacao() {
		
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

	public TipoObservacao getTipoObservacao() {
		return tipoObservacao;
	}

	public void setTipoObservacao(TipoObservacao tipoObservacao) {
		this.tipoObservacao = tipoObservacao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getHora() {
		return hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
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
		Observacao other = (Observacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
