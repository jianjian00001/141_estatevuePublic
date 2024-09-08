import http from '@/utils/http'
//获取栋数列表
export async function getListApi(parm){
    return await http.get("/api/HouseBuilding/list",parm);
}
//新增栋数
export async function addApi(parm){
    return await http.post("/api/HouseBuilding",parm)
}
//编辑
export async function editApi(parm){
    return await http.put("/api/HouseBuilding",parm)
}
//删除
export async function deleteApi(parm){
    return await http.delete("/api/HouseBuilding",parm)
}