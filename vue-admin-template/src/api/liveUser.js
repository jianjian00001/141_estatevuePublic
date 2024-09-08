import http from '@/utils/http'
//新增
export async function addApi(parm){
    return await http.post("/api/liveUser",parm)
}
//获取角色列表
export async function getRoleListApi(){
    return await http.get("/api/role/getList",null)
}
//业主列表
export async function getListApi(parm){
    console.log(parm)
    return await http.get("/api/liveUser/list",parm)
}
//根据id查询用户
export async function getUserByIdApi(parm){
    return await http.get("/api/liveUser/getUserById",parm)
}
//编辑
export async function editApi(parm){
    return await http.put("/api/liveUser",parm)
}
//获取房屋列表
export async function getHouseListApi(parm){
    return await http.get("/api/houseList/list",parm)
}
//分配房屋保存
export async function assignSaveApi(parm){
    return await http.post("/api/liveUser/assignSave",parm)
}
//获取车位列表
export async function getParkListApi(parm){
    return await http.get("/api/parkList/list",parm)
}
//分配车位保存
export async function assignParkSaveApi(parm){
    return await http.post("/api/liveUser/assignParkSave",parm)
}
//退房
export async function returnHouseApi(parm){
    return await http.post("/api/liveUser/returnHose",parm)
}
//退车位
export async function returnParkApi(parm){
    return await http.post("/api/liveUser/returnPark",parm)
}
//删除
export async function deleteUserApi(parm){
    return await http.delete("/api/liveUser",parm)
}