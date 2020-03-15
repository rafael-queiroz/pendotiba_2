package br.com.logic.pendotiba.despachante.handler;

import java.io.Serializable;

public class FieldMessage implements Serializable {
	
	private static final long serialVersionUID = -5161435470354241267L;

	String fieldName;
	String message;
	
	
	
	public FieldMessage() {
	}

	public FieldMessage(String fieldName, String message) {
		super();
		this.fieldName = fieldName;
		this.message = message;
	}

	
	
	//GETTERS E SETTERS
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
