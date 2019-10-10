package com.comakeit.spring.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Data Integrity Exception")
public class DataIntegrityException extends DataIntegrityViolationException{

	private static final long serialVersionUID = -3802842312819622523L;
	
	public DataIntegrityException(String msg) {
		super(msg);
	}

}
