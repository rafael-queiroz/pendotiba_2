package br.com.logic.pendotiba.despachante.exception;

public class ObjectNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 8009210542503237466L;

	public ObjectNotFoundException(String msg) {
		super(msg);
	}
	
	public ObjectNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}

}