package com.swjt.xingzishop.enums;

import lombok.Getter;

@Getter
public enum UserEnum {
    ERROR(1001, "用户表单验证错误"),
    OCCUPY(1002, "用户名已经存在了！"),
    WAIT(0, "正在审核，还未上架"),
    SUC(2, "已经被领养");
    private Integer code;
    private String msg;

    UserEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
