package com.alexeyn.couponator.enums;

public enum CouponsCategories {

    FOOD,
    ELECTRICITY,
    VACATION,
    HOBBY;

    public static boolean contains(String couponCategory) {

        for (CouponsCategories c : CouponsCategories.values()) {
            if (c.name().equalsIgnoreCase(couponCategory)) {
                return true;
            }
        }

        return false;
    }

}
