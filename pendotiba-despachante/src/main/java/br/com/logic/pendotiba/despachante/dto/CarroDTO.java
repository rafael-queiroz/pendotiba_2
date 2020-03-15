package br.com.logic.pendotiba.despachante.dto;

import java.math.BigInteger;

import br.com.logic.pendotiba.core.model.Carro;

public class CarroDTO {

	Long id;
	String numeroDeOrdem;
	Long quantidadeRoletas;
	BigInteger roleta1;
	BigInteger roleta2;
	BigInteger roleta3;

	
	
	public CarroDTO() {

	}
	
	public CarroDTO(Carro carro) {
		this.id = carro.getId();
		this.numeroDeOrdem = carro.getNumeroDeOrdem();
		this.quantidadeRoletas = carro.getQuantidadeRoletas();
		this.roleta1 = carro.getRoleta1();
		this.roleta2 = BigInteger.ZERO;
		this.roleta3 = BigInteger.ZERO;
	}




	//GETTERS E SETTERS
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroDeOrdem() {
		return numeroDeOrdem;
	}

	public void setNumeroDeOrdem(String numeroDeOrdem) {
		this.numeroDeOrdem = numeroDeOrdem;
	}

	public Long getQuantidadeRoletas() {
		return quantidadeRoletas;
	}

	public void setQuantidadeRoletas(Long quantidadeRoletas) {
		this.quantidadeRoletas = quantidadeRoletas;
	}

	public BigInteger getRoleta1() {
		return roleta1;
	}

	public void setRoleta1(BigInteger roleta1) {
		this.roleta1 = roleta1;
	}

	public BigInteger getRoleta2() {
		return roleta2;
	}

	public void setRoleta2(BigInteger roleta2) {
		this.roleta2 = roleta2;
	}

	public BigInteger getRoleta3() {
		return roleta3;
	}

	public void setRoleta3(BigInteger roleta3) {
		this.roleta3 = roleta3;
	}

}
