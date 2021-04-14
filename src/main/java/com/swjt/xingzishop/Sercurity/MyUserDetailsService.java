package com.swjt.xingzishop.Sercurity;


import com.swjt.xingzishop.Bean.XzUser;
import com.swjt.xingzishop.Mapper.XzRoleMapper;
import com.swjt.xingzishop.Mapper.XzUserMapper;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * fugitive
 * 权限类型转换
 *
 * @author : wpf
 * @date : 2020-10-26 00:56
 **/
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Resource
    private XzUserMapper userMapper;

    @Resource
    private XzRoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String accountNumber) throws UsernameNotFoundException {
        XzUser user = userMapper.selectByLoginName(accountNumber);
        if (user == null){throw new UsernameNotFoundException("用户不存在");}

        user.setAuthorities(generateAuthorities(roleMapper.selectByUserId(user.getUserId()).getUserRole()));
        return user;
    }
    private List<GrantedAuthority> generateAuthorities(String roles){
        List<GrantedAuthority> authorities = new ArrayList<>();
        String[] roleArray = roles.split(";");
        if (!"".equals(roles)){
            for (String role : roleArray) {
                authorities.add(new SimpleGrantedAuthority(role));
            }

        }
        return authorities;
    }
}
