package com.swjt.xingzishop.Vo;

import lombok.Data;

import java.util.Date;

@Data
public class UserVo {

    private String accountNumber;

    private String volunteerId;
    /**昵称*/
    private String nickname;
    /**
     * 用户头像
     */
    private String portrait;
    /**邮箱*/
    private String email;
    /**手机*/
    private String phone;
    /**
     * 创建时间
     */
    private Date createTime;
    /** 更新时间*/
    private Date updateTime;
    /**性别*/
    private boolean sex;

}
