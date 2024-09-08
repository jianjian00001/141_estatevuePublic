package com.itmk.web.user.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginResult implements Serializable {
    private Long userId;
    private String token;
    //token过期时间
    private Long expireTime;
}
