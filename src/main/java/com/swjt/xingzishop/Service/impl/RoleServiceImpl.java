package com.swjt.xingzishop.Service.impl;

import com.swjt.xingzishop.Bean.XzRole;
import com.swjt.xingzishop.Mapper.XzRoleMapper;
import com.swjt.xingzishop.Service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private XzRoleMapper xzRoleMapper;
    @Override
    public XzRole SelectByUserId(String UserId) {
        return xzRoleMapper.selectByUserId(UserId);
    }

    @Override
    public void SaveRole(XzRole xzRole) {
        xzRoleMapper.insert(xzRole);
    }

    @Override
    public int UpdateRole(XzRole xzRole) {
        return xzRoleMapper.updateByPrimaryKey(xzRole);
    }
}
