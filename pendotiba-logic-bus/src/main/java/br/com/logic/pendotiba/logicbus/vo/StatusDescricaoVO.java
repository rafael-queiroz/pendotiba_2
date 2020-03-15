package br.com.logic.pendotiba.logicbus.vo;

public class StatusDescricaoVO {
	
	Boolean podeAbastecer;
	String errorDescricao;
	
	
	public StatusDescricaoVO() {
	}
	
	public StatusDescricaoVO(Boolean podeAbastecer, String errorDescricao) {
		super();
		this.podeAbastecer = podeAbastecer;
		this.errorDescricao = podeAbastecer ? "" : "A(s) bomba(s): ".concat(errorDescricao).concat(" não foi(foram) finalizada(s) no dia anterior");
	}

	
	
	public Boolean getPodeAbastecer() {
		return podeAbastecer;
	}
	
	public void setPodeAbastecer(Boolean podeAbastecer) {
		this.podeAbastecer = podeAbastecer;
	}
	
	public String getErrorDescricao() {
		return errorDescricao;
	}
	
	public void setErrorDescricao(String errorDescricao) {
		this.errorDescricao = errorDescricao;
	}

}
