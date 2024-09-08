package com.itmk.web.user_complaint.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserComplaintParm implements Serializable {
    //当前页
    private Long currentPage;
    //页容量
    private Long pageSize;
    //投诉人id
    private Long userId;
    //标题
    private String title;
    //内容
    private String complaintContent;
}
