package com.swjt.xingzishop.enums;

import lombok.Getter;

@Getter
public enum PetStateEnum {
    UP(1,"正常，未被领养，已经上架"),
    WAIT(0,"正在审核，还未上架"),
    SUC(2,"已经被领养");
    private Integer code;
    private String msg;
    PetStateEnum(Integer code,String msg){
        this.code = code;
        this.msg =msg;
    }

}
