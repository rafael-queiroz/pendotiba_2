package br.com.logic.pendotiba.core.enums;

public enum TipoBombaEnum {
	
	DIESEL(1, "Diesel"),
	ARLA(2, "Arla");
	
	private int cod;
	private String descricao;
	
	private TipoBombaEnum(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public int getCod() {
		return cod;
	}
	
	public String getDescricao () {
		return descricao;
	}
	
	public static TipoBombaEnum toEnum(Integer cod) {
		
		if (cod == null) {
			return null;
		}
		
		for (TipoBombaEnum x : TipoBombaEnum.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: " + cod);
	}
	
}
