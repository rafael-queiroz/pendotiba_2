package br.com.logic.pendotiba.logicbus.resources.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

import br.com.logic.pendotiba.core.model.MapaDiarioCarro;
import br.com.logic.pendotiba.core.util.DataUtil;

public class AbastecimentoOdometroRoletaDTO {

	Long id;
	String dataCompetencia;
	
	Long idCarro;
	String numeroDeOrdem;
	
	BigDecimal volumeDiesel;
	BigDecimal volumeDieselAnterior;
	
	BigDecimal volumeArla;

	BigInteger odometro;
	BigInteger odometroAnterior;
	
	BigInteger roleta;
	BigInteger roletaAnterior;
	
	Long idFuncionarioRoleta;
	Long idFuncionarioAbastecimentoOdometro;
	
	Long idBombaAbastecimentoDiesel;
	Long idBombaAbastecimentoArla;
	
	Boolean tanqueCheio = false;
	
	
	
	public AbastecimentoOdometroRoletaDTO() {
	}
	
	public AbastecimentoOdometroRoletaDTO(MapaDiarioCarro obj) {
		this.id = obj.getId();
		this.dataCompetencia = DataUtil.getDataStringYYYYMMDD(obj.getDataCompetencia());
		this.volumeDiesel = obj.getVolumeDiesel();
		this.volumeArla = obj.getVolumeArla();
		this.odometro = obj.getOdometro();
		this.roleta = obj.getRoleta();
		this.idCarro = obj.getCarro().getId();
		this.numeroDeOrdem = obj.getCarro().getNumeroDeOrdem();
		
		if (this.volumeDieselAnterior == null && this.volumeDiesel == null)
			this.volumeDieselAnterior = obj.getCarro().getUltimoAbastecimentoDiesel();
		else
			this.volumeDieselAnterior = obj.getVolumeDieselAnterior();
			
		if (this.odometroAnterior == null && this.odometro == null)
			this.odometroAnterior = obj.getCarro().getOdometro();
		else
			this.odometroAnterior = obj.getOdometroAnterior();
		
		if (this.roletaAnterior == null && this.roleta == null)
			this.roletaAnterior = obj.getCarro().getRoleta1();
		else
			this.roletaAnterior = obj.getRoletaAnterior();
		
		this.idFuncionarioRoleta = obj.getFuncionarioRoleta() != null ? obj.getFuncionarioRoleta().getId() : null;
		this.idFuncionarioAbastecimentoOdometro = obj.getFuncionarioAbastecimentoOdometro() != null ? obj.getFuncionarioAbastecimentoOdometro().getId() : null;
		
		this.idBombaAbastecimentoDiesel = obj.getBombaAbastecimentoDiesel() != null ? obj.getBombaAbastecimentoDiesel().getId() : null;
		this.idBombaAbastecimentoArla = obj.getBombaAbastecimentoArla() != null ? obj.getBombaAbastecimentoArla().getId() : null;
		
		if(obj.getVolumeDiesel() != null && obj.getVolumeDiesel().signum() == 0)
			this.tanqueCheio = true;
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
	
	public BigInteger getOdometro() {
		return odometro;
	}
	
	public void setOdometro(BigInteger odometro) {
		this.odometro = odometro;
	}
	
	public BigInteger getRoleta() {
		return roleta;
	}
	
	public void setRoleta(BigInteger roleta) {
		this.roleta = roleta;
	}
	
	public Long getIdCarro() {
		return idCarro;
	}
	
	public void setIdCarro(Long idCarro) {
		this.idCarro = idCarro;
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

	public String getNumeroDeOrdem() {
		return numeroDeOrdem;
	}

	public void setNumeroDeOrdem(String numeroDeOrdem) {
		this.numeroDeOrdem = numeroDeOrdem;
	}
	
	public Long getIdFuncionarioRoleta() {
		return idFuncionarioRoleta;
	}
	
	public void setIdFuncionarioRoleta(Long idFuncionarioRoleta) {
		this.idFuncionarioRoleta = idFuncionarioRoleta;
	}
	
	public Long getIdFuncionarioAbastecimentoOdometro() {
		return idFuncionarioAbastecimentoOdometro;
	}
	
	public void setIdFuncionarioAbastecimentoOdometro(Long idFuncionarioAbastecimentoOdometro) {
		this.idFuncionarioAbastecimentoOdometro = idFuncionarioAbastecimentoOdometro;
	}
	
	public Long getIdBombaAbastecimentoArla() {
		return idBombaAbastecimentoArla;
	}
	
	public void setIdBombaAbastecimentoArla(Long idBombaAbastecimentoArla) {
		this.idBombaAbastecimentoArla = idBombaAbastecimentoArla;
	}
	
	public Long getIdBombaAbastecimentoDiesel() {
		return idBombaAbastecimentoDiesel;
	}
	
	public void setIdBombaAbastecimentoDiesel(Long idBombaAbastecimentoDiesel) {
		this.idBombaAbastecimentoDiesel = idBombaAbastecimentoDiesel;
	}
	
	public Boolean getTanqueCheio() {
		return tanqueCheio;
	}
	
	public void setTanqueCheio(Boolean tanqueCheio) {
		this.tanqueCheio = tanqueCheio;
	}
	
}