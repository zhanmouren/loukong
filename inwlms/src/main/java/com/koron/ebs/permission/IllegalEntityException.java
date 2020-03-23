package com.koron.ebs.permission;

public class IllegalEntityException extends Exception{

	public IllegalEntityException() {
		super();
	}

	public IllegalEntityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public IllegalEntityException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalEntityException(String message) {
		super(message);
	}

	public IllegalEntityException(Throwable cause) {
		super(cause);
	}
}
