package com.alexeyn.couponator.data;

import com.alexeyn.couponator.enums.UserType;

public class LoggedInUserData {
    private String token;
    private UserType type;
    private Long companyId;
    private Long userId;

    public LoggedInUserData() {
    }

    public LoggedInUserData(UserType type, Long companyId, Long userId) {
        this(type, userId);
        this.companyId = companyId;
    }

    public LoggedInUserData(UserType type, Long userId) {
        this.type = type;
        this.companyId = null;
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}