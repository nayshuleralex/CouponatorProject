package com.alexeyn.couponator.enums;

public enum ErrorTypes {
    GENERAL_ERROR("General error", true, 601),
    HACKING_ATTEMPT("Hacking Attempt", true, 602),
    ENTITY_NOT_FOUND("Entity not found", false, 409),
    FAIL_TO_GENERATE_ID("Couldn't generate an ID", false, 409),
    LOGIN_FAILED("Login failed. Please try again.", false, 409),
    NAME_ALREADY_EXIST("The name you chose already exist. Enter another name", false, 409),
    ID_DOES_NOT_EXIST("This ID dons't exist", false, 409),
    NON_REPLACEABLE_NAME("Cannot change the name", false, 409),
    INCORRECT_PASSWORD("Password must contain at least 6 characters, one letter and one digit", false, 409),
    NOT_ENOUGH_COUPONS_LEFT("Not enough coupons left to purchase the amount requested", false, 409),
    COUPON_EXPIRED("The coupon is expired", false, 409),
    COUPON_TITLE_EXIST_FOR_COMPANY("The company already have a coupon with the same title", false, 409),
    COUPON_PER_COMPANY_DOES_NOT_EXIST("There is no coupon for such company in DB", false, 409),
    CANNOT_UPDATE_COMPANY_ID("Coupons company ID cannot be changed", false, 409),
    COUPON_TYPE_DOES_NOT_EXIST("Coupon type doesn't exist", false, 409),
    INVALID_PRICE("Coupon price must be more than 0", false, 409),
    INVALID_AMOUNT("Coupon amount must be more than 0", false, 409),
    INVALID_DATES("The dates you've entered are wrong", false, 409),
    INVALID_USER_TYPE("UserType is not allowed", false, 603),
    INVALID_ID("ID must be grater then 0", false, 409),
    COMPANY_ALREADY_EXIST("Company Already Exist", false, 409),
    COMPANY_DOES_NOT_EXIST("Company doesn't exist", false, 409),
    NULL_DATA("Provided Data is not instantiated", false, 409),
    COUPON_DOES_NOT_EXIST("Coupon doesn't exist", false, 409),
    COUPON_ALREADY_EXIST("Coupon already exist", false, 409),
    CUSTOMER_DOES_NOT_EXIST("Customer already Exist", false, 409),
    CUSTOMER_ALREADY_EXIST("Customer doesn't Exist", false, 409),
    USER_DOES_NOT_EXIST("Customer already Exist", false, 409),
    USER_ALREADY_EXIST("Customer doesn't Exist", false, 409),
    USER_TYPE_DOES_NOT_EXIST("User Type doesn't exist", false, 409),
    PURCHASE_DOES_NOT_EXIST("Purchase doesn't exist", false, 409),
    PURCHASE_ALREADY_EXIST("Purchase already exist", false, 409),
    REDUNDANT_DATA("Redundant data", false, 409),
    EMPTY_DATA("Provided data is empty", false, 409),
    EMPTY_TABLE("Table is empty", false, 409),
    UPDATE_FAILED("Cannot update table", false, 409);

    private String errorMessage;
    private boolean isCritical;
    private int internalErrorCode;

    ErrorTypes(String internalMessage, boolean isShowStackTrace, int internalErrorCode) {
        this.errorMessage = internalMessage;
        this.isCritical = isShowStackTrace;
        this.internalErrorCode = internalErrorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isCritical() {
        return isCritical;
    }

    public int getInternalErrorCode() {
        return internalErrorCode;
    }

}
