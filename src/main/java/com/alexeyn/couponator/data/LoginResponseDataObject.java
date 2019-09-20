package com.alexeyn.couponator.data;


import com.alexeyn.couponator.enums.UserType;

public class LoginResponseDataObject {
    private UserType userType;
    private int token;

    public LoginResponseDataObject(int token, UserType userType) {
        this.userType = userType;
        this.token = token;
    }

    public LoginResponseDataObject() {
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }


    @Override
    public String toString() {
        return "LoginResponseDataObject [" +
                "userType=" + userType +
                ", token=" + token +
                ']';
    }
}
