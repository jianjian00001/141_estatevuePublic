import http from '@/utils/http'
//获取列表
export async function getListApi(parm){
    return await http.get("/api/houseList/list",parm)
}
//新增
export async function addApi(parm){
    return await http.post("/api/houseList",parm)
}
//根据栋数id查询单元列表
export async function getUnitListByIdApi(parm){
    return await http.get("/api/houseUnit/getUnitListByBuildId",parm)
}
//查询栋数列表
export async function getBuildApi(){
    return await http.get("/api/HouseBuilding/unitList",null)
}
//编辑
export async function editApi(parm){
    return await http.put("/api/houseList",parm)
}
//删除
export async function deleteApi(parm){
    return await http.delete("/api/houseList",parm)
}