import http from '@/utils/http'
//获取权限列表
export async function getMenuListApi(){
    return await http.get("/api/menu/list",null)
}
//新增权限
export async function addMenuApi(parm){
    return await http.post("/api/menu",parm)
}
//获取上级部门树
export async function getParentTreeApi(){
    return await http.get("/api/menu/parent")
}
//编辑权限
export async function editMenuApi(parm){
    return await http.put("/api/menu",parm)
}
//删除权限
export async function deleteMenuApi(parm){
    return await http.delete("/api/menu",parm)
}