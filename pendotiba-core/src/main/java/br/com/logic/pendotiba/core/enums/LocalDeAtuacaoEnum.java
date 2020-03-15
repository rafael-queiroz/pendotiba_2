package br.com.logic.pendotiba.core.enums;

public enum LocalDeAtuacaoEnum {
	
	RIO_DE_JANEIRO(1, "Rio de Janeiro"),
	NITEROI(2, "Niterói"),
	OUTROS(2, "Outros");
	
	private int cod;
	private String descricao;
	
	private LocalDeAtuacaoEnum(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public int getCod() {
		return cod;
	}
	
	public String getDescricao () {
		return descricao;
	}
	
	public static LocalDeAtuacaoEnum toEnum(Integer cod) {
		
		if (cod == null) {
			return null;
		}
		
		for (LocalDeAtuacaoEnum x : LocalDeAtuacaoEnum.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: " + cod);
	}
	
}
