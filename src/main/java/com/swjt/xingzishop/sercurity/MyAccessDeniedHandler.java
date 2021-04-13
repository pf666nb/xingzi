package com.swjt.xingzishop.sercurity;

import com.alibaba.fastjson.JSON;
import com.swjt.xingzishop.enums.ResultCode;
import com.swjt.xingzishop.utils.ResultTool;
import com.swjt.xingzishop.vo.JsonResult;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * branches
 * 认证失败处理类，返回未授权
 *
 * @author : wpf
 * @date : 2020-11-04 08:47
 **/
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");

        //返回json数据
        JsonResult result = null;
        result = ResultTool.fail(ResultCode.NO_PERMISSION);

        //处理编码方式，防止中文乱码的情况
        response.setContentType("text/json;charset=utf-8");
        //塞到HttpServletResponse中返回给前台
        response.getWriter().write(JSON.toJSONString(result));
    }
}
