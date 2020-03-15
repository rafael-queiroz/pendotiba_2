package br.com.logic.pendotiba.core.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
@Table(name = "mapa_diario_carro")
public class MapaDiarioCarro implements Serializable, Comparable<MapaDiarioCarro> {
	
	static final long serialVersionUID = -7654425090207754859L;

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	Long id;
	
	@NotNull(message = "Data de competência é obrigatório")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "data_competencia")
	Date dataCompetencia = new Date();
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Column(name = "data_hora_cadastro_abastecimento_diesel")
	Date dataHoraCadastroAbastecimentoDiesel;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Column(name = "data_hora_cadastro_abastecimento_arla")
	Date dataHoraCadastroAbastecimentoArla;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Column(name = "data_hora_cadastro_roleta")
	Date dataHoraCadastroRoleta;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Column(name = "data_hora_cadastro_odometro")
	Date dataHoraCadastroOdometro;
	
	@Column(name = "volume_diesel", precision = 7, scale = 1)
	BigDecimal volumeDiesel;
	
	@Column(name = "volume_arla", precision = 7, scale = 1)
	BigDecimal volumeArla;
	
	@Column(name = "odometro", precision = 6, scale = 0)
	BigInteger odometro;
	
	@Column(name = "roleta", precision = 5, scale = 0)
	BigInteger roleta;
	
	@Column(name = "volume_diesel_anterior", precision = 7, scale = 1)
	BigDecimal volumeDieselAnterior;
	
	@Column(name = "odometro_anterior", precision = 6, scale = 0)
	BigInteger odometroAnterior;
	
	@Column(name = "roleta_anterior", precision = 5, scale = 0)
	BigInteger roletaAnterior;
	
	@Column(name = "km_por_litro", precision = 19, scale = 2)
	BigDecimal kmPorLitro;
	
	@Column(name = "meta_consumo_diesel", precision = 5, scale = 2)
	BigDecimal metaConsumoDiesel;
	
	@Column(name = "diferenca_km_por_litro_percentual", precision = 19, scale = 2)
	BigDecimal diferencaKmPorLitroPercentual;
	
	@NotNull(message = "Carro é obrigatório")
	@ManyToOne
	@JoinColumn(name = "id_carro")
	Carro carro;
	
	@ManyToOne
	@JoinColumn(name = "id_funcionario_roleta")
	Funcionario funcionarioRoleta;
	
	@ManyToOne
	@JoinColumn(name = "id_funcionario_abastecimento_odometro")
	Funcionario funcionarioAbastecimentoOdometro;
	
	@ManyToOne
	@JoinColumn(name = "id_linha")
	Linha linha;
	
	@ManyToOne
	@JoinColumn(name = "id_bomba_abastecimento_diesel")
	BombaAbastecimento bombaAbastecimentoDiesel;
	
	@ManyToOne
	@JoinColumn(name = "id_bomba_abastecimento_arla")
	BombaAbastecimento bombaAbastecimentoArla;
	
	@Transient
	String volumeDieselStr;
	
	@Transient
	String volumeArlaStr;
	
	@Transient
	String roletaStr;
	
	@Transient
	String odometroStr;
	
	@Transient
	String volumeDieselAnteriorStr;
	
	@Transient
	String roletaAnteriorStr;
	
	@Transient
	String odometroAnteriorStr;
	
	@Transient
	BigDecimal diferencaVolumeDiesel;
	
	@Transient
	BigInteger diferencaRoleta;
	
	@Transient
	BigInteger diferencaOdometro;
	
	@Transient
	Boolean podeAlterar;
	
	
	
	
	public MapaDiarioCarro() {

	}

	/*public AbastecimentoOdometroRoleta(Date dataCompetencia) {
		this.dataCompetencia = dataCompetencia;
	}*/

	public MapaDiarioCarro(Date data, Carro carro) {
		this.dataCompetencia = data;
		this.carro = carro;
	}

	public MapaDiarioCarro(Date data, Carro carro, Linha linha) {
		this.dataCompetencia = data;
		this.carro = carro;
		this.linha = linha;
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
	
	public Date getDataHoraCadastroAbastecimentoArla() {
		return dataHoraCadastroAbastecimentoArla;
	}
	
	public void setDataHoraCadastroAbastecimentoArla(Date dataHoraCadastroAbastecimentoArla) {
		this.dataHoraCadastroAbastecimentoArla = dataHoraCadastroAbastecimentoArla;
	}
	
	public Date getDataHoraCadastroAbastecimentoDiesel() {
		return dataHoraCadastroAbastecimentoDiesel;
	}
	
	public void setDataHoraCadastroAbastecimentoDiesel(Date dataHoraCadastroAbastecimentoDiesel) {
		this.dataHoraCadastroAbastecimentoDiesel = dataHoraCadastroAbastecimentoDiesel;
	}
	
	public Date getDataHoraCadastroRoleta() {
		return dataHoraCadastroRoleta;
	}
	
	public void setDataHoraCadastroRoleta(Date dataHoraCadastroRoleta) {
		this.dataHoraCadastroRoleta = dataHoraCadastroRoleta;
	}
	
	public Date getDataHoraCadastroOdometro() {
		return dataHoraCadastroOdometro;
	}
	
	public void setDataHoraCadastroOdometro(Date dataHoraCadastroOdometro) {
		this.dataHoraCadastroOdometro = dataHoraCadastroOdometro;
	}

	public BigDecimal getVolumeDiesel() {
		return volumeDiesel;
	}
	
	public void setVolumeDiesel(BigDecimal volumeDiesel) {
		this.volumeDiesel = volumeDiesel;
	}
	
	public BigDecimal getVolumeArla() {
		return volumeArla;
	}
	
	public void setVolumeArla(BigDecimal volumeArla) {
		this.volumeArla = volumeArla;
	}
	
	public Carro getCarro() {
		return carro;
	}

	public void setCarro(Carro carro) {
		this.carro = carro;
	}

	public BigInteger getRoleta() {
		return roleta;
	}
	
	public void setRoleta(BigInteger roleta) {
		this.roleta = roleta;
	}
	
	public BigInteger getOdometro() {
		return odometro;
	}
	
	public void setOdometro(BigInteger odometro) {
		this.odometro = odometro;
	}
	
	public BigDecimal getVolumeDieselAnterior() {
		return volumeDieselAnterior;
	}

	public void setVolumeDieselAnterior(BigDecimal volumeDieselAnterior) {
		this.volumeDieselAnterior = volumeDieselAnterior;
	}

	public BigInteger getOdometroAnterior() {
		return odometroAnterior;
	}

	public void setOdometroAnterior(BigInteger odometroAnterior) {
		this.odometroAnterior = odometroAnterior;
	}

	public BigInteger getRoletaAnterior() {
		return roletaAnterior;
	}

	public void setRoletaAnterior(BigInteger roletaAnterior) {
		this.roletaAnterior = roletaAnterior;
	}

	public Funcionario getFuncionarioRoleta() {
		return funcionarioRoleta;
	}
	
	public void setFuncionarioRoleta(Funcionario funcionarioRoleta) {
		this.funcionarioRoleta = funcionarioRoleta;
	}
	
	public Funcionario getFuncionarioAbastecimentoOdometro() {
		return funcionarioAbastecimentoOdometro;
	}
	
	public void setFuncionarioAbastecimentoOdometro(Funcionario funcionarioAbastecimentoOdometro) {
		this.funcionarioAbastecimentoOdometro = funcionarioAbastecimentoOdometro;
	}
	
	public BigDecimal getKmPorLitro() {
		return kmPorLitro;
	}
	
	public void setKmPorLitro(BigDecimal kmPorLitro) {
		this.kmPorLitro = kmPorLitro;
	}
	
	public BigDecimal getMetaConsumoDiesel() {
		return metaConsumoDiesel;
	}
	
	public void setMetaConsumoDiesel(BigDecimal metaConsumoDiesel) {
		this.metaConsumoDiesel = metaConsumoDiesel;
	}
	
	public Linha getLinha() {
		return linha;
	}
	
	public void setLinha(Linha linha) {
		this.linha = linha;
	}
	
	public BombaAbastecimento getBombaAbastecimentoArla() {
		return bombaAbastecimentoArla;
	}
	
	public void setBombaAbastecimentoArla(BombaAbastecimento bombaAbastecimentoArla) {
		this.bombaAbastecimentoArla = bombaAbastecimentoArla;
	}
	
	public BombaAbastecimento getBombaAbastecimentoDiesel() {
		return bombaAbastecimentoDiesel;
	}
	
	public void setBombaAbastecimentoDiesel(BombaAbastecimento bombaAbastecimentoDiesel) {
		this.bombaAbastecimentoDiesel = bombaAbastecimentoDiesel;
	}
	
	public BigDecimal getDiferencaKmPorLitroPercentual() {
		return diferencaKmPorLitroPercentual;
	}
	
	public void setDiferencaKmPorLitroPercentual(BigDecimal diferencaKmPorLitroPercentual) {
		this.diferencaKmPorLitroPercentual = diferencaKmPorLitroPercentual;
	}
	
	
	public String getVolumeDieselStr() {
		if(volumeDiesel != null)
			volumeDieselStr = volumeDiesel.toString();
		return volumeDieselStr;
	}
	
	public void setVolumeDieselStr(String volumeDieselStr) {
		this.volumeDieselStr = volumeDieselStr;
	}
	
	public String getVolumeArlaStr() {
		if(volumeArla != null)
			volumeArlaStr = volumeArla.toString();
		return volumeArlaStr;
	}
	
	public void setVolumeArlaStr(String volumeArlaStr) {
		this.volumeArlaStr = volumeArlaStr;
	}
	
	public String getRoletaStr() {
		if(roleta != null)
			roletaStr = String.format(new Locale("pt"), "%,d", roleta);
		return roletaStr;
	}
	
	public void setRoletaStr(String roletaStr) {
		this.roletaStr = roletaStr;
	}
	
	public String getOdometroStr() {
		if(odometro != null)
			odometroStr = String.format(new Locale("pt"), "%,d", odometro);
		return odometroStr;
	}
	
	public void setOdometroStr(String odometroStr) {
		this.odometroStr = odometroStr;
	}
	
	public String getVolumeDieselAnteriorStr() {
		if(volumeDieselAnterior != null)
			volumeDieselAnteriorStr = volumeDieselAnterior.toString();
		return volumeDieselAnteriorStr;
	}

	public String getRoletaAnteriorStr() {
		if(roletaAnterior != null)
			roletaAnteriorStr = String.format(new Locale("pt"), "%,d", roletaAnterior);
		return roletaAnteriorStr;
	}
	
	public String getOdometroAnteriorStr() {
		if(odometroAnterior != null)
			odometroAnteriorStr = String.format(new Locale("pt"), "%,d", odometroAnterior);
		return odometroAnteriorStr;
	}
	
	public BigDecimal getDiferencaVolumeDiesel() {
		if(volumeDieselAnterior != null && volumeDiesel != null)
			diferencaVolumeDiesel = volumeDiesel.subtract(volumeDieselAnterior);
		return diferencaVolumeDiesel;
	}
	
	public BigInteger getDiferencaOdometro() {
		if(odometroAnterior != null && odometro != null)
			diferencaOdometro = odometro.subtract(odometroAnterior);
		return diferencaOdometro;
	}
	
	public void setDiferencaOdometro(BigInteger diferencaOdometro) {
		this.diferencaOdometro = diferencaOdometro;
	}
	
	public BigInteger getDiferencaRoleta() {
		if(roletaAnterior != null && roleta != null)
			diferencaRoleta = roleta.subtract(roletaAnterior);
		return diferencaRoleta;
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
		MapaDiarioCarro other = (MapaDiarioCarro) obj;
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

	public boolean exibir() {
		return volumeDiesel != null || odometro != null || roleta != null;
	}

	@Override
	public int compareTo(MapaDiarioCarro m) {
		return this.getCarro().getNumeroDeOrdem().compareTo(m.getCarro().getNumeroDeOrdem());
	}
	
	public static void ordenaPorLinhaPorCarro(List<MapaDiarioCarro> lista) {  
        Collections.sort(lista, new Comparator<MapaDiarioCarro>() {  
            @Override  
            public int compare(MapaDiarioCarro m1, MapaDiarioCarro m2) {  
            	int valor = 0;
            			
    			if(m1.getLinha() != null && m2.getLinha() == null) {
             		valor = -1;
             	} else if(m1.getLinha() == null && m2.getLinha() != null) {
             		valor = 1;
             	} else if(m1.getLinha() != null && m2.getLinha() != null) {
             		valor = m1.getLinha().codigo.compareTo(m2.getLinha().getCodigo());
             	}
            	
				//se for igual, comparar por numero de ordem
				if (valor == 0){
					return m1.getCarro().getNumeroDeOrdem().compareTo(m2.getCarro().getNumeroDeOrdem());
				}
				
				return valor;
            }
        });  
    }
	
	
	public static void ordenaPorTipoDeChassiPorCarro(List<MapaDiarioCarro> lista) {  
        Collections.sort(lista, new Comparator<MapaDiarioCarro>() {  
            @Override  
            public int compare(MapaDiarioCarro m1, MapaDiarioCarro m2) {  
            	int valor = 0;
            			
    			if(m1.getCarro().getTipoChassi() != null && m2.getCarro().getTipoChassi() == null) {
             		valor = -1;
             	} else if(m1.getCarro().getTipoChassi() == null && m2.getCarro().getTipoChassi() != null) {
             		valor = 1;
             	} else if(m1.getCarro().getTipoChassi() != null && m2.getCarro().getTipoChassi() != null) {
             		valor = m1.getCarro().getTipoChassi().getNome().compareTo(m2.getCarro().getTipoChassi().getNome());
             	}
            	
				//se for igual, comparar por numero de ordem
				if (valor == 0){
					return m1.getCarro().getNumeroDeOrdem().compareTo(m2.getCarro().getNumeroDeOrdem());
				}
				
				return valor;
            }
        });  
    }
	
	
	public static void ordenaPorCarroPorEntrada(List<MapaDiarioCarro> lista) {  
        Collections.sort(lista, new Comparator<MapaDiarioCarro>() {  
            @Override  
            public int compare(MapaDiarioCarro m1, MapaDiarioCarro m2) {  
            	
            	int valor = m1.getCarro().getNumeroDeOrdem().compareTo(m2.getCarro().getNumeroDeOrdem());
            	
            	//se for igual, comparar por data de cadastro
				if (valor == 0){
					valor = m1.getDataCompetencia().compareTo(m2.getDataCompetencia());
				}
            	
				
				//se for igual, comparar por id
				if (valor == 0){
					valor = m1.getId().compareTo(m2.getId());
				}
				
				
				return valor;
            }
        });  
    }
	
	

	public static void ordenaPorDataHoraCadastroAbastecimentoDiesel(List<MapaDiarioCarro> lista) {  
        Collections.sort(lista, new Comparator<MapaDiarioCarro>() {  
            @Override  
            public int compare(MapaDiarioCarro m1, MapaDiarioCarro m2) {  
				return m1.getDataHoraCadastroAbastecimentoDiesel().compareTo(m2.getDataHoraCadastroAbastecimentoDiesel());
            }
        });  
    }
	
}
