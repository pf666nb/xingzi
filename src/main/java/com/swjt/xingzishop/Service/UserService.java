package com.swjt.xingzishop.Service;

import com.swjt.xingzishop.Bean.XzUser;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void save(XzUser user);
    void delUser(XzUser user);
    void updateUser(XzUser user);
    void findUser(String userId);
}