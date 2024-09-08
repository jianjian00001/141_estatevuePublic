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
      <el-form-item>
        <el-button icon="el-icon-search" @click="searchBtn">查询</el-button>
        <el-button style="color: #ff7670" @click="resetBtn" icon="el-icon-close"
          >重置</el-button
        >
        <el-button
          v-if="hasPerm('sys:noticeList:add')"
          icon="el-icon-plus"
          type="primary"
          @click="addBtn"
          >新增</el-button
        >
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <el-table :height="tableHeight" :data="tableList" border stripe>
      <el-table-column label="标题" prop="title"></el-table-column>
      <el-table-column label="内容" prop="noticeContent"></el-table-column>
      <el-table-column label="时间" prop="createTime"></el-table-column>
      <el-table-column align="center" width="180" label="操作">
        <template slot-scope="scope">
          <el-button
            v-if="hasPerm('sys:noticeList:edit')"
            type="primary"
            icon="el-icon-edit"
            size="small"
            @click="editBtn(scope.row)"
            >编辑</el-button
          >
          <el-button
            v-if="hasPerm('sys:noticeList:delete')"
            type="danger"
            icon="el-icon-delete"
            size="small"
            @click="deleteBtn(scope.row)"
            >删除</el-button
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
    <!-- 弹框定义 -->
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
          <el-form-item prop="noticeContent" label="内容">
            <el-input type="textarea" v-model="addModel.noticeContent"></el-input>
          </el-form-item>
        </el-form>
      </template>
    </sys-dialog>
  </el-main>
</template>

<script>
import { getListApi, addApi, editApi, deleteApi } from "@/api/notice";
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
            message: "请填标题",
          },
        ],
        noticeContent: [
          {
            trigger: "change",
            required: true,
            message: "请填写公告内容",
          },
        ],
      },
      //表单绑定的数据域
      addModel: {
        editType: "",
        noticeId: "",
        userId: "",
        title: "",
        noticeContent: "",
      },
      //设置弹框属性
      addDialog: {
        title: "",
        height: 180,
        width: 620,
        visible: false,
      },
      //表格高度
      tableHeight: 0,
      //表格数据
      tableList: [],
      parms: {
        currentPage: 1,
        pageSize: 10,
        title: "",
        total: 0,
      },
    };
  },
  created() {
    this.getlList();
  },
  mounted() {
    this.$nextTick(() => {
      this.tableHeight = window.innerHeight - 210;
    });
  },
  methods: {
    //弹框确定事件
    onConfirm() {
      this.$refs.addForm.validate(async (valid) => {
        if (valid) {
          let res = null;
          if (this.addModel.editType == "0") {
            res = await addApi(this.addModel);
          } else {
            res = await editApi(this.addModel);
          }
          if (res && res.code == 200) {
            //刷新列表
            this.getlList();
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
    //页数改变时触发
    currentChange(val) {
      this.parms.currentPage = val;
      this.getlList();
    },
    //页容量改变时触发
    sizeChange(val) {
      this.parms.pageSize = val;
      this.getlList();
    },
    //删除按钮
    async deleteBtn(row) {
      //信息提示
      let confirm = await this.$myconfirm("确定删除该数据吗?");
      if (confirm) {
        let res = await deleteApi({ noticeId: row.noticeId });
        if (res && res.code == 200) {
          //刷新列表
          this.getlList();
          this.$message.success(res.msg);
        }
      }
    },
    //编辑按钮
    editBtn(row) {
      //清空表单
      this.$resetForm("addForm", this.addModel);
      //把当前要编辑的数据复制到表单数据域
      this.$objCoppy(row, this.addModel);
      //设置编辑属性
      this.addModel.editType = "1";
      //设置弹框属性
      this.addDialog.title = "编辑公告";
      this.addDialog.visible = true;
    },
    //新增按钮
    addBtn() {
      //清空表单
      this.$resetForm("addForm", this.addModel);
      //设置编辑属性
      this.addModel.editType = "0";
      //设置弹框属性
      this.addDialog.title = "新增公告";
      this.addDialog.visible = true;
    },
    //重置按钮
    resetBtn() {
      this.parms.title = "";
      this.getlList();
    },
    //查询按钮
    searchBtn() {
      this.getlList();
    },
    //获取列表
    async getlList() {
      let res = await getListApi(this.parms);
      if (res && res.code == 200) {
        //给表格赋值
        console.log(res);
        this.tableList = res.data.records;
        this.parms.total = res.data.total;
      }
    },
  },
};
</script>

<style lang="scss" scoped></style>
