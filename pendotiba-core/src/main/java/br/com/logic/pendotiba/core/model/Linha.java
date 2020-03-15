package br.com.logic.pendotiba.core.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "linha")
public class Linha implements Serializable {
	
	static final long serialVersionUID = -6522916143592977334L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	Long id;
	
	@NotBlank(message = "Código é obrigatório")
	@Column(name = "codigo")
	String codigo;
	
	@NotBlank(message = "Descrição é obrigatório")
	@Column(name = "descricao")
	String descricao;
	
	@Column(name = "tempo_viagem")
	String tempoViagem;
	
	@Column(name = "distancia_viagem", precision = 19, scale = 0)
	BigInteger distanciaViagem;
	
	@Column(name = "tarifa", precision = 19, scale = 2)
	BigInteger tarifa;
	
	@Column(name = "operacional")
	Boolean operacional = true; 
	
	@OneToMany(mappedBy = "linha")
	List<PontoLinha> pontosLinhas;
	
	

	
	public Linha() {
		
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

	public String getTempoViagem() {
		return tempoViagem;
	}
	
	public void setTempoViagem(String tempoViagem) {
		this.tempoViagem = tempoViagem;
	}

	public BigInteger getDistanciaViagem() {
		return distanciaViagem;
	}

	public void setDistanciaViagem(BigInteger distanciaViagem) {
		this.distanciaViagem = distanciaViagem;
	}

	public BigInteger getTarifa() {
		return tarifa;
	}

	public void setTarifa(BigInteger tarifa) {
		this.tarifa = tarifa;
	}
	
	public Boolean getOperacional() {
		return operacional;
	}
	
	public void setOperacional(Boolean operacional) {
		this.operacional = operacional;
	}
	
	public List<PontoLinha> getPontosLinhas() {
		return pontosLinhas;
	}
	
	public void setPontosLinhas(List<PontoLinha> pontosLinhas) {
		this.pontosLinhas = pontosLinhas;
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
		Linha other = (Linha) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	public boolean isNova(){
		return id == null;
	}
	
	
	@Override
	public String toString() {
		return codigo.concat(" - ").concat(descricao);
	}
	
}
