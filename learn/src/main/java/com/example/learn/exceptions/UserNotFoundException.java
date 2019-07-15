package com.example.learn.exceptions;

public class UserNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8170475204809012003L;

	public UserNotFoundException(String message) {
		super(message);
	}
}
