package com.swjt.xingzishop.Sercurity;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * branches
 * 自定义
 *
 * @author : wpf
 * @date : 2020-10-29 20:46
 **/
@Component
public class MyAuthenticationProvider extends DaoAuthenticationProvider {
    public MyAuthenticationProvider(@Qualifier("myUserDetailsService") UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.setUserDetailsService(userDetailsService);
        this.setPasswordEncoder(passwordEncoder);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

        if (authentication.getDetails() instanceof MyWebAuthenticationDetails) {
            MyWebAuthenticationDetails details = (MyWebAuthenticationDetails) authentication.getDetails();
            String imageCode = details.getImageCode();

            String savedImageCode = details.getSavedImageCode();
            if (details.getImageCode() == "QQ_LOGIN_CODE") {
                imageCode = savedImageCode;
            }
            if (StringUtils.isEmpty(imageCode) || !imageCode.equals(savedImageCode) || StringUtils.isEmpty(savedImageCode)) {
//                throw new VerificationCodeException();
            }
            super.additionalAuthenticationChecks(userDetails, authentication);
        } else {

            super.additionalAuthenticationChecks(userDetails, authentication);
        }


    }
}
