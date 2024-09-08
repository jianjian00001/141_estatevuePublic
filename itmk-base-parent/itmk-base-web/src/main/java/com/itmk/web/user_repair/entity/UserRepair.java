package com.itmk.web.user_repair.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("user_repair")
public class UserRepair implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long repairId;
    //报修人id
    private Long userId;
    //联系电话
    private String phone;
    //维修地址
    private String repairAddress;
    //维修内容
    private String repairContent;
    //报修时间
    private Date commitTime;
    //维修状态 0：未维修 1：已维修
    private String status;
}
