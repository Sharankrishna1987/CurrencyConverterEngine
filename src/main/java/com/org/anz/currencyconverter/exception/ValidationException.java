package com.org.anz.currencyconverter.exception;

import com.org.anz.currencyconverter.constant.ApplicationConstant;

public class ValidationException extends ApplicationException {

	private static final long serialVersionUID = 2697459386476527265L;

	public ValidationException(String message) {
		super(String.format(ApplicationConstant.APP_ERROR_MESSAGE + ": %s", message));
	}

}