package com.swjt.xingzishop.Sercurity;

import com.alibaba.fastjson.JSON;

import com.swjt.xingzishop.enums.ResultCode;
import com.swjt.xingzishop.Utils.ResultTool;
import com.swjt.xingzishop.Vo.JsonResult;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * branches
 * 权限不足返回json
 *
 * @author : wpf
 * @date : 2020-11-03 22:23
 **/
@Component
public class JSONAuthenticationPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");

        //返回json数据
        JsonResult result = null;
        if (authException instanceof InsufficientAuthenticationException) {
            result = ResultTool.fail(ResultCode.USER_NOT_LOGIN);
        }
        //处理编码方式，防止中文乱码的情况
        response.setContentType("text/json;charset=utf-8");
        //塞到HttpServletResponse中返回给前台
        response.getWriter().write(JSON.toJSONString(result));
    }
}
