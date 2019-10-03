package com.comakeit.spring.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Not Found")
public class DetailsNotFound extends RuntimeException {

	private static final long serialVersionUID = 4246996151043045151L;

	public DetailsNotFound() {
		super();
	}
}
