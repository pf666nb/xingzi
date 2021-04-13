package com.swjt.xingzishop.utils;

import com.simplestudio.fugitive.vo.ResultVO;

/**
 * fugitive
 * 返回的工具类
 *
 * @author : wpf
 * @date : 2020-10-26 05:22
 **/
public class ResultVOUtil {
    public static ResultVO success(Object object){
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        return resultVO;
    }
    public static ResultVO success(){
        return success(null);
    }
    public static ResultVO error(Integer code,String msg){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }


}
