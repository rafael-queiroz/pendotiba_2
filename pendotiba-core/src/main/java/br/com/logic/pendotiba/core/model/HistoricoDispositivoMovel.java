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
@Table(name = "historico_dispositivo_movel")
public class HistoricoDispositivoMovel implements Serializable {
	
	private static final long serialVersionUID = 5285106253864928852L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Column(name = "data_cadastro")
	Date dataCadastro;
	
	@Column(name = "observacao")
	String observacao;
	
	@ManyToOne
	@JoinColumn(name = "id_ponto")
	Ponto ponto;
	
	@ManyToOne
	@JoinColumn(name = "id_dispositivo_movel")
	DispositivoMovel dispositivoMovel;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	Usuario usuario;
	
	
	
	public HistoricoDispositivoMovel() {

	}

	

	public HistoricoDispositivoMovel(DispositivoMovel dispositivoMovel, Usuario usuario) {
		this.dispositivoMovel = dispositivoMovel;
		this.observacao = dispositivoMovel.getObservacao();
		this.ponto = dispositivoMovel.getPonto();
		this.dataCadastro = dispositivoMovel.getDataCadastro();
		this.usuario = usuario;
	}



	//GETTERS E SETTERS
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	
	public String getObservacao() {
		return observacao;
	}
	
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Ponto getPonto() {
		return ponto;
	}

	public void setPonto(Ponto ponto) {
		this.ponto = ponto;
	}

	public DispositivoMovel getDispositivoMovel() {
		return dispositivoMovel;
	}

	public void setDispositivoMovel(DispositivoMovel dispositivoMovel) {
		this.dispositivoMovel = dispositivoMovel;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
		HistoricoDispositivoMovel other = (HistoricoDispositivoMovel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
