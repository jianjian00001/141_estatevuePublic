import request from '@/utils/request'
import http from '@/utils/http'
import {getUserId} from '@/utils/auth'
//获取员工列表
export async function getUserListApi(parm){
  return await http.get("/api/user/list",parm)
}
//新增员工
export async function addUserApi(parm){
  return await http.post("/api/user",parm)
}
//编辑员工
export async function editUserApi(parm){
  return await http.put("/api/user",parm);
}
//删除员工
export async function deleteUserApi(parm){
  return await http.delete("/api/user",parm);
}

//从自己的后端获取登录数据，返回token和用户id
export async function login(parm){
  console.log('登录参数')
  console.log(parm)
  return await http.post("/api/user/login",parm)
}
//获取用户信息
export async function getInfo(){
  let parm = {
    userId:getUserId()
  }
  return await http.get("/api/user/getInfo",parm)
}
//根据用户id获取角色
export async function getRoleByUserIdApi(parm){
  return await http.get("/api/user/getRoleByUserId",parm)
}
//保存用户角色
export async function assignSaveApi(parm){
  return await http.post("/api/user/saveRole",parm)
}
//获取菜单数据
export async function getMenuList(){
  return await http.get("/api/user/getMenuList",null)
}
//退出登录
export async function loginOutApi(){
  return await http.post("/api/user/loginOut",null)
}
//修改密码
export async function resetPasswordApi(parm){
  return await http.post("/api/user/resetPassword",parm)
}
// //用户登录，使用的是mock.js数据
// export function login(data) {
//   return request({
//     url: '/vue-admin-template/user/login',
//     method: 'post',
//     data
//   })
// }

// export function getInfo(token) {
//   return request({
//     url: '/vue-admin-template/user/info',
//     method: 'get',
//     params: { token }
//   })
// }

// export function logout() {
//   return request({
//     url: '/vue-admin-template/user/logout',
//     method: 'post'
//   })
// }
