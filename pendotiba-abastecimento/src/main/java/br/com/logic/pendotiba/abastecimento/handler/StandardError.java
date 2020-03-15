package br.com.logic.pendotiba.abastecimento.handler;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class StandardError implements Serializable {
	
	private static final long serialVersionUID = -8409265805134181865L;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	Date timestamp;
	Integer status;
	String error;
	String message;
	String path;
	
	
	
	public StandardError(Long timestamp, Integer status, String error, String message, String path) {
		super();
		this.timestamp = new Date(timestamp);
		this.status = status;
		this.error = error;
		this.message = message;
		this.path = path;
	}

	
	
	//GETTERS E SETTERS
	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
