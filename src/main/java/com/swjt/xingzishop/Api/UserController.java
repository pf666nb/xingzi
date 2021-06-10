package com.swjt.xingzishop.Api;

import com.swjt.xingzishop.Api.Param.User.UserRegisterParam;
import com.swjt.xingzishop.Bean.XzUser;
import com.swjt.xingzishop.Service.XZUserService;
import com.swjt.xingzishop.Utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * xingzishop
 * 关于用户的api
 *
 * @author : wpf
 * @date : 2021-06-09 12:56
 **/
@Slf4j
@RestController("user")
public class UserController {
    @Autowired
    XZUserService userService;
    @PostMapping("/register")
    public R UserRegister(@Validated @RequestBody UserRegisterParam param){
        log.info("用户{} | 请求注册",param.getUser_LoginName());
        userService.registerUser(new XzUser(param.getUser_LoginName(),param.getUser_PassWord()));

        return R.ok();
    }
}
