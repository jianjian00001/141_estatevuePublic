package com.itmk.web.user_complaint.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.echarts.EchartsVo;
import com.itmk.web.user_complaint.entity.ComplaintVo;
import com.itmk.web.user_complaint.entity.UserComplaint;
import com.itmk.web.user_complaint.entity.UserComplaintParm;
import com.itmk.web.user_complaint.service.UserComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 投诉管理模块
 */
@RestController
@RequestMapping("/api/userComplaint")
public class UserComplaintController {
    @Autowired
    private UserComplaintService userComplaintService;

    /**
     * 投诉列表
     */

    @GetMapping("/list")
    public ResultVo getList(UserComplaintParm parm){
        //构造查询条件
        QueryWrapper<UserComplaint> query = new QueryWrapper<>();
        query.lambda().like(UserComplaint::getTitle,parm.getTitle())
                .like(UserComplaint::getComplaintContent,parm.getComplaintContent());
        //构造分页对象
        IPage<UserComplaint> page = new Page<>();
        page.setCurrent(parm.getCurrentPage());
        page.setSize(parm.getPageSize());
        IPage<UserComplaint> list = userComplaintService.page(page, query);
        return ResultUtils.success("查询成功",list);
    }

    @GetMapping("/getComplateList")
    public ResultVo getComplateList(){
        List<ComplaintVo> list = userComplaintService.getComplaint();
        List<String> names = new ArrayList<>();
        List<Integer> counts = new ArrayList<>();
        EchartsVo vo = new EchartsVo();
        if(list.size() > 0){
            for(int i=0;i<list.size();i++){
                names.add(list.get(i).getNames());
                counts.add(list.get(i).getValue());
            }
            vo.setCounts(counts);
            vo.setNames(names);
        }
        return ResultUtils.success("查询成功",vo);
    }
    /**
     * 我的投诉
     * @param parm
     * @return
     */
    @GetMapping("/myList")
    public ResultVo getMyList(UserComplaintParm parm){
        //构造查询条件
        QueryWrapper<UserComplaint> query = new QueryWrapper<>();
        query.lambda().like(UserComplaint::getTitle,parm.getTitle())
                .like(UserComplaint::getComplaintContent,parm.getComplaintContent())
        .eq(UserComplaint::getUserId,parm.getUserId());
        //构造分页对象
        IPage<UserComplaint> page = new Page<>();
        page.setCurrent(parm.getCurrentPage());
        page.setSize(parm.getPageSize());
        IPage<UserComplaint> list = userComplaintService.page(page, query);
        return ResultUtils.success("查询成功",list);
    }
    /**
     * 新增投诉
     */
    @PostMapping
    @PreAuthorize("hasAuthority('sys:myUserComplaint:add')")
    public ResultVo add(@RequestBody UserComplaint userComplaint){
        //设置投诉状态
        userComplaint.setStatus("0");
        //设置投诉时间
        userComplaint.setCreateTime(new Date());
        //入库保存
        boolean save = userComplaintService.save(userComplaint);
        if(save){
            return ResultUtils.success("投诉成功!");
        }
        return ResultUtils.error("投诉失败!");
    }

    /**
     * 编辑投诉
     */
    @PutMapping
    @PreAuthorize("hasAuthority('sys:myUserComplaint:edit')"+"|| hasAuthority('sys:myUserComplaint:do')")
    public ResultVo edit(@RequestBody UserComplaint userComplaint){
        //编辑保存
        boolean save = userComplaintService.updateById(userComplaint);
        if(save){
            return ResultUtils.success("编辑成功!");
        }
        return ResultUtils.error("编辑失败!");
    }

    /**
     * 删除
     * @param complaintId
     * @return
     */
    @PreAuthorize("hasAuthority('sys:myUserComplaint:delete')")
    @DeleteMapping("/{complaintId}")
    public ResultVo delete(@PathVariable("complaintId") Long complaintId){
        boolean save = userComplaintService.removeById(complaintId);
        if(save){
            return ResultUtils.success("删除成功!");
        }
        return ResultUtils.error("删除失败!");
    }
}
