package br.com.logic.pendotiba.core.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.logic.pendotiba.core.enums.TipoArquivoEnum;

@Entity
@Table(name = "arquivo_importado")
public class ArquivoImportado implements Serializable {
	
	static final long serialVersionUID = -1702020379527059728L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	Long id;
	
	@Column(name = "nome_arquivo")
	String nomeArquivo;
	
	@Column(name = "nome_arquivo_original")
	String nomeArquivoOriginal;
	
	@Column(name = "content_type_arquivo")
	String contentTypeArquivo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Column(name = "data_importacao")
	Date dataImportacao;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "data_competencia")
	Date dataCompetencia;
	
	@Column(name = "quantidade_registros")
	Long quantidadeRegistros;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	Usuario usuario;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_arquivo")
	TipoArquivoEnum tipoArquivo;
	
	@Column(name = "erro")
	Boolean erro = false;
	
	
	@Transient
	boolean dataCompetenciaRepetida = false;
	
	
	
	public ArquivoImportado() {

	}



	//GETTERS E SETTERS
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}
	
	public String getNomeArquivoOriginal() {
		return nomeArquivoOriginal;
	}
	
	public void setNomeArquivoOriginal(String nomeArquivoOriginal) {
		this.nomeArquivoOriginal = nomeArquivoOriginal;
	}

	public String getContentTypeArquivo() {
		return contentTypeArquivo;
	}

	public void setContentTypeArquivo(String contentTypeArquivo) {
		this.contentTypeArquivo = contentTypeArquivo;
	}

	public Date getDataImportacao() {
		return dataImportacao;
	}

	public void setDataImportacao(Date dataImportacao) {
		this.dataImportacao = dataImportacao;
	}
	
	public Date getDataCompetencia() {
		return dataCompetencia;
	}
	
	public void setDataCompetencia(Date dataCompetencia) {
		this.dataCompetencia = dataCompetencia;
	}
	
	public Long getQuantidadeRegistros() {
		return quantidadeRegistros;
	}
	
	public void setQuantidadeRegistros(Long quantidadeRegistros) {
		this.quantidadeRegistros = quantidadeRegistros;
	}
	
	public boolean isDataCompetenciaRepetida() {
		return dataCompetenciaRepetida;
	}
	
	public void setDataCompetenciaRepetida(boolean dataCompetenciaRepetida) {
		this.dataCompetenciaRepetida = dataCompetenciaRepetida;
	}

	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public TipoArquivoEnum getTipoArquivo() {
		return tipoArquivo;
	}
	
	public void setTipoArquivo(TipoArquivoEnum tipoArquivo) {
		this.tipoArquivo = tipoArquivo;
	}
	
	public Boolean getErro() {
		return erro;
	}
	
	public void setErro(Boolean erro) {
		this.erro = erro;
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
		ArquivoImportado other = (ArquivoImportado) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
