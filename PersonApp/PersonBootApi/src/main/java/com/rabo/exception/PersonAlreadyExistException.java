package com.rabo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Person already exists")
public class PersonAlreadyExistException extends RuntimeException {
	/**
	 * @author GangavRK
	 *
	 */
	private static final long serialVersionUID = 1L;

	public PersonAlreadyExistException(String message) {
		super(message);
	}
}
