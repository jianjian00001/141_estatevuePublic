<template>
  <div class="dashboard-container">
    <el-row>
      <el-col :span="12">
        <div id="main" style="width: 500px; height: 450px"></div>
      </el-col>

      <el-col :span="12">
        <div id="pie" style="width: 500px; height: 400px"></div>
      </el-col>
    </el-row>
  </div>
</template>

<script>

import { mapGetters } from "vuex";

import { repairNumberHApi } from '@/api/repair'

export default {
  name: "Dashboard",
  computed: {
    ...mapGetters(["name"]),
  },
  data() {
    return {
      value: new Date()
    }
  },
  methods: {
    async repairNumberh() {
      var chartDom = document.getElementById('main');
      var myChart = this.$echarts.init(chartDom,);
      var option = {
        title: {
          text: '去年各季度维修数量统计',
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
    }
  },
  async mounted() {
    this.repairNumberh();
  }
};
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>

