package com.alexeyn.couponator.enums;

public enum ErrorType_old {
	GENERAL_ERROR(600, "General error"),
	NAME_IS_ALREADY_EXISTS(600, "The name you chose is already exist. Please pick another name"),
	INVALID_ID(600, "The ID you've enter is invalid"), 
	INVALID_AMOUNT(600, "The amount you've entered is invalid"),
	INVALID_PRICE(600, "The price you've entered is invalid"),
	INVALID_EMAIL(600, "The email you've entered is invalid. Please try again."),
	INVALID_PASSWORD(600, "The password you've entered is invalid. Please try again."),
	INVALID_DATES(600, "The dates you've entered is invalid. Please try again."),
	FIELD_IS_IRREPLACEABLE(600, "You can't change this field."),
	NAME_IS_IRREPLACEABLE(600, "You can't change your name."), 
	COUPON_IS_OUT_OF_ORDER(600, "Coupon is out of order"),
	LOGIN_FAILED(600, "Login failed. credentials is incorrect, Please try again.");

	private int internalErrorCode;
	private String internalMessage;
	

	private ErrorType_old(int internalErrorCode, String internalMessage) {
		this.internalErrorCode = internalErrorCode;
		this.internalMessage = internalMessage;
	}

	public int getInternalErrorCode() {
		return internalErrorCode;
	}

	public String getInternalMessage() {
		return internalMessage;
	}
}
