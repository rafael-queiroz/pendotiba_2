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
@Table(name = "programacao_importada")
public class ProgramacaoImportada implements Serializable {
	
	static final long serialVersionUID = 6802971306679713072L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	Long id;
	
	@Column(name = "versao")
	String versao;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_competencia")
	Date dataCompetencia;
	
	@Column(name = "ordem_programacao")
	String ordemProgramacao;

	@ManyToOne
	@JoinColumn(name = "id_linha")
	Linha linha;
	
	@Column(name = "sentido")
	String sentido;
	
	@Temporal(TemporalType.TIME)
	@Column(name = "saida")
	Date saida;
	
	@Temporal(TemporalType.TIME)
	@Column(name = "chegada")
	Date chegada;
	
	@ManyToOne
	@JoinColumn(name = "id_ponto_origem")
	Ponto pontoOrigem;
	
	@ManyToOne
	@JoinColumn(name = "id_ponto_destino")
	Ponto pontoDestino;
	
	@Column(name = "movimento")
	String movimento;
	
	@Column(name = "ordem_viagem")
	Long ordemViagem;
	
	
	
	public ProgramacaoImportada() {
		
	}

	
	public ProgramacaoImportada(String versao, Date dataCompetencia, Linha linha, String sentido,
			Date saida, Date chegada, Ponto pontoOrigem, Ponto pontoDestino, String movimento, String ordemProgramacao) {
		super();
		this.versao = versao;
		this.dataCompetencia = dataCompetencia;
		this.linha = linha;
		this.sentido = sentido;
		this.saida = saida;
		this.chegada = chegada;
		this.pontoOrigem = pontoOrigem;
		this.pontoDestino = pontoDestino;
		this.movimento = movimento;
		this.ordemProgramacao = ordemProgramacao;
	}



	//GETTERS E SETTERS
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVersao() {
		return versao;
	}

	public void setVersao(String versao) {
		this.versao = versao;
	}

	public Date getDataCompetencia() {
		return dataCompetencia;
	}

	public void setDataCompetencia(Date dataCompetencia) {
		this.dataCompetencia = dataCompetencia;
	}

	public String getOrdemProgramacao() {
		return ordemProgramacao;
	}
	
	public void setOrdemProgramacao(String ordemProgramacao) {
		this.ordemProgramacao = ordemProgramacao;
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

	public Date getSaida() {
		return saida;
	}

	public void setSaida(Date saida) {
		this.saida = saida;
	}

	public Date getChegada() {
		return chegada;
	}

	public void setChegada(Date chegada) {
		this.chegada = chegada;
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

	public String getMovimento() {
		return movimento;
	}

	public void setMovimento(String movimento) {
		this.movimento = movimento;
	}
	
	public Long getOrdemViagem() {
		return ordemViagem;
	}
	
	public void setOrdemViagem(Long ordemViagem) {
		this.ordemViagem = ordemViagem;
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
		ProgramacaoImportada other = (ProgramacaoImportada) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
