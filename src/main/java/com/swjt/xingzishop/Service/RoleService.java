package com.swjt.xingzishop.Service;

import com.swjt.xingzishop.Bean.XzRole;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


public interface RoleService {

    XzRole SelectByUserId(String UserId);

    void SaveRole(XzRole xzRole);

    int UpdateRole(XzRole xzRole);

    //默认不提供删除方法---不允许物理删除

}
