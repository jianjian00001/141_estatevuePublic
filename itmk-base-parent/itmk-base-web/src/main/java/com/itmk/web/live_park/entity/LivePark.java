package com.itmk.web.live_park.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 租户和房屋的关系表
 */
@Data
@TableName("live_park")
public class LivePark implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long liveParkId;
    //业主id
    private Long userId;
    //车位id
    private Long parkId;
    //使用状态
    private String liveStatue;
}
