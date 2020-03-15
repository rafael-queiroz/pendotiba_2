package br.com.logic.pendotiba.core.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "ponto")
public class Ponto implements Serializable {
	
	static final long serialVersionUID = 4469862319984729357L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	Long id;
	
	@NotBlank(message = "Código é obrigatório")
	@Column(name = "codigo")
	String codigo;
	
	@NotBlank(message = "Descrição é obrigatório")
	@Column(name = "descricao")
	String descricao;
	
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	@Column(name = "tempo_presta_conta")
	Date tempoPrestaConta;
	
	@ManyToOne
	@JoinColumn(name = "id_terminal")
	Terminal terminal;
	
	@OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
	@JoinColumn(name = "id_ponto")
	List<DispositivoMovel> dispositivos;
	
	
	
	public Ponto() {
		
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Date getTempoPrestaConta() {
		return tempoPrestaConta;
	}
	
	public void setTempoPrestaConta(Date tempoPrestaConta) {
		this.tempoPrestaConta = tempoPrestaConta;
	}

	public Terminal getTerminal() {
		return terminal;
	}
	
	public void setTerminal(Terminal terminal) {
		this.terminal = terminal;
	}
	
	public List<DispositivoMovel> getDispositivos() {
		if(dispositivos == null)
			dispositivos = new ArrayList<DispositivoMovel>();
		return dispositivos;
	}
	
	public void setDispositivos(List<DispositivoMovel> dispositivos) {
		this.dispositivos = dispositivos;
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
		Ponto other = (Ponto) obj;
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
		return codigo;
	}

}
