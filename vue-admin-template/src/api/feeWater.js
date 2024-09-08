import http from '@/utils/http'
//获取列表
export async function getListApi(parm){
    return await http.get("/api/feeWater/list",parm)
}
//根据单元id查询房屋列表
export async function getHouseByUnitIdApi(parm){
    return await http.get("/api/houseList/getHouseByUnitId",parm)
}
//新增
export async function addApi(parm){
    return await http.post("/api/feeWater",parm)
}
//编辑
export async function editApi(parm){
    return await http.put("/api/feeWater",parm)
}
//删除
export async function deleteApi(parm){
    return await http.delete("/api/feeWater",parm)
}
//缴费
export async function payApi(parm){
    return await http.post("/api/feeWater/payPower",parm)
}