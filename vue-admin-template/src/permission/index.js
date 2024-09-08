//按钮权限的判断
export default function hasPermission(code) {
    let tag = false;
    //获取当前用户的所有权限
    let codeList = JSON.parse(sessionStorage.getItem("codeList"));
    for (let i = 0; i < codeList.length; i++) {
        if (codeList[i] === code) {
            tag = true;
            break;
        }
    }
    return tag;
}