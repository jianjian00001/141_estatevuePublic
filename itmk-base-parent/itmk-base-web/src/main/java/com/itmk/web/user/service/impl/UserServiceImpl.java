package com.itmk.web.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmk.web.user.entity.User;
import com.itmk.web.user.entity.UserParm;
import com.itmk.web.user.mapper.UserMapper;
import com.itmk.web.user.service.UserService;
import com.itmk.web.user_role.entity.UserRole;
import com.itmk.web.user_role.mapper.UserRoleMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 继承 ServiceImpl 的好处是 可以使用 Mybatis-Plus的通用CRUD方法，
 * implements UserService的原因，是如果 Mybatis-Plus的通用CRUD方法不够用，或者不符合开发需求的
 * 时候，需要扩展我们自己定义的方法
 * @Service 表示把该类交给spring 进行管理，我们就可以在其他类里面直接注入使用
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    public IPage<User> list(UserParm parm) {
        //构造分页对象
        IPage<User> page = new Page<>();
        page.setSize(parm.getPageSize());
        page.setCurrent(parm.getCurentPage());
        //构造查询条件
        QueryWrapper<User> query = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(parm.getPhone())){
            query.lambda().like(User::getPhone,parm.getPhone());
        }
        if(StringUtils.isNotEmpty(parm.getLoginName())){
            query.lambda().like(User::getLoginName,parm.getLoginName());
        }
        return this.baseMapper.selectPage(page,query);
    }

    @Override
    public UserRole getRoleByUserId(UserRole userRole) {
        QueryWrapper<UserRole> query = new QueryWrapper<>();
        query.lambda().eq(UserRole::getUserId,userRole.getUserId());
        return userRoleMapper.selectOne(query);
    }

    @Override
    @Transactional
    public void saveRole(UserRole userRole) {
        //先把原来的角色删除
        QueryWrapper<UserRole> query = new QueryWrapper<>();
        query.lambda().eq(UserRole::getUserId,userRole.getUserId());
        userRoleMapper.delete(query);
        //把新的角色保存
        userRoleMapper.insert(userRole);
    }

    @Override
    public User loadUser(String username) {
        //构造查询条件
        QueryWrapper<User> query = new QueryWrapper<>();
        query.lambda().eq(User::getUsername,username);
        return this.baseMapper.selectOne(query);
    }
}
