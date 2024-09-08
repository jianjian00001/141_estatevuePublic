<template>
  <el-main>
    <!-- 管理员统计 -->
    <el-row
      :gutter="20"
      type="flex"
      class="row-bg"
      justify="center"
      style="margin-bottom: 80px"
    >
      <el-col :span="6">
        <div class="show-header">
          <div class="show-num">{{ total.userCount }}</div>
          <div class="bottom-text">业主总数</div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="show-header" style="background: rgb(255, 153, 0)">
          <div class="show-num">{{ total.houseCount }}</div>
          <div class="bottom-text">房屋总数</div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="show-header" style="background: rgb(45, 183, 245)">
          <div class="show-num">{{ total.carCount }}</div>
          <div class="bottom-text">车位总数</div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="show-header" style="background: rgb(237, 64, 20)">
          <div class="show-num">{{ total.repairCount }}</div>
          <div class="bottom-text">待维修总数</div>
        </div>
      </el-col>
    </el-row>
    <div style="display: flex">
      <div id="main" style="width: 400px; height: 300px; flex-grow: 1"></div>
      <div id="main2" style="width: 400px; height: 300px; flex-grow: 1"></div>
    </div>
    <el-card class="box-card" style="margin-top:30px;">
      <div slot="header" class="clearfix">
        <span style="color:#000000;font-weight:600;">公告列表</span>
      </div>
      <div v-for="o in notice" :key="o.noticeId" class="text item">
        <div style="display: flex;">
          <div style="font-size: 15px;font-weight: bold;">{{ o.title }}</div>
          <div style="font-size: 14px;margin-left: 20px;">{{ o.noticeContent }}</div>
          <div style="font-size: 14px;margin-left: 20px;">{{ o.createTime }}</div>
        </div>
        <el-divider></el-divider>
      </div>
    </el-card>
  </el-main>
</template>

<script>

import { mapGetters } from "vuex";
import { repairNumberHApi,getTotalApi } from '@/api/repair'
import { getComplateListApi } from '@/api/userComplaint'
import { getTopListApi } from '@/api/notice'

export default {
  name: "Dashboard",
  computed: {
    ...mapGetters(["name"]),
  },
  data() {
    return {
      value: new Date(),
      notice:[],
      total:{
        carCount:0,
        houseCount:0,
        repairCount:0,
        userCount:0
      }
    }
  },
  methods: {
    //首页统计
    async getTotal(){
      let res = await getTotalApi()
      if(res && res.code == 200){
        console.log(res)
        Object.assign(this.total,res.data)
        console.log('总数')
        console.log(this.total)
      }
    },
    //获取通知
    async getNotice(){
      let res = await getTopListApi()
      if(res && res.code == 200){
        this.notice = res.data;
      }
    },
    async repairNumberh() {
      var chartDom = document.getElementById('main');
      var myChart = this.$echarts.init(chartDom,);
      var option = {
        title: {
          text: '各季度维修数量统计',
          subtext: '趋势图',
          left: 'center'
        },
        xAxis: {
          type: 'category',
          data: ['第一季度', '第二季度', '第三季度', '第四季度']
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: [],
            type: 'bar'
          }
        ]
      };
      let res = await repairNumberHApi();
      if (res && res.code == 200) {
        option.series[0].data = res.data;
      }
      myChart.setOption(option);
    },
    async myecharts1() {
      var myChart = this.$echarts.init(document.getElementById("main2"));
      // 指定图表的配置项和数据
      var option = {
        title: {
          text: "每月投诉统计",
        },
        tooltip: {},
        legend: {
          data: ["投诉统计"],
        },
        xAxis: {
          data: [],
        },
        yAxis: {},
        series: [
          {
            name: "投诉统计",
            type: "bar",
            data: [5, 20, 36, 10, 10, 20],
          },
        ],
      };
      let res = await getComplateListApi()
      if(res && res.code == 200){
        option.xAxis.data = res.data.names
        option.series[0].data = res.data.counts

      }
      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
    }
  },
  async mounted() {
    this.repairNumberh();
    this.myecharts1();
    this.getNotice()
    this.getTotal()
  }
};
</script>

<style lang="scss" scoped>
.bottom-text {
  bottom: 0;
  width: 100%;
  background: rgba(0, 0, 0, 0.1);
  height: 25px;
  line-height: 25px;
  text-align: center;
  position: absolute;
  font-weight: 600;
}
.show-header {
  background: #00c0ef;
  color: #fff;
  height: 80px;
  border-radius: 5px;
  position: relative;
}
.show-num {
  font-size: 38px;
  font-weight: 600;
  padding: 5px;
}
</style>

