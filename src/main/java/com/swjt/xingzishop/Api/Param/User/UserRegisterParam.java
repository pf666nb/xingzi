package com.swjt.xingzishop.Api.Param.User;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * xingzishop
 * 用户注册所需的参数类
 *
 * @author : wpf
 * @date : 2021-06-09 13:10
 **/
@Data
public class UserRegisterParam {

    //用户账号
    @NotEmpty
    private String User_LoginName;
    //用户登录使用的密码加密后存储数据库
    @NotEmpty
    private String User_PassWord;

}
