package com.swjt.xingzishop.utils;


import com.swjt.xingzishop.enums.ResultCode;
import com.swjt.xingzishop.vo.JsonResult;

/**
 * branches
 * 返回体构造类
 *
 * @author : wpf
 * @date : 2020-10-31 13:31
 **/
public class ResultTool {
    public static JsonResult success() {
        return new JsonResult(true);
    }

    public static <T> JsonResult<T> success(T data) {
        return new JsonResult(true, data);
    }

    public static JsonResult fail() {
        return new JsonResult(false);
    }

    public static JsonResult fail(ResultCode resultEnum) {
        return new JsonResult(false, resultEnum);
    }
}
