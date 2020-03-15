package br.com.logic.pendotiba.core.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name = "mapa_diario_bomba_abastecimento")
public class MapaDiarioBombaAbastecimento implements Serializable {
	
	static final long serialVersionUID = -7637976835828790402L;

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	Long id;
	
	@NotNull(message = "Data de competência é obrigatório")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "data_competencia")
	Date dataCompetencia;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Column(name = "data_hora_cadastro")
	Date dataHoraCadastro;
	
	@Column(name = "volume_inicial", precision = 7, scale = 1)
	BigDecimal volumeInicial;
	
	@Column(name = "volume_final", precision = 7, scale = 1)
	BigDecimal volumeFinal;
	
	@Column(name = "volume_total", precision = 7, scale = 1)
	BigDecimal volumeTotal;
	
	@Column(name = "volume_pelo_abastecimento", precision = 7, scale = 1)
	BigDecimal volumePeloAbastecimento;
	
	@Column(name = "diferenca_mapa_abastecimento", precision = 7, scale = 1)
	BigDecimal diferencaMapaAbastecimento;
	
	@NotNull(message = "Bomba de abastecimento é obrigatório")
	@ManyToOne
	@JoinColumn(name = "id_bomba_abastecimento")
	BombaAbastecimento bombaAbastecimento;
	
	
	@Transient
	String volumeInicialStr;
	
	@Transient
	String volumeFinalStr;
	
	@Transient
	String volumeTotalStr;
	
	@Transient
	String volumePeloAbastecimentoStr;
	
	@Transient
	String diferencaMapaAbastecimentoStr;
	
	@Transient
	List<MapaDiarioCarro> mapasDiarioCarro;
	
	@Transient
	Boolean podeAlterar = true;

	
	
	public MapaDiarioBombaAbastecimento() {

	}

	public MapaDiarioBombaAbastecimento(Date dataCompetencia, Date dataHoraCadastro, BigDecimal volumeInicial, BombaAbastecimento bombaAbastecimento) {
		this.dataCompetencia = dataCompetencia;
		this.dataHoraCadastro = dataHoraCadastro;
		this.volumeInicial = volumeInicial;
		this.bombaAbastecimento = bombaAbastecimento;
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
	
	public Date getDataHoraCadastro() {
		return dataHoraCadastro;
	}
	
	public void setDataHoraCadastro(Date dataHoraCadastro) {
		this.dataHoraCadastro = dataHoraCadastro;
	}
	
	public BigDecimal getVolumeInicial() {
		return volumeInicial;
	}

	public void setVolumeInicial(BigDecimal volumeInicial) {
		this.volumeInicial = volumeInicial;
	}

	public BigDecimal getVolumeFinal() {
		return volumeFinal;
	}

	public void setVolumeFinal(BigDecimal volumeFinal) {
		this.volumeFinal = volumeFinal;
	}

	public BigDecimal getVolumeTotal() {
		return volumeTotal;
	}

	public void setVolumeTotal(BigDecimal diferenca) {
		this.volumeTotal = diferenca;
	}

	public BombaAbastecimento getBombaAbastecimento() {
		return bombaAbastecimento;
	}

	public void setBombaAbastecimento(BombaAbastecimento bombaAbastecimento) {
		this.bombaAbastecimento = bombaAbastecimento;
	}

	public BigDecimal getVolumePeloAbastecimento() {
		return volumePeloAbastecimento;
	}
	
	public void setVolumePeloAbastecimento(BigDecimal volumePeloAbastecimento) {
		this.volumePeloAbastecimento = volumePeloAbastecimento;
	}
	
	public BigDecimal getDiferencaMapaAbastecimento() {
		return diferencaMapaAbastecimento;
	}
	
	public void setDiferencaMapaAbastecimento(BigDecimal diferencaMapaAbastecimento) {
		this.diferencaMapaAbastecimento = diferencaMapaAbastecimento;
	}
	
	
	// TRANSIENTS
	public String getVolumeInicialStr() {
		if(volumeInicial != null)
			volumeInicialStr = volumeInicial.toString();
		return volumeInicialStr;
	}

	public void setVolumeInicialStr(String volumeInicialStr) {
		this.volumeInicialStr = volumeInicialStr;
	}

	public String getVolumeFinalStr() {
		if(volumeFinal != null)
			volumeFinalStr = volumeFinal.toString();
		return volumeFinalStr;
	}

	public void setVolumeFinalStr(String volumeFinalStr) {
		this.volumeFinalStr = volumeFinalStr;
	}

	public String getVolumeTotalStr() {
		if(volumeTotal != null)
			volumeTotalStr = volumeTotal.toString();
		return volumeTotalStr;
	}

	public void setVolumeTotalStr(String diferencaStr) {
		this.volumeTotalStr = diferencaStr;
	}
	
	public String getVolumePeloAbastecimentoStr() {
		if(volumePeloAbastecimento != null)
			volumePeloAbastecimentoStr = volumePeloAbastecimento.toString();
		return volumePeloAbastecimentoStr;
	}
	
	public void setVolumePeloAbastecimentoStr(String volumePeloAbastecimentoStr) {
		this.volumePeloAbastecimentoStr = volumePeloAbastecimentoStr;
	}
	
	public String getDiferencaMapaAbastecimentoStr() {
		if(diferencaMapaAbastecimento != null)
			diferencaMapaAbastecimentoStr = diferencaMapaAbastecimento.toString();
		return diferencaMapaAbastecimentoStr;
	}
	
	public void setDiferencaMapaAbastecimentoStr(String diferencaMapaAbastecimentoStr) {
		this.diferencaMapaAbastecimentoStr = diferencaMapaAbastecimentoStr;
	}
	
	public List<MapaDiarioCarro> getMapasDiarioCarro() {
		return mapasDiarioCarro;
	}
	
	public void setMapasDiarioCarro(List<MapaDiarioCarro> mapasDiarioCarro) {
		this.mapasDiarioCarro = mapasDiarioCarro;
	}
	
	public Boolean getPodeAlterar() {
		return podeAlterar;
	}
	
	public void setPodeAlterar(Boolean podeAlterar) {
		this.podeAlterar = podeAlterar;
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
		MapaDiarioBombaAbastecimento other = (MapaDiarioBombaAbastecimento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	public boolean isNovo() {
		return id == null;
	}
	
}
