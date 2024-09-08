package com.itmk.web.role_menu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data  //自动生成get和set方法
@TableName("sys_role_menu")  //指明RoleMenu实体对应的数据库表是 sys_role_menu
public class RoleMenu implements Serializable {
    @TableId(type = IdType.AUTO) //主键自动生成
    private Long roleMenuId;
    private Long roleId;
    private Long menuId;
}
