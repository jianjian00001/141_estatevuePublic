package com.itmk.web.role.entity;

import lombok.Data;

@Data
public class RoleParm {
    //页容量
    private Long pageSize;
    //当前页
    private Long currentPage;
    //角色名称
    private String roleName;
}
