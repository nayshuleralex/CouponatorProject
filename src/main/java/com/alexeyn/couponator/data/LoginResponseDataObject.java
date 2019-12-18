package com.alexeyn.couponator.data;


import com.alexeyn.couponator.enums.UserType;

public class LoginResponseDataObject {
    private UserType type;
    private String token;

    public LoginResponseDataObject(String token, UserType type) {
        this.type = type;
        this.token = token;
    }

    public LoginResponseDataObject() {
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    @Override
    public String toString() {
        return "LoginResponseDataObject [" +
                "userType=" + type +
                ", token=" + token +
                ']';
    }
}
