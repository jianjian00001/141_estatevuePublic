package com.itmk.web.sys_notice.entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class SysNoticeParm implements Serializable {
    //当前页
    private Long currentPage;
    //页容量
    private Long pageSize;
    private String title;
}
