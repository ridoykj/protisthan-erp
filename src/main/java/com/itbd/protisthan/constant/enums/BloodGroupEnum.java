package com.itbd.protisthan.constant.enums;

public enum BloodGroupEnum {
    A_POSITIVE("A+"),
    A_NEGATIVE("A-"),
    B_POSITIVE("B+"),
    B_NEGATIVE("B-"),
    O_POSITIVE("O+"),
    O_NEGATIVE("O-"),
    AB_POSITIVE("AB+"),
    AB_NEGATIVE("AB-");

    private final String value;

    BloodGroupEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
