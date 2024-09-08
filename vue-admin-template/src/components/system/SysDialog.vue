<template>
  <el-dialog
    append-to-body
    :title="title"
    :visible.sync="visible"
    :width="width + 'px'"
    :before-close="onClose"
  >
    <div class="container" :style="{ height: height + 'px' }">
      <slot name="content"></slot>
    </div>
    <span slot="footer">
      <el-button type="danger" @click="onClose">取消</el-button>
      <el-button type="primary" @click="onConfirm">确认</el-button>
    </span>
  </el-dialog>
</template>

<script>
export default {
  props: {
    title: {
      type: String,
      default: "标题",
    },
    visible: {
      type: Boolean,
      default: false,
    },
    width: {
      type: Number,
      default: 600,
    },
    height: {
      type: Number,
      default: 400,
    },
  },
  methods: {
    onClose() {
      this.$emit("onClose");
    },
    onConfirm() {
      this.$emit("onConfirm");
    },
  },
};
</script>

<style lang="scss" scoped>
.container {
  overflow-x: inherit;
  overflow-y: auto;
}
.el-dialog__wrapper {
  // z-index: 9999 !important;
  ::v-deep .el-dialog {
    border-top-left-radius: 7px;
    border-top-right-radius: 7px;
    .el-dialog__header {
      border-top-left-radius: 7px;
      border-top-right-radius: 7px;
      background-color: #1890ff;
      .el-dialog__title {
        color: #fff;
        font-size: 16px;
        font-weight: 600;
      }
      .el-dialog__close {
        color: #fff;
      }
    }
    .el-dialog__body {
      padding: 10px;
    }
    .el-dialog__footer {
      border-top: 1px solid #e8eaec !important;
      padding: 10px;
    }
  }
}
</style>
