package br.com.logic.pendotiba.core.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name = "entrada_saida_carro_garagem")
public class EntradaSaidaDeCarroDaGaragem implements Serializable {
	
	static final long serialVersionUID = -2395898088947249453L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	Long id;
	
	@Temporal(TemporalType.DATE)
	@NotNull(message = "Data de competência é obrigatório")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "data_competencia")
	Date dataCompetencia = new Date();
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Column(name = "data_hora_cadastro")
	Date dataHoraCadastro;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Column(name = "data_hora_entrada")
	Date dataHoraEntrada;
	
	@Column(name = "qtd_viagem_programada_ida", precision = 3, scale = 1)
	BigDecimal qtdViagemProgramadaIda;
	
	@Column(name = "qtd_viagem_programada_volta", precision = 3, scale = 1)
	BigDecimal qtdViagemProgramadaVolta;

	@Column(name = "qtd_viagem_realizada_ida", precision = 3, scale = 1)
	BigDecimal qtdViagemRealizadaIda;
	
	@Column(name = "qtd_viagem_realizada_volta", precision = 3, scale = 1)
	BigDecimal qtdViagemRealizadaVolta;
	
	@Column(name = "qtd_passageiro", precision = 3, scale = 0)
	BigInteger qtdPassageiro;
	
	@NotNull(message = "Linha é obrigatória")
	@ManyToOne
	@JoinColumn(name = "id_linha")
	Linha linha;

	@NotNull(message = "Carro é obrigatório")
	@ManyToOne
	@JoinColumn(name = "id_carro")
	Carro carro;
	
	@ManyToOne
	@JoinColumn(name = "id_funcionario")
	Funcionario funcionario;
	
	@NotNull(message = "Turno é obrigatório")
	@ManyToOne
	@JoinColumn(name = "id_turno")
	Turno turno;
	
	@Column(name = "distancia_produtiva", precision = 5, scale = 2)
	BigDecimal distanciaProdutiva = BigDecimal.ZERO;
	
	@Column(name = "distancia_ociosa", precision = 5, scale = 2)
	BigDecimal distanciaOciosa = BigDecimal.ZERO;
	
	
	@PrePersist
	void prePestist() {
		calcularDistanciaPercorrida();
		setDataHoraCadastro(new Date());
		atualizarDataEntrada();
	}
	
	@PreUpdate
	void PreUpdate() {
		calcularDistanciaPercorrida();
		atualizarDataEntrada();
	}
	


	public EntradaSaidaDeCarroDaGaragem() {
		
	}
	
	

	//GETTERS E SETTERS
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
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
	
	public Date getDataHoraCadastro() {
		return dataHoraCadastro;
	}
	
	public void setDataHoraCadastro(Date dataHoraCadastro) {
		this.dataHoraCadastro = dataHoraCadastro;
	}
	
	public Date getDataHoraEntrada() {
		return dataHoraEntrada;
	}
	
	public void setDataHoraEntrada(Date dataHoraEntrada) {
		this.dataHoraEntrada = dataHoraEntrada;
	}
	
	public Linha getLinha() {
		return linha;
	}

	public void setLinha(Linha linha) {
		this.linha = linha;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}
	
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	
	public BigInteger getQtdPassageiro() {
		return qtdPassageiro;
	}
	
	public void setQtdPassageiro(BigInteger qtdPassageiro) {
		this.qtdPassageiro = qtdPassageiro;
	}
	
	public BigDecimal getQtdViagemProgramadaIda() {
		return qtdViagemProgramadaIda;
	}

	public void setQtdViagemProgramadaIda(BigDecimal qtdViagemProgramadaIda) {
		this.qtdViagemProgramadaIda = qtdViagemProgramadaIda;
	}

	public BigDecimal getQtdViagemProgramadaVolta() {
		return qtdViagemProgramadaVolta;
	}

	public void setQtdViagemProgramadaVolta(BigDecimal qtdViagemProgramadaVolta) {
		this.qtdViagemProgramadaVolta = qtdViagemProgramadaVolta;
	}

	public BigDecimal getQtdViagemRealizadaIda() {
		return qtdViagemRealizadaIda;
	}

	public void setQtdViagemRealizadaIda(BigDecimal qtdViagemRealizadaIda) {
		this.qtdViagemRealizadaIda = qtdViagemRealizadaIda;
	}

	public BigDecimal getQtdViagemRealizadaVolta() {
		return qtdViagemRealizadaVolta;
	}

	public void setQtdViagemRealizadaVolta(BigDecimal qtdViagemRealizadaVolta) {
		this.qtdViagemRealizadaVolta = qtdViagemRealizadaVolta;
	}

	public Turno getTurno() {
		return turno;
	}
	
	public void setTurno(Turno turno) {
		this.turno = turno;
	}
	
	public BigDecimal getDistanciaOciosa() {
		return distanciaOciosa;
	}
	
	public void setDistanciaOciosa(BigDecimal distanciaOciosa) {
		this.distanciaOciosa = distanciaOciosa;
	}
	
	public BigDecimal getDistanciaProdutiva() {
		return distanciaProdutiva;
	}
	
	public void setDistanciaProdutiva(BigDecimal distanciaProdutiva) {
		this.distanciaProdutiva = distanciaProdutiva;
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
		EntradaSaidaDeCarroDaGaragem other = (EntradaSaidaDeCarroDaGaragem) obj;
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



	public static void ordenarPorLinha(List<EntradaSaidaDeCarroDaGaragem> saidas) {
		 Collections.sort(saidas, new Comparator<EntradaSaidaDeCarroDaGaragem>() {  
            @Override  
            public int compare(EntradaSaidaDeCarroDaGaragem m1, EntradaSaidaDeCarroDaGaragem m2) {  
            	return m1.getLinha().getId().compareTo(m2.getLinha().getId());
            }
        });  		
	}
	
	public static void ordenarPorDataHoraEntrada(List<EntradaSaidaDeCarroDaGaragem> saidas) {
		 Collections.sort(saidas, new Comparator<EntradaSaidaDeCarroDaGaragem>() {  
           @Override  
           public int compare(EntradaSaidaDeCarroDaGaragem m1, EntradaSaidaDeCarroDaGaragem m2) {  
           		int valor = m1.getDataCompetencia().compareTo(m2.getDataCompetencia());
           		
           		if(valor == 0 && m1.getDataHoraEntrada() != null && m2.getDataHoraEntrada() != null)
           			valor = m1.getDataHoraEntrada().compareTo(m2.getDataHoraEntrada());
           		
           		return valor;
           }
       });  		
	}
	
	
	public void calcularDistanciaPercorrida() {
		if(linha != null) {
			distanciaOciosa = BigDecimal.ZERO;
			distanciaProdutiva = BigDecimal.ZERO;
			for(PontoLinha pontoLinha : linha.getPontosLinhas()) {
				distanciaOciosa = distanciaOciosa.add(pontoLinha.getDistanciaOciosa());
				if(pontoLinha.getSentido().equals(PontoLinha.SENTIDO_IDA) && qtdViagemRealizadaIda != null)
					distanciaProdutiva = distanciaProdutiva.add(qtdViagemRealizadaIda.multiply(pontoLinha.getDistanciaProdutiva()));
				else if(pontoLinha.getSentido().equals(PontoLinha.SENTIDO_VOLTA) && qtdViagemRealizadaVolta != null)
					distanciaProdutiva = distanciaProdutiva.add(qtdViagemRealizadaVolta.multiply(pontoLinha.getDistanciaProdutiva()));
			}
		}
	}
	
	void atualizarDataEntrada() {
		if(qtdViagemRealizadaIda != null && qtdViagemRealizadaVolta != null && dataHoraEntrada == null) 
			setDataHoraEntrada(new Date());
	}
	
}
