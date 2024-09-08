package com.itmk.web.role_menu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.web.role_menu.entity.RoleMenu;

import java.util.List;

public interface RoleMenuService extends IService<RoleMenu> {
    //保存权限
    void saveRoleMenu(Long roleId, List<Long> menuIds);
}
