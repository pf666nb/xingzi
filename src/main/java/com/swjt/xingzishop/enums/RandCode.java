package com.swjt.xingzishop.enums;

import lombok.Getter;

@Getter
public enum RandCode {

    EMAIL("email-code"),
    PHONE("phone-code"),
    PHONE_NUMBER("phone-number"),
    EMAIL_NUMBER("email-address"),
    CAPTCHA("captcha");
    private String type;

    RandCode(String type) {

        this.type = type;
    }
}
