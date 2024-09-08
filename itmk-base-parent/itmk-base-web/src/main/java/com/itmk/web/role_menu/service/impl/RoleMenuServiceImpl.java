package com.itmk.web.role_menu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmk.web.role_menu.entity.RoleMenu;
import com.itmk.web.role_menu.mapper.RoleMenuMapper;
import com.itmk.web.role_menu.service.RoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {
//    @Autowired
//    private RoleMenuMapper roleMenuMapper;

    @Override
    @Transactional
    public void saveRoleMenu(Long roleId, List<Long> menuIds) {
        //删除角色原来的权限
        QueryWrapper<RoleMenu> query = new QueryWrapper<>();
        query.lambda().eq(RoleMenu::getRoleId,roleId);
        this.baseMapper.delete(query);
        //保存新的权限
        this.baseMapper.saveRoleMenu(roleId,menuIds);
    }
}
