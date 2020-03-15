package br.com.logic.pendotiba.despachante.dto;

import br.com.logic.pendotiba.core.model.Funcionario;

public class FuncionarioDTO {
	
	Long id;
	String matricula;
	String nome;
	Boolean ativo = true;
	String cartao;
	Boolean motorista;
	Boolean cobrador;
	
	
	
	public FuncionarioDTO() {

	}
	
	
	public FuncionarioDTO(Funcionario funcionario) {
		this.id = funcionario.getId();
		this.matricula = funcionario.getMatricula();
		this.nome = funcionario.getNome();
		this.ativo = funcionario.getAtivo();
		this.cartao = funcionario.getCartao();
		this.motorista = funcionario.getFuncao().getMotorista();
		this.cobrador = funcionario.getFuncao().getCobrador();
	}
	
	

	//GETTERS E SETTERS
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	
	public Boolean getAtivo() {
		return ativo;
	}
	
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	
	public String getCartao() {
		return cartao;
	}
	
	public void setCartao(String cartao) {
		this.cartao = cartao;
	}
	
	public Boolean getMotorista() {
		return motorista;
	}
	
	public void setMotorista(Boolean motorista) {
		this.motorista = motorista;
	}
	
	public Boolean getCobrador() {
		return cobrador;
	}
	
	public void setCobrador(Boolean cobrador) {
		this.cobrador = cobrador;
	}

}
