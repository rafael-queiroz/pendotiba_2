package br.com.logic.pendotiba.abastecimento.dto;

import br.com.logic.pendotiba.core.model.Perfil;
import br.com.logic.pendotiba.core.model.Usuario;

public class LoginUsuarioDTO {
	
	String status;
	String perfil;
	String errorDescricao;
	String matricula;
	String nome;
	Long idFuncionario;
	Boolean podeAbastecer;
	
	
	public LoginUsuarioDTO() {
	}
	
	

	public LoginUsuarioDTO(Usuario usuario, String status, StatusDescricaoVO vo) {
		this.status = status;
		this.matricula = usuario.getFuncionario().getMatricula();
		this.nome = usuario.getFuncionario().getNome();
		this.idFuncionario = usuario.getFuncionario().getId();
		this.perfil = verificarPerfil(usuario, vo);
		this.errorDescricao = vo.getErrorDescricao();
		this.podeAbastecer = vo.getPodeAbastecer();
	}

	
	String verificarPerfil(Usuario usuario, StatusDescricaoVO vo) {
		if(usuario.getPerfil().getId().equals(Perfil.APP_ABASTECIMENTO_E_ENCERRANTE)) {
			if(vo.getPodeAbastecer())
				return "APP_ABASTECIMENTO_ODOMETRO";
			return "APP_BOMBA_ABASTECIMENTO";
		}
		
		return usuario.getPerfil().getNome();
	}



	public LoginUsuarioDTO(String status, String errorDescricao) {
		this.status = status;
		this.errorDescricao = errorDescricao;
	}



	//GETTERS E SETTERS
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getPerfil() {
		return perfil;
	}
	
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	
	public String getErrorDescricao() {
		return errorDescricao;
	}
	
	public void setErrorDescricao(String errorDescricao) {
		this.errorDescricao = errorDescricao;
	}
	
	public String getMatricula() {
		return matricula;
	}
	
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Long getIdFuncionario() {
		return idFuncionario;
	}
	
	public void setIdFuncionario(Long idFuncionario) {
		this.idFuncionario = idFuncionario;
	}
	
	public Boolean getPodeAbastecer() {
		return podeAbastecer;
	}
	
	public void setPodeAbastecer(Boolean podeAbastecer) {
		this.podeAbastecer = podeAbastecer;
	}

}
