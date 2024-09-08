import http from '@/utils/http'
//我的电费列表
export async function getMyPowerFeeApi(parm){
    return await http.get("/api/feePower/getMyPowerFee",parm)
}
//我的水费列表
export async function getMyWaterFeeApi(parm){
    return await http.get("/api/feeWater/getMyWaterFee",parm)
}
//我的停车费列表
export async function getMyParkFeeApi(parm){
    return await http.get("/api/feePark/getMyParkFee",parm)
}