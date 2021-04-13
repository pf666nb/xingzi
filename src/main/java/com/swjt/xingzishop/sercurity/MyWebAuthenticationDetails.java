package com.swjt.xingzishop.sercurity;


import com.swjt.xingzishop.enums.RandCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * branches
 * 拓展验证码类
 *
 * @author : wpf
 * @date : 2020-10-29 20:48
 **/
@Getter
@Setter
public class MyWebAuthenticationDetails extends WebAuthenticationDetails {
    private String imageCode;
    private String savedImageCode;


    public MyWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        this.imageCode = request.getParameter(RandCode.CAPTCHA.getType());
        HttpSession session = request.getSession();
        this.savedImageCode = (String) session.getAttribute(RandCode.CAPTCHA.getType());
        if (StringUtils.isEmpty(this.savedImageCode)) {
            session.removeAttribute(RandCode.CAPTCHA.getType());
        }
    }
}
