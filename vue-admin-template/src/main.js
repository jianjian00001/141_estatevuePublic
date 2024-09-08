import Vue from 'vue'
import 'default-passive-events'
import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import * as echarts from 'echarts';
// import locale from 'element-ui/lib/locale/lang/en' // lang i18n
//中文
import locale from 'element-ui/lib/locale/lang/zh-CN'
import '@/styles/index.scss' // global css
//清空表单
import resetForm from '@/utils/resetForm'
Vue.prototype.$resetForm = resetForm;
//信息提示框
import myconfirm from '@/utils/myconfirm'
Vue.prototype.$myconfirm = myconfirm;
//对象快速复制
import objCoppy from '@/utils/objCoppy'
Vue.prototype.$objCoppy = objCoppy;
Vue.prototype.$echarts = echarts
//按钮权限
//按钮权限判断
import hasPermission from '@/permission/index'
Vue.prototype.hasPerm = hasPermission;
import App from './App'
import store from './store'
import router from './router'

import '@/icons' // icon
import '@/permission' // permission control
//引入图标库
import '@/assets/icons/iconfont.css'

/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online ! ! !
 */
if (process.env.NODE_ENV === 'production') {
  const { mockXHR } = require('../mock')
  mockXHR()
}

// set ElementUI lang to EN
Vue.use(ElementUI, { locale })
// 如果想要中文版 element-ui，按如下方式声明
// Vue.use(ElementUI)

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
