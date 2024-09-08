import http from '@/utils/http'
//获取列表
export async function getListApi(parm){
    return await http.get("/api/userComplaint/list",parm)
}
export async function getComplateListApi(){
    return await http.get("/api/userComplaint/getComplateList")
}
//新增
export async function addApi(parm){
    return await http.post("/api/userComplaint",parm)
}
//编辑
export async function editApi(parm){
    return await http.put("/api/userComplaint",parm)
}
//删除
export async function deleteApi(parm){
    return await http.delete("/api/userComplaint",parm)
}
//获取我的投诉列表
export async function getMyListApi(parm){
    return await http.get("/api/userComplaint/myList",parm)
}