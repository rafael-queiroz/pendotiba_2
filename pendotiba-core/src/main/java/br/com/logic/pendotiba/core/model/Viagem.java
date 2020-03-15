package br.com.logic.pendotiba.core.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name = "viagem")
public class Viagem implements Serializable, Comparable<Viagem> {
	
	static final long serialVersionUID = -1290084366826177498L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	Long id;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "data_competencia")
	Date dataCompetencia;
	
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	@Column(name = "hora_saida_programada")
	Date horaSaidaProgramada;
	
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	@Column(name = "hora_saida_realizada")
	Date horaSaidaRealizada;
	
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	@Column(name = "hora_chegada")
	Date horaChegada;
	
	@Column(name = "roleta_final_1", precision = 5, scale = 0)
	BigInteger roletaFinal1;
	
	@Column(name = "ordem_viagem", precision = 2, scale = 0)
	BigInteger ordemViagem;
	
	@ManyToOne
	@JoinColumn(name = "id_programacao")
	Programacao programacao;
	
	@ManyToOne
	@JoinColumn(name = "id_ponto_linha")
	PontoLinha pontoLinha;
	
	@ManyToOne
	@JoinColumn(name = "id_linha_programada")
	Linha linhaProgramada;
	
	@ManyToOne
	@JoinColumn(name = "id_linha_realizada")
	Linha linhaRealizada;
	
	@ManyToOne
	@JoinColumn(name = "id_carro_realizado")
	Carro carroRealizado;
	
	@ManyToOne
	@JoinColumn(name = "id_tipo_viagem_perdida")
	TipoViagemPerdida tipoViagemPerdida;
	
	@ManyToOne
	@JoinColumn(name = "id_motivo_pulo_viagem")
	MotivoPuloViagem motivoPuloViagem;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario_responsavel_chegada")
	Usuario usuarioResponsavelChegada;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario_responsavel_saida")
	Usuario usuarioResponsavelSaida;
	
	@Column(name = "obs_viagem_perdida")
	String obsViagemPerdida;
	
	@Column(name = "obs_viagem_pulada")
	String obsViagemPulada;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_viagem")
	List<ErroViagem> erros;
	
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	@Column(name = "hora_sd")
	Date horaSD;
	
	@Column(name = "sd")
	Boolean sd = false;
	
	
	@Transient
	String roletaFinal1Str;
	
	
	@PrePersist
	void prePersit() {
		if(this.programacao != null)
			this.dataCompetencia = this.programacao.getDataCompetencia();
		
		if(this.linhaRealizada == null)
			this.linhaRealizada = this.linhaProgramada;
	}
	
	
	
	public Viagem() {
		
	}

	
	public Viagem(Programacao programacao, Date dataCompetencia, Date horaSaidaProgramada, Linha linha, PontoLinha pontoLinha, Date horaSaidaRealizada, Linha linhaRealizada) {
		this.programacao = programacao;
		this.linhaProgramada = linha;
		this.horaSaidaProgramada = horaSaidaProgramada;
		//this.ordemViagem = ordemViagem;
		this.pontoLinha = pontoLinha;
		this.dataCompetencia = dataCompetencia;
		//this.roletaFinal1 = roletaFinal1;
		this.horaSaidaRealizada = horaSaidaRealizada;
		this.linhaRealizada = linhaRealizada;
	}
	

	// cadastro viagem pelo app apos ciação de nova guia
	public Viagem(Programacao programacao, Date dataCompetencia, Date horaSaidaProgramada, Linha linhaProgramada, PontoLinha pontoLinha) {
		this.programacao = programacao;
		this.dataCompetencia = dataCompetencia;
		this.horaSaidaProgramada = horaSaidaProgramada;
		this.linhaProgramada = linhaProgramada;
		this.pontoLinha = pontoLinha;
	}


	// cadastro viagem extra pelo app 
	public Viagem(Programacao programacao, Date dataCompetencia, Date horaSaidaRealizada, Linha linhaRealizada, PontoLinha pontoLinha, Carro carroRealizado, 
			Usuario usuarioRespSaida, BigInteger roletaFinal1) {
		this.programacao = programacao;
		this.dataCompetencia = dataCompetencia;
		this.horaSaidaRealizada = horaSaidaRealizada;
		this.linhaRealizada = linhaRealizada;
		this.pontoLinha = pontoLinha;
		this.carroRealizado = carroRealizado;
		this.usuarioResponsavelSaida = usuarioRespSaida;
		this.roletaFinal1 = roletaFinal1;
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

	public Date getHoraSaidaProgramada() {
		return horaSaidaProgramada;
	}

	public void setHoraSaidaProgramada(Date horaSaidaProgramada) {
		this.horaSaidaProgramada = horaSaidaProgramada;
	}

	public Date getHoraSaidaRealizada() {
		return horaSaidaRealizada;
	}

	public void setHoraSaidaRealizada(Date horaSaidaRealizada) {
		this.horaSaidaRealizada = horaSaidaRealizada;
	}
	
	public Date getHoraChegada() {
		return horaChegada;
	}
	
	public void setHoraChegada(Date horaChegada) {
		this.horaChegada = horaChegada;
	}

	public Programacao getProgramacao() {
		return programacao;
	}

	public void setProgramacao(Programacao programacao) {
		this.programacao = programacao;
	}

	public PontoLinha getPontoLinha() {
		return pontoLinha;
	}

	public void setPontoLinha(PontoLinha pontoLinha) {
		this.pontoLinha = pontoLinha;
	}

	public Linha getLinhaProgramada() {
		return linhaProgramada;
	}

	public void setLinhaProgramada(Linha linhaProgramada) {
		this.linhaProgramada = linhaProgramada;
	}

	public Linha getLinhaRealizada() {
		return linhaRealizada;
	}

	public void setLinhaRealizada(Linha linhaRealizada) {
		this.linhaRealizada = linhaRealizada;
	}
	
	public Carro getCarroRealizado() {
		return carroRealizado;
	}

	public void setCarroRealizado(Carro carroRealizado) {
		this.carroRealizado = carroRealizado;
	}

	public BigInteger getRoletaFinal1() {
		return roletaFinal1;
	}
	
	public void setRoletaFinal1(BigInteger roletaFinal1) {
		this.roletaFinal1 = roletaFinal1;
	}
	
	public BigInteger getOrdemViagem() {
		return ordemViagem;
	}
	
	public void setOrdemViagem(BigInteger ordemViagem) {
		this.ordemViagem = ordemViagem;
	}

	public TipoViagemPerdida getTipoViagemPerdida() {
		return tipoViagemPerdida;
	}
	
	public void setTipoViagemPerdida(TipoViagemPerdida tipoViagemPerdida) {
		this.tipoViagemPerdida = tipoViagemPerdida;
	}

	public String getObsViagemPerdida() {
		return obsViagemPerdida;
	}
	
	public void setObsViagemPerdida(String obsViagemPerdida) {
		this.obsViagemPerdida = obsViagemPerdida;
	}
	
	public String getObsViagemPulada() {
		return obsViagemPulada;
	}
	
	public void setObsViagemPulada(String obsViagemPulada) {
		this.obsViagemPulada = obsViagemPulada;
	}
	
	public Usuario getUsuarioResponsavelChegada() {
		return usuarioResponsavelChegada;
	}
	
	public void setUsuarioResponsavelChegada(Usuario usuarioResponsavelChegada) {
		this.usuarioResponsavelChegada = usuarioResponsavelChegada;
	}
	
	public Usuario getUsuarioResponsavelSaida() {
		return usuarioResponsavelSaida;
	}
	
	public void setUsuarioResponsavelSaida(Usuario usuarioResponsavelSaida) {
		this.usuarioResponsavelSaida = usuarioResponsavelSaida;
	}
	
	public List<ErroViagem> getErros() {
		if(erros == null)
			erros = new ArrayList<ErroViagem>();
		return erros;
	}
	
	public void setErros(List<ErroViagem> erros) {
		this.erros = erros;
	}

	public MotivoPuloViagem getMotivoPuloViagem() {
		return motivoPuloViagem;
	}
	
	public void setMotivoPuloViagem(MotivoPuloViagem motivoPuloViagem) {
		this.motivoPuloViagem = motivoPuloViagem;
	}
	
	public Date getHoraSD() {
		return horaSD;
	}

	public void setHoraSD(Date horaSD) {
		this.horaSD = horaSD;
	}

	public Boolean getSd() {
		return sd;
	}

	public void setSd(Boolean sd) {
		this.sd = sd;
	}



	// TRANSIENT
	public String getRoletaFinal1Str() {
		if(roletaFinal1 != null)
			roletaFinal1Str = String.format(new Locale("pt"), "%,d", roletaFinal1);
		return roletaFinal1Str;
	}
	
	public void setRoletaFinal1Str(String roletaFinal1Str) {
		this.roletaFinal1Str = roletaFinal1Str;
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
		Viagem other = (Viagem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	@Override
	public int compareTo(Viagem o) {
		//return horaSaidaProgramada.compareTo(o.getHoraSaidaProgramada());
		return ordemViagem.compareTo(o.getOrdemViagem());
	}
	
	
	@Override
	public String toString() {
		//return horaSaidaProgramada != null ? horaSaidaProgramada.toString() :  horaSaidaRealizada.toString(); // mudado por cauda da criacao de guia nova pelo app que nao manda id linha 20190325
		
		if (horaSaidaProgramada != null)
			return horaSaidaProgramada.toString();
		else if (horaSaidaRealizada != null)
			return horaSaidaRealizada.toString();
		return "";
		
	}
	
	public boolean isNova(){
		return id == null;
	}
	
	public boolean podeAlterar() {
		int contador = 0;
		
		List<Viagem> viagens = programacao.getViagens();
		for (Viagem v : viagens) {
			if (!viagens.get(0).equals(this) && v.equals(this)) {
				Viagem viagemAnterior = viagens.get(contador - 1);
				if(viagemAnterior.getHoraSaidaRealizada() == null )
					return false;
			}
			contador ++;
		}
		
		return horaChegada == null;
	}
	
	public boolean podeAlterarPorUsuario(Usuario usuario) {
		if(usuario.getPerfil().getId().equals(5L) && !programacao.liberada()) 
			return false;
		return true;
	}


	public void tratarRoletas() {
		int contador = 0;
		List<Viagem> viagens = programacao.getViagens();
		for (Viagem v : viagens) {
			if (viagens.get(0).equals(this)) {
				this.roletaFinal1 = this.roletaFinal1 == null ? this.getProgramacao().carroAtual().getRoletaInicial1() : this.roletaFinal1;
				break;
			} else if (v.equals(this)) {
				Viagem viagemAnterior = viagens.get(contador - 1);
				this.roletaFinal1 = this.roletaFinal1 == null ? viagemAnterior.getRoletaFinal1() : this.roletaFinal1;
				break;
			}
			contador ++;
		}
	}
	
	
	public boolean marcarSaida() {
		return horaSaidaRealizada == null && horaChegada == null;
	}
	
	public boolean marcarChegada() {
		return horaSaidaRealizada != null && horaChegada == null;
	}
}
