package com.swjt.xingzishop.Sercurity;

import com.alibaba.fastjson.JSON;
import com.swjt.xingzishop.Bean.XzUser;

import com.swjt.xingzishop.Service.XZUserService;
import com.swjt.xingzishop.Utils.ResultTool;
import com.swjt.xingzishop.Vo.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * fugitive
 * 成功登录的逻辑处理
 *
 * @author : wpf
 * @date : 2020-10-26 02:52
 **/
@Component
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    public static AuthSuccessHandler authSuccessHandler;
    @Autowired
    private XZUserService userService;

    @PostConstruct
    public void init() {
        authSuccessHandler = this;
        authSuccessHandler.userService = this.userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");

        XzUser user = authSuccessHandler.userService.findUserByLoginName(authentication.getName());
        //返回json数据
        JsonResult result = ResultTool.success();
        //处理编码方式，防止中文乱码的情况
        response.setContentType("text/json;charset=utf-8");
        //塞到HttpServletResponse中返回给前台
        response.getWriter().write(JSON.toJSONString(result));

    }
}
