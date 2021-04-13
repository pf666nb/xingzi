package com.swjt.xingzishop.Bean;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    //用户ID，用户的唯一表示
    private String User_Id;
    //用户的昵称，用于前台展示
    private String User_Name;
    //用户登录使用的账号
    private String User_LoginName;
    //用户登录使用的密码加密后存储数据库
    private String User_PassWord;
    //用户是否可以登录，默认是true，可以登录
    private Boolean User_IsBan=true;
    //用户的注册时间
    private Date User_CreateTime;
    //用户资料的修改时间
    private Date User_UpdateTime;
}
