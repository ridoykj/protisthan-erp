package com.itbd.protisthan.constant.enums;

public enum ReligionEnum {
    ISLAM("Islam"),
    HINDU("Hindu"),
    BUDDHIST("Buddhist"),
    CHRISTIAN("Christian"),
    OTHERS("Others");

    private final String value;

    ReligionEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
