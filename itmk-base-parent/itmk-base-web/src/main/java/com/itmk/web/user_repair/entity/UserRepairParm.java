package com.itmk.web.user_repair.entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class UserRepairParm implements Serializable {
    //当前页
    private Long currentPage;
    //页容量
    private Long pageSize;
    //投诉人id
    private Long userId;
    //维修内容
    private String repairContent;
}
