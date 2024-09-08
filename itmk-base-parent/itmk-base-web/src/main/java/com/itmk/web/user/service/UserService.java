package com.itmk.web.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.web.user.entity.User;
import com.itmk.web.user.entity.UserParm;
import com.itmk.web.user_role.entity.UserRole;

public interface UserService extends IService<User> {
    //查询用户列表
    IPage<User> list(UserParm parm);
    //根据用户id查询角色
    UserRole getRoleByUserId(UserRole userRole);
    //保存用户角色
    void saveRole(UserRole userRole);
    //根据登录名查用户信息
    User loadUser(String username);
}
