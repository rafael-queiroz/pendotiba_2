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

@Entity
@Table(name = "ponto_linha")
public class PontoLinha implements Serializable {
	
	static final long serialVersionUID = 4768127562283161539L;
	
	public static final String SENTIDO_IDA = "IDA";
	public static final String SENTIDO_VOLTA = "VOLTA";

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	Long id;
	
	@ManyToOne
	@JoinColumn(name = "id_ponto_origem")
	Ponto pontoOrigem;
	
	@ManyToOne
	@JoinColumn(name = "id_ponto_destino")
	Ponto pontoDestino;
	
	@ManyToOne
	@JoinColumn(name = "id_linha")
	Linha linha;
	
	@JoinColumn(name = "sentido")
	String sentido;
	
	@Column(name = "distancia_produtiva", precision = 5, scale = 2)
	BigDecimal distanciaProdutiva;
	
	@Column(name = "distancia_ociosa", precision = 5, scale = 2)
	BigDecimal distanciaOciosa;
	
	
	
	@Transient
	String distanciaProdutivaStr;
	
	@Transient
	String distanciaOciosaStr;
	
	
	
	
	public PontoLinha() {
		
	}
	
	
	
	//GETTERS E SETTERS
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Ponto getPontoOrigem() {
		return pontoOrigem;
	}
	
	public void setPontoOrigem(Ponto pontoOrigem) {
		this.pontoOrigem = pontoOrigem;
	}
	
	public Ponto getPontoDestino() {
		return pontoDestino;
	}
	
	public void setPontoDestino(Ponto pontoDestino) {
		this.pontoDestino = pontoDestino;
	}

	public Linha getLinha() {
		return linha;
	}

	public void setLinha(Linha linha) {
		this.linha = linha;
	}

	public String getSentido() {
		return sentido;
	}
	
	public void setSentido(String sentido) {
		this.sentido = sentido;
	}
	
	public BigDecimal getDistanciaProdutiva() {
		return distanciaProdutiva;
	}
	
	public void setDistanciaProdutiva(BigDecimal distanciaProdutiva) {
		this.distanciaProdutiva = distanciaProdutiva;
	}
	
	public BigDecimal getDistanciaOciosa() {
		return distanciaOciosa;
	}
	
	public void setDistanciaOciosa(BigDecimal distanciaOciosa) {
		this.distanciaOciosa = distanciaOciosa;
	}
	
	public String getDistanciaOciosaStr() {
		if(distanciaOciosa != null)
			distanciaOciosaStr = distanciaOciosa.toString();
		return distanciaOciosaStr;
	}
	
	public void setDistanciaOciosaStr(String distanciaOciosaStr) {
		this.distanciaOciosaStr = distanciaOciosaStr;
	}
	
	public String getDistanciaProdutivaStr() {
		if(distanciaProdutiva != null)
			distanciaProdutivaStr = distanciaProdutiva.toString();
		return distanciaProdutivaStr;
	}
	
	public void setDistanciaProdutivaStr(String distanciaProdutivaStr) {
		this.distanciaProdutivaStr = distanciaProdutivaStr;
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
		PontoLinha other = (PontoLinha) obj;
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
		return pontoOrigem.getDescricao().toString().concat(" X ").concat(pontoDestino.getDescricao().toString());
	}



	public boolean ehIda() {
		return sentido.equals(SENTIDO_IDA);
	}

}
