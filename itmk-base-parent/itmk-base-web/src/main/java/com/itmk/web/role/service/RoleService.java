package com.itmk.web.role.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.web.role.entity.*;

public interface RoleService extends IService<Role> {
    IPage<Role> list(RoleParm parm);
    //获取权限树回显
    RolePermissionVo getAssignTree(RoleAssignParm parm);
    void saveAssign(RolePermissionParm parm);
}
