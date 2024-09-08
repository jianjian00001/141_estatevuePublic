package com.itmk.web.menu.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data  //自动生成get和set方法
@TableName("sys_menu") // 指明menu实体对应的表是 sys_menu
public class Menu implements Serializable {
    //主键
    @TableId(type = IdType.AUTO)
    private Long menuId;
    //上级菜单id
    private Long parentId;
    //菜单名称
    private String menuLabel;
    //权限字段
    private String menuCode;
    //路由名称
    private String name;
    //路由地址
    private String path;
    //组件路径
    private String url;
    //菜单类型 0：目录 1：菜单 2：按钮
    private String type;
    //图标
    private String icon;
    //备注
    private String remark;
    //上级菜单名称
    private String parentName;
    //序号
    private Integer orderNum;
    //不属于数据库表中的字段，需要排除
    @TableField(exist = false)
    private List<Menu> children = new ArrayList<>();
    //不属于数据库表中的字段，需要排除
    @TableField(exist = false)
     private Boolean open;
}