package com.itmk.web.echarts;

import com.itmk.utils.ResultUtils;

import com.itmk.utils.ResultVo;
import com.itmk.web.live_house.service.LiveHouseService;
import com.itmk.web.live_park.mapper.LiveParkMapper;
import com.itmk.web.live_park.service.LiveParkService;
import com.itmk.web.live_user.service.LiveUserService;
import com.itmk.web.user_repair.entity.UserRepair;
import com.itmk.web.user_repair.service.UserRepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;

import javax.annotation.Resource;
import java.util.Date;

import java.util.List;




@RestController
@RequestMapping("/api/echarts")
public class EchartsController {

    @Autowired
    private UserRepairService userRepairService;
    @Autowired
    private LiveUserService liveUserService;
    @Autowired
    private LiveHouseService liveHouseService;
    @Resource
    private LiveParkService liveParkService;

    @GetMapping("/getTotal")
    public ResultVo getTotal() {
        TotalVo vo = new TotalVo();
        int repairCount = userRepairService.count();
        int housecount = liveHouseService.count();
        int usercount = liveUserService.count();
        int parkcount = liveParkService.count();
        vo.setCarCount(parkcount);
        vo.setHouseCount(housecount);
        vo.setRepairCount(repairCount);
        vo.setUserCount(usercount);
        return ResultUtils.success("成功",vo);
    }

    @GetMapping("/member")
    public ResultVo members() {
        List<UserRepair> list = userRepairService.list();
        int q1 = 0; // 第一季度
        int q2 = 0; // 第二季度
        int q3 = 0; // 第三季度
        int q4 = 0; // 第四季度
        for (UserRepair user : list) {
            Date createTime = user.getCommitTime();
            Quarter quarter = DateUtil.quarterEnum(createTime);
            switch (quarter) {
                case Q1: q1 += 1; break;
                case Q2: q2 += 1; break;
                case Q3: q3 += 1; break;
                case Q4: q4 += 1; break;
                default: break;
            }
        }
        return ResultUtils.success("成功",CollUtil.newArrayList(q1, q2, q3, q4));
    }

}
