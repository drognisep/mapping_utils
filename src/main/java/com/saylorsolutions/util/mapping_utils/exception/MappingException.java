package com.saylorsolutions.util.mapping_utils.exception;

public class MappingException extends RuntimeException {
	private static final long serialVersionUID = -4110130036077264429L;

	public MappingException() {
	}

	public MappingException(String message) {
		super(message);
	}

	public MappingException(Throwable cause) {
		super(cause);
	}

	public MappingException(String message, Throwable cause) {
		super(message, cause);
	}
}
