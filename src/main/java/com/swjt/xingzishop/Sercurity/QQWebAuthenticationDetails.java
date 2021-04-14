package com.swjt.xingzishop.Sercurity;


import com.swjt.xingzishop.enums.RandCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * branches
 * 通过QQ登录的Details
 *
 * @author : wpf
 * @date : 2020-11-03 17:40
 **/
@Getter
@Setter
public class QQWebAuthenticationDetails extends WebAuthenticationDetails {
    private String imageCode;
    private String savedImageCode;


    public QQWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        this.imageCode = "QQ_LOGIN_CODE";
        HttpSession session = request.getSession();
        this.savedImageCode = (String) session.getAttribute(RandCode.CAPTCHA.getType());
        if (StringUtils.isEmpty(this.savedImageCode)) {
            session.removeAttribute(RandCode.CAPTCHA.getType());
        }
    }
}
