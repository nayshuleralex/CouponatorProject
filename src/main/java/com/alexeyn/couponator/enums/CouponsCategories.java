package com.alexeyn.couponator.enums;

public enum CouponsCategories {

    FOOD,
    ELECTRICITY,
    VACATION,
    HOBBY;

    public static boolean contains(CouponsCategories couponCategory) {

        for (CouponsCategories c : CouponsCategories.values()) {
            if (c.name().equalsIgnoreCase(String.valueOf(couponCategory))) {
                return true;
            }
        }

        return false;
    }

}
