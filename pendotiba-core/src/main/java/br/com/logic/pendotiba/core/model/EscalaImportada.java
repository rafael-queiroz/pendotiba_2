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
@Table(name = "escala_importada")
public class EscalaImportada implements Serializable {
	
	static final long serialVersionUID = 4274705363075320535L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	Long id;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "data_competencia")
	Date dataCompetencia;
	
	@Temporal(TemporalType.TIME)
	@Column(name = "inicio_jornada")
	Date incioJornada;
	
	@Temporal(TemporalType.TIME)
	@Column(name = "saida")
	Date saida;
	
	@Column(name = "cracha_motorista")
	String crachaMotorista;
	
	@Column(name = "nome_motorista")
	String nomeMotorista;
	
	@Column(name = "escala_motorista")
	String escalaMotorista;
	
	@Column(name = "cracha_parceiro")
	String crachaParceiro;
	
	@Column(name = "nome_parceiro")
	String nomeParceiro;
	
	@Column(name = "escala_parceiro")
	String escalaParceiro;
	
	@Column(name = "ordem_veiculo")
	String ordemVeiculo;
	
	@Column(name = "ordem_programacao")
	String ordemProgramacao;

	@Column(name = "ordem_viagem")
	Long ordemViagem;
	
	@ManyToOne
	@JoinColumn(name = "id_linha")
	Linha linha;
	
	@ManyToOne
	@JoinColumn(name = "id_motorista")
	Funcionario motorista;
	
	@ManyToOne
	@JoinColumn(name = "id_parceiro")
	Funcionario parceiro;
	
	@ManyToOne
	@JoinColumn(name = "id_carro")
	Carro carro;
	
	@ManyToOne
	@JoinColumn(name = "id_turno")
	Turno turno;
	
	@ManyToOne
	@JoinColumn(name = "id_arquivo_importado")
	ArquivoImportado arquivoImportado;
	
	
	
	
	public EscalaImportada() {
		
	}

	public EscalaImportada(Date dataCompetencia, Date inicioJornada, Date saida, String crachaMotorista, String nomeMotorista, 
			String crachaPerceiro, String nomeParceiro, String ordemVeiculo, Linha linha,
			Funcionario motorista, Funcionario parceiro, Carro carro, ArquivoImportado arquivoImportado) {
		this.dataCompetencia = dataCompetencia;
		this.incioJornada = inicioJornada;
		this.saida = saida;
		this.crachaMotorista = crachaMotorista;
		this.nomeMotorista = nomeMotorista;
		//this.escalaMotorista = escalaMotorista;
		this.crachaParceiro = crachaPerceiro;
		this.nomeParceiro = nomeParceiro;
		//this.escalaParceiro = escalaParceiro;
		this.ordemVeiculo = ordemVeiculo;
		this.linha = linha;
		this.motorista = motorista;
		this.parceiro = parceiro;
		this.carro = carro;
		this.arquivoImportado = arquivoImportado;
	}



	public EscalaImportada(Date dataCompetencia, Date saida, String crachaMotorista, String nomeMotorista,
			String escalaMotorista, String crachaPerceiro, String nomeParceiro, String escalaParceiro,
			String ordemVeiculo, Linha linha, ArquivoImportado arquivoImportado) {
		this.dataCompetencia = dataCompetencia;
		this.saida = saida;
		this.crachaMotorista = crachaMotorista;
		this.nomeMotorista = nomeMotorista;
		this.escalaMotorista = escalaMotorista;
		this.crachaParceiro = crachaPerceiro;
		this.nomeParceiro = nomeParceiro;
		this.escalaParceiro = escalaParceiro;
		this.ordemVeiculo = ordemVeiculo;
		this.linha = linha;
		this.arquivoImportado = arquivoImportado;
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
	
	public Date getIncioJornada() {
		return incioJornada;
	}
	
	public void setIncioJornada(Date incioJornada) {
		this.incioJornada = incioJornada;
	}
	
	public Date getSaida() {
		return saida;
	}

	public void setSaida(Date saida) {
		this.saida = saida;
	}

	public String getCrachaMotorista() {
		return crachaMotorista;
	}

	public void setCrachaMotorista(String crachaMotorista) {
		this.crachaMotorista = crachaMotorista;
	}

	public String getNomeMotorista() {
		return nomeMotorista;
	}

	public void setNomeMotorista(String nomeMotorista) {
		this.nomeMotorista = nomeMotorista;
	}

	public String getEscalaMotorista() {
		return escalaMotorista;
	}

	public void setEscalaMotorista(String escalaMotorista) {
		this.escalaMotorista = escalaMotorista;
	}

	public String getCrachaParceiro() {
		return crachaParceiro;
	}

	public void setCrachaParceiro(String crachaParceiro) {
		this.crachaParceiro = crachaParceiro;
	}

	public String getNomeParceiro() {
		return nomeParceiro;
	}

	public void setNomeParceiro(String nomeParceiro) {
		this.nomeParceiro = nomeParceiro;
	}

	public String getEscalaParceiro() {
		return escalaParceiro;
	}

	public void setEscalaParceiro(String escalaParceiro) {
		this.escalaParceiro = escalaParceiro;
	}

	public String getOrdemVeiculo() {
		return ordemVeiculo;
	}

	public void setOrdemVeiculo(String ordemVeiculo) {
		this.ordemVeiculo = ordemVeiculo;
	}
	
	public String getOrdemProgramacao() {
		return ordemProgramacao;
	}
	
	public void setOrdemProgramacao(String ordemProgramacao) {
		this.ordemProgramacao = ordemProgramacao;
	}
	
	public Long getOrdemViagem() {
		return ordemViagem;
	}
	
	public void setOrdemViagem(Long ordemViagem) {
		this.ordemViagem = ordemViagem;
	}
	
	public Linha getLinha() {
		return linha;
	}

	public void setLinha(Linha linha) {
		this.linha = linha;
	}

	public Funcionario getMotorista() {
		return motorista;
	}

	public void setMotorista(Funcionario motorista) {
		this.motorista = motorista;
	}

	public Funcionario getParceiro() {
		return parceiro;
	}

	public void setParceiro(Funcionario parceiro) {
		this.parceiro = parceiro;
	}
	
	public Carro getCarro() {
		return carro;
	}
	
	public void setCarro(Carro carro) {
		this.carro = carro;
	}
	
	public Turno getTurno() {
		return turno;
	}
	
	public void setTurno(Turno turno) {
		this.turno = turno;
	}
	
	public ArquivoImportado getArquivoImportado() {
		return arquivoImportado;
	}
	
	public void setArquivoImportado(ArquivoImportado arquivoImportado) {
		this.arquivoImportado = arquivoImportado;
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
		EscalaImportada other = (EscalaImportada) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
