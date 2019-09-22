package com.alexeyn.couponator.enums;

public enum UserType {
    ADMIN,
    CUSTOMER,
    COMPANY;


    public static boolean contains(UserType userType) {

        for (UserType u : UserType.values()) {
            if (u.name().equalsIgnoreCase(String.valueOf(userType))) {
                return true;
            }
        }

        return false;
    }

}
