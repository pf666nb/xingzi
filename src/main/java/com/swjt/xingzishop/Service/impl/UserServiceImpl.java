package com.swjt.xingzishop.Service.impl;

import com.swjt.xingzishop.Bean.XzUser;
import com.swjt.xingzishop.Mapper.XzUserMapper;
import com.swjt.xingzishop.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements UserService {
    @Autowired
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
    public void findUser(String userId) {
        xzUserMapper.selectByPrimaryKey(userId);
    }
}
