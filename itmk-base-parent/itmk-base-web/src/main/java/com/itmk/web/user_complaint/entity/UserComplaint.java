package com.itmk.web.user_complaint.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("user_complaint")
public class UserComplaint implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long complaintId;
    //投诉人id
    private Long userId;
    //标题
    private String title;
    //投诉内容
    private String complaintContent;
    //投诉时间
    private Date createTime;
    //处理状态 0：未处理 1：已处理
    private String status;
}
