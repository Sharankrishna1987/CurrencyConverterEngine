package com.org.anz.currencyconverter.exception;

public class FileUtilException extends ApplicationException {

	private static final long serialVersionUID = 2728110025490347730L;

	public FileUtilException() {
		super();
	}

	public FileUtilException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FileUtilException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileUtilException(String message) {
		super(message);
	}

	public FileUtilException(Throwable cause) {
		super(cause);
	}

}