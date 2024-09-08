package com.itmk.web.user.entity;


import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfo implements Serializable {
    private Long id;
    //用户名
    private String name;
    //头像
    private String avatar;
    //介绍
    private String introduction;
    //权限集合
    private Object[] roles;
}