package br.com.logic.pendotiba.logicbus.resources.dto;

import java.math.BigDecimal;

import br.com.logic.pendotiba.core.model.MapaDiarioBombaAbastecimento;
import br.com.logic.pendotiba.core.util.DataUtil;

public class MapaDiarioBombaAbastecimentoDTO {

	Long id;
	String dataCompetencia;
	BigDecimal volumeInicial;
	BigDecimal volumeFinal;
	BigDecimal diferenca;
	Long idBombaAbastecimento;
	String descricaoBomba;
	
	
	
	public MapaDiarioBombaAbastecimentoDTO() {
	}
	

	public MapaDiarioBombaAbastecimentoDTO(MapaDiarioBombaAbastecimento obj) {
		this.id = obj.getId();
		this.dataCompetencia = DataUtil.getDataStringYYYYMMDD(obj.getDataCompetencia());
		this.volumeInicial = obj.getVolumeInicial() != null ? obj.getVolumeInicial() : obj.getBombaAbastecimento().getVolumeAtual();
		this.volumeFinal = obj.getVolumeFinal();
		this.diferenca = obj.getVolumeTotal();
		this.idBombaAbastecimento = obj.getBombaAbastecimento() != null ? obj.getBombaAbastecimento().getId() : null;
		this.descricaoBomba = obj.getBombaAbastecimento() != null ? obj.getBombaAbastecimento().getDescricao() : null;
	}
	
	
	
	//GETTERS E SETTERS
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getDataCompetencia() {
		return dataCompetencia;
	}
	
	public void setDataCompetencia(String dataCompetencia) {
		this.dataCompetencia = dataCompetencia;
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

	public BigDecimal getDiferenca() {
		return diferenca;
	}

	public void setDiferenca(BigDecimal diferenca) {
		this.diferenca = diferenca;
	}

	public Long getIdBombaAbastecimento() {
		return idBombaAbastecimento;
	}

	public void setIdBombaAbastecimento(Long idBombaAbastecimento) {
		this.idBombaAbastecimento = idBombaAbastecimento;
	}
	
	public String getDescricaoBomba() {
		return descricaoBomba;
	}
	
	public void setDescricaoBomba(String descricaoBomba) {
		this.descricaoBomba = descricaoBomba;
	}
	
}