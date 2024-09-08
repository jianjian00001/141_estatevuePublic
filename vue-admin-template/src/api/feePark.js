import http from '@/utils/http'
//获取列表
export async function getListApi(parm){
    return await http.get("/api/feePark/list",parm)
}
//查询车位列表
export async function getParkListApi(){
    return await http.get("/api/parkList/listNoPage",null)
}
//新增
export async function addApi(parm){
    return await http.post("/api/feePark",parm)
}
//编辑
export async function editApi(parm){
    return await http.put("/api/feePark",parm)
}
//删除
export async function deleteApi(parm){
    return await http.delete("/api/feePark",parm)
}
//缴费
export async function payApi(parm){
    return await http.post("/api/feePark/pay",parm)
}