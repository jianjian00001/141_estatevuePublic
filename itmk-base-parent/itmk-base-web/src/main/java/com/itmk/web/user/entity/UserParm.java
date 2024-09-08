package com.itmk.web.user.entity;

import lombok.Data;

@Data
public class UserParm {
    //页容量
    private Long pageSize;
    //当前页
    private Long curentPage;
    //姓名
    private String loginName;
    //电话
    private String phone;
}
