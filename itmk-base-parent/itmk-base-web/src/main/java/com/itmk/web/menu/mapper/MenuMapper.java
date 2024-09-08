package com.itmk.web.menu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itmk.web.menu.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {
    //根据用户id查询权限
    List<Menu> getMenuByUserId(@Param("userId") Long userId);
    //根据业主的id查询菜单
    List<Menu> getMenuByUserIdForLiveUser(@Param("userId") Long userId);
    //根据角色id查询原来的权限
    List<Menu> getMenuByRoleId(@Param("roleId") Long roleId);
 }
