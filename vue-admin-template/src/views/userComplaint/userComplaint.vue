<template>
  <el-main>
    <!-- 搜索框 -->
    <el-form
      :model="parms"
      ref="searchForm"
      label-width="80px"
      :inline="true"
      size="small"
    >
      <el-form-item label="标题">
        <el-input v-model="parms.title"></el-input>
      </el-form-item>
      <el-form-item label="内容">
        <el-input v-model="parms.complaintContent"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button icon="el-icon-search" @click="searchBtn">查询</el-button>
        <el-button style="color: #ff7670" @click="resetBtn" icon="el-icon-close"
          >重置</el-button
        >
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <el-table :height="tableHeight" :data="tableList" border stripe>
      <el-table-column label="标题" prop="title"></el-table-column>
      <el-table-column label="内容" prop="complaintContent"></el-table-column>
      <el-table-column label="处理状态" prop="status">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status == '1'" type="success" size="small"
            >已处理</el-tag
          >
          <el-tag v-if="scope.row.status == '0'" type="danger" size="small"
            >未处理</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column align="center" width="120" label="操作">
        <template slot-scope="scope">
          <el-button
            v-if="hasPerm('sys:myUserComplaint:do')"
            type="success"
            icon="el-icon-delete"
            size="small"
            @click="doBtn(scope.row)"
            >处理</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <el-pagination
      @size-change="sizeChange"
      @current-change="currentChange"
      :current-page.sync="parms.currentPage"
      :page-sizes="[10, 20, 40, 80, 100]"
      :page-size="parms.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="parms.total"
      background
    >
    </el-pagination>
    <!-- 新增弹框 -->
    <sys-dialog
      :title="addDialog.title"
      :height="addDialog.height"
      :width="addDialog.width"
      :visible="addDialog.visible"
      @onClose="onClose"
      @onConfirm="onConfirm"
    >
      <template slot="content">
        <el-form
          :model="addModel"
          ref="addForm"
          :rules="rules"
          label-width="80px"
          :inline="false"
          size="small"
        >
          <el-form-item prop="title" label="标题">
            <el-input v-model="addModel.title"></el-input>
          </el-form-item>
          <el-form-item prop="complaintContent" label="投诉内容">
            <el-input type="textarea" v-model="addModel.complaintContent"></el-input>
          </el-form-item>
        </el-form>
      </template>
    </sys-dialog>
  </el-main>
</template>

<script>
import { getListApi, addApi, editApi, deleteApi } from "@/api/userComplaint";
import { getUserId } from "@/utils/auth";
import SysDialog from "@/components/system/SysDialog.vue";
export default {
  components: {
    SysDialog,
  },
  data() {
    return {
      //表单验证规则
      rules: {
        title: [
          {
            trigger: "change",
            required: true,
            message: "请填写标题",
          },
        ],
        complaintContent: [
          {
            trigger: "change",
            required: true,
            message: "请填写投诉内容",
          },
        ],
      },
      //新增投诉绑定对象
      addModel: {
        complaintId: "",
        userId: "",
        editType: "",
        title: "",
        complaintContent: "",
      },
      //定义弹框属性
      addDialog: {
        title: "",
        height: 180,
        width: 650,
        visible: false,
      },
      //表格高度
      tableHeight: 0,
      //表格数据
      tableList: [],
      //列表查询参数
      parms: {
        currentPage: 1,
        pageSize: 10,
        title: "",
        complaintContent: "",
        total: 0,
      },
    };
  },
  created() {
    this.getList();
  },
  mounted() {
    this.$nextTick(() => {
      this.tableHeight = window.innerHeight - 210;
    });
  },
  methods: {
    //弹框确认事件
    onConfirm() {
      this.$refs.addForm.validate(async (valid) => {
        if (valid) {
          this.addModel.userId = getUserId();
          let res = null;
          if (this.addModel.editType == "0") {
            res = await addApi(this.addModel);
          } else {
            res = await editApi(this.addModel);
          }
          if (res && res.code == 200) {
            //刷新列表
            this.getList();
            this.$message.success(res.msg);
            this.addDialog.visible = false;
          }
        }
      });
    },
    //弹框关闭
    onClose() {
      this.addDialog.visible = false;
    },
    //处理按钮
    async doBtn(row) {
      if (row.status == "1") {
        this.$message.warning("该投诉已经处理，无需重复处理！");
        return;
      }
      let parm = {
        complaintId: row.complaintId,
        status: "1",
      };
      const confirm = await this.$myconfirm("确定处理该投诉吗?");
      if (confirm) {
        let res = await editApi(parm);
        if (res && res.code == 200) {
          //刷新表格
          this.getList();
          this.$message.success(res.msg);
        }
      }
    },

    //重置按钮
    resetBtn() {
      this.parms.title = "";
      this.parms.complaintContent = "";
      this.getList();
    },
    //搜索按钮
    searchBtn() {
      this.getList();
    },
    //页数改变时触发
    currentChange(val) {
      this.parms.currentPage = val;
      this.getList();
    },
    //页容量改变时触发
    sizeChange(val) {
      this.parms.pageSize = val;
      this.getList();
    },

    //获取列表
    async getList() {
      let res = await getListApi(this.parms);
      if (res && res.code == 200) {
        console.log(res);
        //把返回的数据放到表格
        this.tableList = res.data.records;
        this.parms.total = res.data.total;
      }
    },
  },
};
</script>

<style lang="scss" scoped></style>
