package com.itmk.web.role.entity;


import lombok.Data;

import java.io.Serializable;

@Data
public class RoleAssignParm implements Serializable {
    //用户id
    private Long userId;
    //角色id
    private Long roleId;
}