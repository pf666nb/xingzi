package com.swjt.xingzishop.Service;

import com.swjt.xingzishop.Bean.XzUser;
import com.swjt.xingzishop.Mapper.XzUserMapper;
import com.swjt.xingzishop.SpringTest;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;


import javax.annotation.Resource;
import java.util.Date;

@FixMethodOrder(MethodSorters.JVM)
public class UserServiceTest extends SpringTest {
    @Resource
    private XzUserMapper mapper;
    @Test
    public void save() {
        XzUser xzUser = new XzUser();
        xzUser.setUserId("qwertyuiopasd");
        xzUser.setUserCreatetime(new Date());
        xzUser.setUserUpdatetime(new Date());
        xzUser.setUserLoginname("123456789");
        xzUser.setUserPassword("123456789");
        xzUser.setUserName("wpf");
        xzUser.setUserIsban(true);
        mapper.insert(xzUser);

    }
    @Test
    public void findUser() {
        XzUser xzUser = mapper.selectByPrimaryKey("qwertyuiopasd");
        System.out.println(xzUser.toString());

    }
    @Test
    public void updateUser() {
        XzUser xzUser = new XzUser();
        xzUser.setUserId("qwertyuiopasd");
        xzUser.setUserCreatetime(new Date());
        xzUser.setUserUpdatetime(new Date());
        xzUser.setUserLoginname("123456789");
        xzUser.setUserPassword("123456789");
        xzUser.setUserName("zhy");
        xzUser.setUserIsban(true);
        int i = mapper.updateByPrimaryKey(xzUser);
        System.out.println(i);

    }

    @Test
    public void delUser() {
        mapper.deleteByPrimaryKey("qwertyuiopasd");

    }




}
