package com.itmk.web.user_complaint.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.web.user_complaint.entity.ComplaintVo;
import com.itmk.web.user_complaint.entity.UserComplaint;

import java.util.List;

public interface UserComplaintService extends IService<UserComplaint> {
    List<ComplaintVo> getComplaint();
}
