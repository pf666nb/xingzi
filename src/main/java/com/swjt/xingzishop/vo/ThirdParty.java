package com.swjt.xingzishop.vo;

import lombok.Data;

/**
 * branches
 * 第三方登录接口实体类
 *
 * @author : wpf
 * @date : 2020-11-02 17:49
 **/
@Data
public class ThirdParty {

    private String name;
    private String img;
    private Integer sex;
    private String uniq;
    private String from;
}
