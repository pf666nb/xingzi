package com.swjt.xingzishop.sercurity;

import com.alibaba.fastjson.JSON;
import com.swjt.xingzishop.enums.ResultCode;
import com.swjt.xingzishop.utils.ResultTool;
import com.swjt.xingzishop.vo.JsonResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * branches
 * 注销成功处理器
 *
 * @author : wpf
 * @date : 2020-11-04 09:13
 **/
@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {


    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");

        //返回json数据
        JsonResult result = null;
        result = ResultTool.fail(ResultCode.SUCCESS);

        //处理编码方式，防止中文乱码的情况
        response.setContentType("text/json;charset=utf-8");
        //塞到HttpServletResponse中返回给前台
        response.getWriter().write(JSON.toJSONString(result));
    }
}
