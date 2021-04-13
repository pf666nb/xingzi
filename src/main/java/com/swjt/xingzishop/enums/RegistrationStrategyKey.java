package com.swjt.xingzishop.enums;

import lombok.Getter;

@Getter
public enum RegistrationStrategyKey {

    QQ("QQ"),
    PHONE("PHONE"),
    EMAIL("EMAIL");

    private String strategy;

    RegistrationStrategyKey(String strategy) {
        this.strategy = strategy;
    }
}
