package com.swjt.xingzishop.Service.impl;

import com.swjt.xingzishop.Bean.XzRole;
import com.swjt.xingzishop.Mapper.XzRoleMapper;
import com.swjt.xingzishop.SpringTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.annotation.Resource;


@FixMethodOrder(MethodSorters.JVM)
public class RoleServiceImplTest extends SpringTest {
    @Resource
    private XzRoleMapper xzRoleMapper;

    @Test
    public void saveRole() {
        XzRole xzRole = new XzRole();
        xzRole.setRoleId(123);
        xzRole.setUserId(123+"");
        xzRole.setUserRole("USER");
        xzRoleMapper.insert(xzRole);
    }

    @Test
    public void selectByUserId() {

        XzRole xzRole = xzRoleMapper.selectByUserId(123+"");
        System.out.println(xzRole.toString());


    }



    @Test
    public void updateRole() {
        XzRole xzRole = new XzRole();
        xzRole.setRoleId(123);
        xzRole.setUserId(123+"");
        xzRole.setUserRole("ADMIN");
        xzRoleMapper.updateByPrimaryKey(xzRole);
    }
}
