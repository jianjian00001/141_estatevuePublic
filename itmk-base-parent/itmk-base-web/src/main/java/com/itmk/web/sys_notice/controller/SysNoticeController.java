package com.itmk.web.sys_notice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.sys_notice.entity.SysNotice;
import com.itmk.web.sys_notice.entity.SysNoticeParm;
import com.itmk.web.sys_notice.service.SysNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 公告管理模块
 */
@RestController
@RequestMapping("/api/sysNotice")
public class SysNoticeController {
    @Autowired
    private SysNoticeService sysNoticeService;

    /**
     * 查询列表
     */
    @GetMapping("/list")
    public ResultVo getList(SysNoticeParm parm){
        //构造查询条件
        QueryWrapper<SysNotice> query = new QueryWrapper<>();
        query.lambda().like(SysNotice::getTitle,parm.getTitle())
                .orderByDesc(SysNotice::getCreateTime);
        //构造分页对象
        IPage<SysNotice> page = new Page<>();
        page.setCurrent(parm.getCurrentPage());
        page.setSize(parm.getPageSize());
        IPage<SysNotice> list = sysNoticeService.page(page, query);
        return ResultUtils.success("查询成功",list);
    }

    /**
     * 新增
     */
    @PostMapping
    @PreAuthorize("hasAuthority('sys:noticeList:add')")
    public ResultVo add(@RequestBody SysNotice sysNotice){
        sysNotice.setCreateTime(new Date());
        boolean b = sysNoticeService.save(sysNotice);
        if(b){
            return ResultUtils.success("新增成功!");
        }
        return ResultUtils.error("新增失败!");
    }

    /**
     * 编辑
     */
    @PutMapping
    @PreAuthorize("hasAuthority('sys:noticeList:edit')")
    public ResultVo edit(@RequestBody SysNotice sysNotice){
        boolean b = sysNoticeService.updateById(sysNotice);
        if(b){
            return ResultUtils.success("编辑成功!");
        }
        return ResultUtils.error("编辑失败!");
    }

    /**
     * 删除
     */
    @PreAuthorize("hasAuthority('sys:noticeList:delete')")
    @DeleteMapping("/{noticeId}")
    public ResultVo delete(@PathVariable("noticeId") Long noticeId){
        boolean b = sysNoticeService.removeById(noticeId);
        if(b){
            return ResultUtils.success("删除成功!");
        }
        return ResultUtils.error("删除失败!");
    }

    @GetMapping("/getTopList")
    public ResultVo getTopList(){
        QueryWrapper<SysNotice> query = new QueryWrapper<>();
        query.lambda().orderByDesc(SysNotice::getCreateTime).last("limit 3");
        List<SysNotice> list = sysNoticeService.list(query);
        return ResultUtils.success("查询成功",list);
    }
}
