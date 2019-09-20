package com.alexeyn.couponator.enums;

public enum UserType {
    ADMIN,
    CUSTOMER,
    COMPANY;


    public static boolean contains(String userType) {

        for (UserType u : UserType.values()) {
            if (u.name().equalsIgnoreCase(userType)) {
                return true;
            }
        }

        return false;
    }

}
