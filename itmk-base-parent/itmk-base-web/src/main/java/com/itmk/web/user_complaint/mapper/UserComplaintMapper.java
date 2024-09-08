package com.itmk.web.user_complaint.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itmk.web.user_complaint.entity.ComplaintVo;
import com.itmk.web.user_complaint.entity.UserComplaint;

import java.util.List;

public interface UserComplaintMapper extends BaseMapper<UserComplaint> {
    List<ComplaintVo> getComplaint();
}
