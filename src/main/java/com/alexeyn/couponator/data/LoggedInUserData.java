package com.alexeyn.couponator.data;

import com.alexeyn.couponator.enums.UserType;

public class LoggedInUserData {
    private int token;
    private UserType userType;
    private Long organizationId;
    private long userId;

    public LoggedInUserData(UserType userType, Long organizationId, long userId) {
        super();
        this.userType = userType;
        this.organizationId = organizationId;
        this.userId = userId;
    }

    public int getToken() {
        return token;
    }
    public void setToken(int token) {
        this.token = token;
    }
    public UserType getUserType() {
        return userType;
    }
    public void setUserType(UserType userType) {
        this.userType = userType;
    }
    public Long getOrganizationId() {
        return organizationId;
    }
    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
}