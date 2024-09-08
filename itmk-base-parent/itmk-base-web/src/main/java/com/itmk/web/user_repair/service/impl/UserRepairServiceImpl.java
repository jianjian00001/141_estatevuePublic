package com.itmk.web.user_repair.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmk.web.user_repair.entity.UserRepair;
import com.itmk.web.user_repair.mapper.UserRepairMapper;
import com.itmk.web.user_repair.service.UserRepairService;
import org.springframework.stereotype.Service;

@Service
public class UserRepairServiceImpl extends ServiceImpl<UserRepairMapper, UserRepair> implements UserRepairService {
}
