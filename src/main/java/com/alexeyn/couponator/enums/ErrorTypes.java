package com.alexeyn.couponator.enums;

public enum ErrorTypes {
    GENERAL_ERROR("General error", true),
    HACKING_ATTEMPT("Hacking Attempt", true),
	ENTITY_NOT_FOUND("Entity not found", false),
    FAIL_TO_GENERATE_ID("Couldn't generate an ID", false),
    LOGIN_FAILED("Login failed. Please try again.", false),
    NAME_ALREADY_EXISTS("The name you chose already exist. Enter another name", false),
    ID_DOES_NOT_EXIST("This ID dons't exist", false),
    NON_REPLACEABLE_NAME("Cannot change the name", false),
    INCORRECT_PASSWORD("Password must contain at least 6 characters, one letter and one digit",false),
    NOT_ENOUGH_COUPONS_LEFT("Not enough coupons left to purchase the amount requested", false),
    COUPON_EXPIRED("The coupon is expiered", false),
    COUPON_TITLE_EXIST_FOR_COMPANY("The company already have a coupon with the same title",false),
    COUPON_PER_COMPANY_DONT_EXIST("There is no coupon for such company in DB", false),
    CANNOT_UPDATE_COMPANY_ID("Coupons company ID cannot be changed", false),
    INVALID_PRICE("Coupon price must be more than 0", false),
    INVALID_AMOUNT("Coupon amount must be more than 0", false),
    INVALID_DATES("The dates you've entered are wrong", false),
    INVALID_ID("ID must be grater then 0", false),
    COMPANY_ALREADY_EXIST("Company Already Exist", false),
    COMPANY_DOES_NOT_EXIST("Company doesn't exist", false),
    NULL_DATA("Provided Data is not instastiatated", false),
    COUPON_DOES_NOT_EXIST("Coupon doesn't exist", false),
    COUPON_ALREADY_EXITS("Coupon already exist", false),
    CUSTOMER_DOES_NOT_EXIST("Customer already Exist", false),
    CUSTOMER_ALREADY_EXIST("Customer doesn't Exist", false),
    USER_DOES_NOT_EXIST("Customer already Exist", false),
    USER_ALREADY_EXIST("Customer doesn't Exist", false),
    USER_TYPE_DOES_NOT_EXIST("User Type doesn't exist", false),
    PURCHASE_DOES_NOT_EXIST("Purchase doesn't exist", false),
    PURCHASE_ALREADY_EXIST("Purchase already exist", false),
    REDUNDANT_DATA("Redundant data", false),
    EMPTY_DATA("Provided data is empty", false),
    EMPTY_TABLE("Table is empty", false);

    private String errorMessage;
    private boolean isShowStackTrace;

    private ErrorTypes(String internalMessage, boolean isShowStackTrace) {
        this.errorMessage = internalMessage;
        this.isShowStackTrace = isShowStackTrace;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isShowStackTrace() {
        return isShowStackTrace;
    }
}
