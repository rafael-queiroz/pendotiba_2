package br.com.logic.pendotiba.logicbus.vo;

import java.io.Serializable;

public class EscalaDetalhesVO implements Serializable {
	
	static final long serialVersionUID = 791509674428337599L;

	String DT_SOLTURA;
	String HR_INICIO_TRABALHO;
	String HR_INICIO_JORNADA;
	String NR_LINHA;
	String TURNO;
	String NR_MATRICULA_MOTORISTA;
	String NR_MATRICULA_COBRADOR;
	String CD_LOCAL_PEGADA;
	String CD_VEICULO;
	
	public EscalaDetalhesVO(String dT_SOLTURA, String hR_INICIO_TRABALHO, String hR_INICIO_JORNADA, String nR_LINHA,
			String tURNO, String nR_MATRICULA_MOTORISTA, String nR_MATRICULA_COBRADOR, String cD_LOCAL_PEGADA,
			String cD_VEICULO) {
		super();
		DT_SOLTURA = dT_SOLTURA;
		HR_INICIO_TRABALHO = hR_INICIO_TRABALHO;
		HR_INICIO_JORNADA = hR_INICIO_JORNADA;
		NR_LINHA = nR_LINHA;
		TURNO = tURNO;
		NR_MATRICULA_MOTORISTA = nR_MATRICULA_MOTORISTA;
		NR_MATRICULA_COBRADOR = nR_MATRICULA_COBRADOR;
		CD_LOCAL_PEGADA = cD_LOCAL_PEGADA;
		CD_VEICULO = cD_VEICULO;
	}
	
	
	public String getDT_SOLTURA() {
		return DT_SOLTURA;
	}
	public void setDT_SOLTURA(String dT_SOLTURA) {
		DT_SOLTURA = dT_SOLTURA;
	}
	public String getHR_INICIO_TRABALHO() {
		return HR_INICIO_TRABALHO;
	}
	public void setHR_INICIO_TRABALHO(String hR_INICIO_TRABALHO) {
		HR_INICIO_TRABALHO = hR_INICIO_TRABALHO;
	}
	public String getHR_INICIO_JORNADA() {
		return HR_INICIO_JORNADA;
	}
	public void setHR_INICIO_JORNADA(String hR_INICIO_JORNADA) {
		HR_INICIO_JORNADA = hR_INICIO_JORNADA;
	}
	public String getNR_LINHA() {
		return NR_LINHA;
	}
	public void setNR_LINHA(String nR_LINHA) {
		NR_LINHA = nR_LINHA;
	}
	public String getTURNO() {
		return TURNO;
	}
	public void setTURNO(String tURNO) {
		TURNO = tURNO;
	}
	public String getNR_MATRICULA_MOTORISTA() {
		return NR_MATRICULA_MOTORISTA;
	}
	public void setNR_MATRICULA_MOTORISTA(String nR_MATRICULA_MOTORISTA) {
		NR_MATRICULA_MOTORISTA = nR_MATRICULA_MOTORISTA;
	}
	public String getNR_MATRICULA_COBRADOR() {
		return NR_MATRICULA_COBRADOR;
	}
	public void setNR_MATRICULA_COBRADOR(String nR_MATRICULA_COBRADOR) {
		NR_MATRICULA_COBRADOR = nR_MATRICULA_COBRADOR;
	}
	public String getCD_LOCAL_PEGADA() {
		return CD_LOCAL_PEGADA;
	}
	public void setCD_LOCAL_PEGADA(String cD_LOCAL_PEGADA) {
		CD_LOCAL_PEGADA = cD_LOCAL_PEGADA;
	}
	public String getCD_VEICULO() {
		return CD_VEICULO;
	}
	public void setCD_VEICULO(String cD_VEICULO) {
		CD_VEICULO = cD_VEICULO;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((CD_LOCAL_PEGADA == null) ? 0 : CD_LOCAL_PEGADA.hashCode());
		result = prime * result + ((CD_VEICULO == null) ? 0 : CD_VEICULO.hashCode());
		result = prime * result + ((DT_SOLTURA == null) ? 0 : DT_SOLTURA.hashCode());
		result = prime * result + ((HR_INICIO_JORNADA == null) ? 0 : HR_INICIO_JORNADA.hashCode());
		result = prime * result + ((HR_INICIO_TRABALHO == null) ? 0 : HR_INICIO_TRABALHO.hashCode());
		result = prime * result + ((NR_LINHA == null) ? 0 : NR_LINHA.hashCode());
		result = prime * result + ((NR_MATRICULA_COBRADOR == null) ? 0 : NR_MATRICULA_COBRADOR.hashCode());
		result = prime * result + ((NR_MATRICULA_MOTORISTA == null) ? 0 : NR_MATRICULA_MOTORISTA.hashCode());
		result = prime * result + ((TURNO == null) ? 0 : TURNO.hashCode());
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
		EscalaDetalhesVO other = (EscalaDetalhesVO) obj;
		if (CD_LOCAL_PEGADA == null) {
			if (other.CD_LOCAL_PEGADA != null)
				return false;
		} else if (!CD_LOCAL_PEGADA.equals(other.CD_LOCAL_PEGADA))
			return false;
		if (CD_VEICULO == null) {
			if (other.CD_VEICULO != null)
				return false;
		} else if (!CD_VEICULO.equals(other.CD_VEICULO))
			return false;
		if (DT_SOLTURA == null) {
			if (other.DT_SOLTURA != null)
				return false;
		} else if (!DT_SOLTURA.equals(other.DT_SOLTURA))
			return false;
		if (HR_INICIO_JORNADA == null) {
			if (other.HR_INICIO_JORNADA != null)
				return false;
		} else if (!HR_INICIO_JORNADA.equals(other.HR_INICIO_JORNADA))
			return false;
		if (HR_INICIO_TRABALHO == null) {
			if (other.HR_INICIO_TRABALHO != null)
				return false;
		} else if (!HR_INICIO_TRABALHO.equals(other.HR_INICIO_TRABALHO))
			return false;
		if (NR_LINHA == null) {
			if (other.NR_LINHA != null)
				return false;
		} else if (!NR_LINHA.equals(other.NR_LINHA))
			return false;
		if (NR_MATRICULA_COBRADOR == null) {
			if (other.NR_MATRICULA_COBRADOR != null)
				return false;
		} else if (!NR_MATRICULA_COBRADOR.equals(other.NR_MATRICULA_COBRADOR))
			return false;
		if (NR_MATRICULA_MOTORISTA == null) {
			if (other.NR_MATRICULA_MOTORISTA != null)
				return false;
		} else if (!NR_MATRICULA_MOTORISTA.equals(other.NR_MATRICULA_MOTORISTA))
			return false;
		if (TURNO == null) {
			if (other.TURNO != null)
				return false;
		} else if (!TURNO.equals(other.TURNO))
			return false;
		return true;
	}
	
	
	

}
