package com.blasanka.user_service.exceptions;

public class UnAuthorizedException extends RuntimeException {

	private static final long serialVersionUID = 6339295906015053228L;

	public UnAuthorizedException(String message) {
		super(message);
	}
}
