package com.swjt.xingzishop.enums;

import lombok.Getter;

@Getter
public enum RoleEnum {
    ADMIN("ADMIN"),
    USER("USER"),
    SUPER("ADMIN,USER");
    private String role;
    RoleEnum(String role){
        this.role = role;
    }
}
