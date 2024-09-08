package com.itmk.web.role.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
@Data //自动生成 get 和 set方法
@TableName("sys_role")  //指定实体对应的表 是哪一个
public class Role implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long roleId;
    //角色名称
    private String roleName;
    //备注
    private String remark;
}
