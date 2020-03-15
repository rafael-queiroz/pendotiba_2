package br.com.logic.pendotiba.logicbus.service.exception;

import org.springframework.http.HttpStatus;

public class ApiValidationException extends Exception {

	static final long serialVersionUID = -1834011907663538669L;
	HttpStatus httpStatus;

    public ApiValidationException() {
        super();
    }

    public ApiValidationException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public ApiValidationException(HttpStatus httpStatus, Throwable cause) {
        super(cause);
        this.httpStatus = httpStatus;
    }

    public ApiValidationException(HttpStatus httpStatus, String message, Throwable cause) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

}
