package com.iworks.justiceleague.exception;

public class HeroDoesNotExistException extends RuntimeException {

	private static final long serialVersionUID = -4319643136669828876L;
	
	private int status = 400;

	public HeroDoesNotExistException(String message) {
		super(message);
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}	
	
}
