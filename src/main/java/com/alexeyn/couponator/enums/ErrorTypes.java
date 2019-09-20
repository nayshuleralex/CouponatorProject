package com.alexeyn.couponator.enums;

public enum ErrorTypes {
    GENERAL_ERROR("General error", true),
    HACKING_ATTEMPT("Hacking Attempt", true),
	ENTITY_NOT_FOUND("Entity not found", false),
    FAIL_TO_GENERATE_ID("Couldn't generate an ID", false),
    LOGIN_FAILED("Login failed. Please try again.", false),
    NAME_ALREADY_EXISTS("The name you chose already exist. Enter another name", false),
    ID_DONT_EXIST("This ID dons't exist", false), NON_REPLACEABLE_NAME("Cannot change the name", false),
    INNCORECT_PASSWORD("Password must contain 6-10 charaters, at least one letter, at least one digit", false),
    NOT_ENOUGH_COUPONS_LEFT("Not enough coupons left to purchase the amount requested", false),
    COUPON_EXPIERED("The coupon is expiered", false),
    COUPON_TITLE_EXIST_FOR_COMPANY("The company already have a coupon with the same title, please change the title",
            false),
    COUPON_PER_COMPANY_DONT_EXIST("There is no coupon for such company in DB", false),
    CANNOT_UPDATE_COMPANY_ID("Coupons company ID cannot be changed", false),
    INVALID_PRICE("Coupon price must be more than 0", false),
    INVALID_AMOUNT("Coupon amount must be more than 0", false),
    INVALID_DATES("The dates you've entered are wrong", false), INVALID_ID("ID must be grater then 0", false),
    RESULT_SET_EXTRACTION_FAIL("Failed to extract data from result set", false),
    EMPTY_RESULT_SET("ResultSet is empty", false), COMPANY_ALREADY_EXIST("Company Already Exist", false),
    COMPANY_DONT_EXIST("Company doesn't exist", false), NULL_DATA("Provided Data is not instastiatated", false),
    COUPON_DONT_EXIST("Coupon doesn't exist", false), COUPON_ALREADY_EXITS("Coupon already exist", false),
    CUSTOMER_DONT_EXIST("Customer already Exist", false), CUSTOMER_ALREADY_EXIST("Customer doesn't Exist", false),
    USER_DONT_EXIST("Customer already Exist", false), USER_ALREADY_EXIST("Customer doesn't Exist", false),
    PURCHASE_DONT_EXIST("Purchase doesn't exist", false), PURCHASE_ALREADY_EXIST("Purchase already exist", false),
    EMPTY_DATA("Provided data is empty", false), EMPTY_TABLE("Table is empty", false);

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
