package com.neueda.atm.task.exception;

public class ExceptionResponse {
	private String message;
	private String requestURI;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getRequestURI() {
		return requestURI;
	}
	public void setRequestURI(String requestURI) {
		this.requestURI = requestURI;
	}
	

}
