package com.alexeyn.couponator.exceptions;

import java.sql.SQLException;

import com.alexeyn.couponator.enums.ErrorTypes;

public class ApplicationException extends SQLException {

	/**
	 * WHAT THE FUCK IS THIS???
	 */
	private static final long serialVersionUID = 1L;
	private ErrorTypes errorTypes;

	public ApplicationException(Exception e, ErrorTypes errorTypes, String message) {
		super(message, e);
		this.errorTypes = errorTypes;
	}

	public ApplicationException(ErrorTypes errorTypes, String message) {
		super(message);
		this.errorTypes = errorTypes;
	}

	public ErrorTypes getErrorTypes() {
		return errorTypes;
	}

}
