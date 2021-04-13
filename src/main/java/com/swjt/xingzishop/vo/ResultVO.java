package com.swjt.xingzishop.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * fugitive
 * http请求最外层返回的对象
 *
 * @author : wpf
 * @date : 2020-10-26 05:18
 **/
@Data
@ApiModel(description = "返回的相应数据")
public class ResultVO<T> {
    /**错误码*/
    @ApiModelProperty(value = "状态码")
    private Integer code;
    /**错误信息*/
    @ApiModelProperty(value = "信息")
    private String msg;
    /**数据*/
    @ApiModelProperty(value = "返回数据")
    private T data;
}
