package com.swjt.xingzishop.Service.impl;

import com.swjt.xingzishop.Bean.XzUser;
import com.swjt.xingzishop.Mapper.XzUserMapper;
import com.swjt.xingzishop.Service.XZUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;


@Service
public class UserServiceImpl implements XZUserService {


    @Lazy
    @Autowired
    PasswordEncoder passwordEncoder;

    @Resource
    private XzUserMapper xzUserMapper;

    @Override
    public void save(XzUser user) {
        xzUserMapper.insert(user);
    }

    @Override
    public void delUser(XzUser user) {
        xzUserMapper.deleteByPrimaryKey(user.getUserId());
    }

    @Override
    public void updateUser(XzUser user) {
        xzUserMapper.updateByPrimaryKey(user);
    }

    @Override
    public XzUser findUser(String userId) {
        return xzUserMapper.selectByPrimaryKey(userId);
    }

    @Override
    public XzUser findUserByLoginName(String LoginName) {
        return xzUserMapper.selectByLoginName(LoginName);
    }

    @Override
    public boolean registerUser(XzUser user) {
        XzUser xzUser = xzUserMapper.selectByLoginName(user.getUserLoginname());
        if (xzUser!=null){
            return false;
        }else {
           user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
           xzUserMapper.insert(user);
        }

        return true;
    }
}
