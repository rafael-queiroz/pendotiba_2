package br.com.logic.pendotiba.core.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.text.ParseException;
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
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.logic.pendotiba.core.util.DataUtil;


@Entity
@Table(name = "programacao")
public class Programacao implements Serializable {
	
	static final long serialVersionUID = -6340588160404444788L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	Long id;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "data_competencia")
	Date dataCompetencia;
	
	/*
	@Column(name = "codigo_escala")
	String codigoEscala;
	*/
	
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	@Column(name = "inicio_trabalho")
	Date inicioTrabalho;
	
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	@Column(name = "termino_trabalho")
	Date terminoTrabalho;
	
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	@Column(name = "inicio_jornada")
	Date inicioJornada;
	
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	@Column(name = "termino_jornada")
	Date terminoJornada;
	
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	@Column(name = "hora_liberacao")
	Date horaLiberacao;
	
	@ManyToOne
	@JoinColumn(name = "id_ponto_pegada_motorista")
	Ponto pontoPegadaMotorista;
	
	@ManyToOne
	@JoinColumn(name = "id_motorista_programado")
	Funcionario motoristaProgramado;
	
	@ManyToOne
	@JoinColumn(name = "id_motorista")
	Funcionario motorista;
	
	@ManyToOne
	@JoinColumn(name = "id_parceiro")
	Funcionario parceiro;
	
	@ManyToOne
	@JoinColumn(name = "id_linha")
	Linha linha;
	
	@ManyToOne
	@JoinColumn(name = "id_turno")
	Turno turno;
	
	@ManyToOne
	@JoinColumn(name = "id_carro_programado")
	Carro carroProgramado;
	
	@ManyToOne
	@JoinColumn(name = "id_carro_realizado")
	Carro carroRealizado;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_programacao")
	List<Viagem> viagens;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_programacao")
	List<ErroProgramacao> erros;
	
	@ManyToOne
	@JoinColumn(name = "id_status")
	Status status; 
	
	@Column(name = "completa")
	Boolean completa = true; 
	
	@Column(name = "obs_liberacao")
	String obsLiberacao;
	
	@ManyToOne
	@JoinColumn(name = "id_garagem")
	Garagem garagem;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_programacao")
	List<CarroProgramacao> roletas;
	
	@Column(name = "mudou_ponto_pegada")
	Integer mudouPontoPegada = 0;
	
	@Transient
	BigInteger roletaInicial1;
	
	@Transient
	String uuid;
	
	@Transient
	Usuario usuario;
	

	public Programacao() {
		
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
	/*
	public String getCodigoEscala() {
		return codigoEscala;
	}

	public void setCodigoEscala(String codigoEscala) {
		this.codigoEscala = codigoEscala;
	}
	*/
	public Date getInicioTrabalho() {
		return inicioTrabalho;
	}
	
	public void setInicioTrabalho(Date inicioTrabalho) {
		this.inicioTrabalho = inicioTrabalho;
	}
	
	public Date getTerminoTrabalho() {
		return terminoTrabalho;
	}
	
	public void setTerminoTrabalho(Date terminoTrabalho) {
		this.terminoTrabalho = terminoTrabalho;
	}
	
	public Date getInicioJornada() {
		return inicioJornada;
	}
	
	public void setInicioJornada(Date inicioJornada) {
		this.inicioJornada = inicioJornada;
	}
	
	public Date getTerminoJornada() {
		return terminoJornada;
	}
	
	public void setTerminoJornada(Date terminoJornada) {
		this.terminoJornada = terminoJornada;
	}
	
	public Date getHoraLiberacao() {
		return horaLiberacao;
	}
	
	public void setHoraLiberacao(Date horaLiberacao) {
		this.horaLiberacao = horaLiberacao;
	}

	public Ponto getPontoPegadaMotorista() {
		return pontoPegadaMotorista;
	}
	
	public void setPontoPegadaMotorista(Ponto pontoPegadaMotorista) {
		this.pontoPegadaMotorista = pontoPegadaMotorista;
	}

	public Funcionario getMotoristaProgramado() {
		return motoristaProgramado;
	}
	
	public void setMotoristaProgramado(Funcionario motoristaProgramado) {
		this.motoristaProgramado = motoristaProgramado;
	}
	
	public Funcionario getMotorista() {
		return motorista;
	}

	public void setMotorista(Funcionario motorista) {
		this.motorista = motorista;
	}
	
	public Funcionario getParceiro() {
		if(parceiro == null)
			parceiro = new Funcionario();
		return parceiro;
	}
	
	public void setParceiro(Funcionario parceiro) {
		this.parceiro = parceiro;
	}

	public Linha getLinha() {
		return linha;
	}

	public void setLinha(Linha linha) {
		this.linha = linha;
	}

	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}
	
	public Carro getCarroProgramado() {
		return carroProgramado;
	}
	
	public void setCarroProgramado(Carro carroProgramado) {
		this.carroProgramado = carroProgramado;
	}
	
	public Carro getCarroRealizado() {
		return carroRealizado;
	}
	
	public void setCarroRealizado(Carro carroRealizado) {
		this.carroRealizado = carroRealizado;
	}
	
	public List<Viagem> getViagens() {
		if(viagens == null)
			viagens = new ArrayList<Viagem>();
		return viagens;
	}
	
	public void setViagens(List<Viagem> viagens) {
		this.viagens = viagens;
	}
	
	public List<ErroProgramacao> getErros() {
		if(erros == null)
			erros = new ArrayList<ErroProgramacao>();
		return erros;
	}
	
	public void setErros(List<ErroProgramacao> erros) {
		this.erros = erros;
	}

	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}

	public Boolean getCompleta() {
		return completa;
	}

	public void setCompleta(Boolean completa) {
		this.completa = completa;
	}
	
	public String getObsLiberacao() {
		return obsLiberacao;
	}
	
	public void setObsLiberacao(String obsLiberacao) {
		this.obsLiberacao = obsLiberacao;
	}
	
	public Garagem getGaragem() {
		return garagem;
	}
	
	public void setGaragem(Garagem garagem) {
		this.garagem = garagem;
	}
	
	public List<CarroProgramacao> getRoletas() {
		if(roletas == null)
			roletas = new ArrayList<CarroProgramacao>();
		return roletas;
	}
	
	public Integer getMudouPontoPegada() {
		return mudouPontoPegada;
	}
	
	public void setMudouPontoPegada(Integer mudouPontoPegada) {
		this.mudouPontoPegada = mudouPontoPegada;
	}
	
	public void setRoletas(List<CarroProgramacao> roletas) {
		this.roletas = roletas;
	}
	
	public BigInteger getRoletaInicial1() {
		if(roletaInicial1 == null && carroAtual() != null)
			return carroAtual().getCarro().getRoleta1();
		return roletaInicial1;
	}
	
	public void setRoletaInicial1(BigInteger roletaInicial1) {
		this.roletaInicial1 = roletaInicial1;
	}
	
	/*
	public BigInteger getRoletaInicial2() {
		if(roletaInicial2 == null && carroAtual() != null)
			return carroAtual().getCarro().getRoleta2();
		return roletaInicial2;
	}
	
	public void setRoletaInicial2(BigInteger roletaInicial2) {
		this.roletaInicial2 = roletaInicial2;
	}
	
	public BigInteger getRoletaInicial3() {
		if(roletaInicial3 == null && carroAtual() != null)
			return carroAtual().getCarro().getRoleta3();
		return roletaInicial3;
	}
	
	public void setRoletaInicial3(BigInteger roletaInicial3) {
		this.roletaInicial3 = roletaInicial3;
	}
	*/
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
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
		Programacao other = (Programacao) obj;
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

	
	public boolean liberada() {
		return status != null && status.getId().equals(Status.LIBERADO);
	}
	
	public boolean ativa() {
		return status != null && status.getId().equals(Status.ATIVO);
	}
	
	public boolean inativa() {
		return status != null && status.getId().equals(Status.CORTADO);
	}
	
	public boolean encerrado() {
		return status != null && status.getId().equals(Status.ENCERRADO);
	}
	
	public boolean podeAlterar() {
		return !encerrado() && !possuiViagensRealizadas();
	}
	
	
	public boolean podeEncerrar() {
		return status.getId().equals(Status.LIBERADO);
	}
	
	
	public boolean podeTrocarCarro() {
		return (status.getId().equals(Status.LIBERADO) && !podeAlterar());
	}
	
	
	public void atualizarRoletas(BigInteger roletaFinal1) {
		for(CarroProgramacao roleta : roletas)
			if(roleta.getCarro().equals(carroRealizado)) 
				roleta.setRoletaFinal1(roletaFinal1);
	}
	

	public CarroProgramacao carroAtual() {
		return !roletas.isEmpty() ? roletas.get(getRoletas().size()-1) : null;
	}
	
	
	public boolean exibirRoleta1() {
		return !liberada() && carroRealizado != null;
	}
	
	public boolean exibirRoleta2() {
		return !liberada() && carroRealizado != null && carroRealizado.getQuantidadeRoletas() > 1;
	}
	
	public boolean exibirRoleta3() {
		return !liberada() && carroRealizado != null && carroRealizado.getQuantidadeRoletas() > 2;
	}
	
	
	
	// 18.01.2019 - Rafael
	public boolean podeLiberar() throws ParseException {
		if(!ativa() || !completa || carroRealizado == null || motorista == null || !DataUtil.ehHoje(dataCompetencia))
			return false;
		
		return true;
	}
	
	public boolean possuiViagensRealizadas() {
		for (Viagem viagem : viagens) 
			if(viagem.getHoraSaidaRealizada() != null)
				return true;
		return false;
	}
	
	public boolean possuiViagens() {
		return viagens.isEmpty();
	}

	
	public Viagem getPrimeiraViagem() {
		if(viagens.isEmpty())
			return viagens.get(0);
		return null;
	}
	
	
	public boolean saiuNoHorario() {
		if(possuiViagensRealizadas())
			if (getPrimeiraViagem().getHoraSaidaRealizada().equals(inicioTrabalho))
				return true;
		return false;
	}
}
