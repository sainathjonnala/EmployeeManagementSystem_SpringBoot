package com.comakeit.spring.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Invalid Credentials")
public class InvalidLoginException extends RuntimeException {

	private static final long serialVersionUID = 6115354918826801894L;

	public InvalidLoginException() {
		super();
	}

	public InvalidLoginException(String message) {
		super(message);
	}
}
