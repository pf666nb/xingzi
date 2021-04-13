package com.swjt.xingzishop.vo;


import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * branches
 * Handler返回的json
 *
 * @author : wpf
 * @date : 2020-10-26 18:49
 **/
@Data
public class SimpleResultVO {
    private Integer code;
    private String msg;


    public SimpleResultVO(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String toJSONString(Integer code, String msg) {
        return JSONObject.toJSONString(new SimpleResultVO(code, msg));
    }

}
