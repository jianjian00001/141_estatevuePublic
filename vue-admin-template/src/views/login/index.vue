<template>
  <div class="logincontainer">
    <el-form
      class="loginForm"
      :model="loginForm"
      ref="loginForm"
      :rules="rules"
      label-width="80px"
      :inline="false"
      size="normal"
    >
      <el-form-item>
        <span class="loginTitle">小区物业管理</span>
      </el-form-item>
      <el-form-item prop="username">
        <el-input
          v-model="loginForm.username"
          placeholder="请输入用户名"
        ></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          type="password"
          v-model="loginForm.password"
          placeholder="请输入密码"
        ></el-input>
      </el-form-item>
      <el-form-item prop="userType">
        <el-radio-group v-model="loginForm.userType">
          <el-radio :label="0">业主</el-radio>
          <el-radio :label="1">物业</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item>
        <el-row :gutter="20">
          <el-col :span="12" :offset="0">
            <el-button type="primary" class="mybtn" @click="onSubmit"
              >登录</el-button
            >
          </el-col>
          <el-col :span="12" :offset="0">
            <el-button class="mybtn">取消</el-button>
          </el-col>
        </el-row>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      //登录表单绑定数据源
      loginForm: {
        username: "admin",
        password: "123456",
        userType: "" //0：业主  1： 物业
      },
      //表单验证规则
      rules: {
        username: [
          {
            trigger: "change",
            required: true,
            message: "请输入用户名"
          }
        ],
        password: [
          {
            trigger: "change",
            required: true,
            message: "请输入密码"
          }
        ],
        userType: [
          {
            trigger: "change",
            required: true,
            message: "请选择用户类型"
          }
        ]
      }
    };
  },
  methods: {
    //登录提交事件
    onSubmit() {
      //表单验证
      this.$refs.loginForm.validate(valid => {
        //验证通过才提交表单
        if (valid) {
          this.loading = true;
          //调用store里面的login方法
          this.$store
            .dispatch("user/login", this.loginForm)
            .then(() => {
              //跳转路由
              this.$router.push({ path: this.redirect || "/" });
              this.loading = false;
            })
            .catch(() => {
              this.loading = false;
            });
        }
      });
    }
  }
};
</script>

<style lang="scss" scoped>
.logincontainer {
  height: 100%;
  background: #fff;
  background-image: url("../../assets/images/login_bg.png");
  display: flex;
  align-items: center;
  justify-content: center;
}
.loginForm {
  height: 350px;
  width: 480px;
  background: #fff;
  padding: 35px 20px;
  border-radius: 10px;
}
.loginTitle {
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 24px;
  font-weight: 600;
  color: #409eff;
}
// ::v-deep  深度查找   !important 强制使用我们自己定义的样式
.logincontainer ::v-deep .el-form-item__content {
  margin-left: 0px !important;
}
.mybtn {
  width: 100%;
}
</style>
