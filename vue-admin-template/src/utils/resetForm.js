//表单清空
// formName: 表单的ref属性  obj表单的数据域
export default function resetForm(formName,obj){
    //清空表单
    if(this.$refs[formName]){
        this.$refs[formName].resetFields();
    }
    //清空数据域
    Object.keys(obj).forEach(key =>{
        obj[key] = '';
    })
}