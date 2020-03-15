package br.com.logic.pendotiba.core.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotBlank;

import br.com.logic.pendotiba.core.enums.TipoBombaEnum;

@Entity
@Table(name = "bomba_abastecimento")
public class BombaAbastecimento implements Serializable {
	
	static final long serialVersionUID = 3077692309813133800L;

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	Long id;
	
	@NotBlank(message = "Código é obrigatório")
	@Column(name = "codigo")
	String codigo;
	
	@Column(name = "codigo_exportacao")
	String codigoExportacao;
	
	@Column(name = "descricao")
	String descricao;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_bomba")
	TipoBombaEnum tipoBomba = TipoBombaEnum.DIESEL;
	
	@Column(name = "volume_atual", precision = 7, scale = 1)
	BigDecimal volumeAtual;
	
	@Transient
	String volumeAtualStr;
	
	
	
	
	public BombaAbastecimento() {

	}

	public BombaAbastecimento(String codigo, String descricao) {
		super();
		this.codigo = codigo;
		this.descricao = descricao;
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
	
	public String getCodigoExportacao() {
		return codigoExportacao;
	}
	
	public void setCodigoExportacao(String codigoExportacao) {
		this.codigoExportacao = codigoExportacao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public TipoBombaEnum getTipoBomba() {
		return tipoBomba;
	}
	
	public void setTipoBomba(TipoBombaEnum tipoBomba) {
		this.tipoBomba = tipoBomba;
	}
	
	public BigDecimal getVolumeAtual() {
		return volumeAtual;
	}
	
	public void setVolumeAtual(BigDecimal volumeAtual) {
		this.volumeAtual = volumeAtual;
	}
	
	public String getVolumeAtualStr() {
		if(volumeAtual != null)
			volumeAtualStr = volumeAtual.toString();
		return volumeAtualStr;
	}
	
	public void setVolumeAtualStr(String volumeAtualStr) {
		this.volumeAtualStr = volumeAtualStr;
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
		BombaAbastecimento other = (BombaAbastecimento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	@Override
	public String toString() {
		return codigo;
	}
	
	
	public boolean isNova(){
		return id == null;
	}
	
	public boolean isDiesel(){
		return tipoBomba.equals(TipoBombaEnum.DIESEL);
	}
	
}
