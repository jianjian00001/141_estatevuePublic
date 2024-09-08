## 本项目完整源码是收费的  接毕业设计和论文

### 作者QQ：3642795578 微信：grapro666 (支持修改、 部署调试、 支持代做毕设)

### 接网站建设、小程序、H5、APP、各种系统等

**博客地址：
[https://blog.csdn.net/2303_76227485/article/details/138865894](https://blog.csdn.net/2303_76227485/article/details/138865894)**

**视频演示：
[https://www.bilibili.com/video/BV1Ls421P7sL/](https://www.bilibili.com/video/BV1Ls421P7sL/)**

**毕业设计所有选题地址：
[https://github.com/ynwynw/allProject](https://github.com/ynwynw/allProject)**

## 基于Java+Springboot+Vue的物业管理系统(源代码+数据库)141

## 一、系统介绍
本项目前后端分离，分为管理员、员工、用户三种角色(角色权限可自行分配)

### 1、用户：
- 登录、水费、电费、停车费、投诉管理、维修管理、公告查看、修改密码
### 2、员工：
- 电费管理、水费管理、停车费管理、投诉管理、维修管理、公告管理、统计分析
### 3、管理员：
- 员工所有功能、员工管理、角色管理、权限管理、楼栋管理、单元管理、房屋管理、车位管理、业主管理、分配房屋、分配车位

## 二、所用技术
后端技术栈：

- Springboot
- MybatisPlus
- Jwt
- SpringSecurity
- Mysql
- Maven

前端技术栈：

- Vue
- Vue-router 
- Vuex
- axios 
- elementui

## 三、环境介绍

基础环境:IDEA/eclipse, JDK1.8, Mysql5.7或以上, Maven3.6, node14, navicat, vscode

所有项目以及源代码本人均调试运行无问题 可支持远程调试运行

## 四、页面截图
### 1、用户
![contents](./picture/picture1.png)
![contents](./picture/picture2.png)
![contents](./picture/picture3.png)
![contents](./picture/picture4.png)
![contents](./picture/picture5.png)
![contents](./picture/picture6.png)
![contents](./picture/picture7.png)
![contents](./picture/picture8.png)
![contents](./picture/picture9.png)
### 2、员工：
![contents](./picture/picture10.png)
![contents](./picture/picture11.png)
![contents](./picture/picture12.png)
![contents](./picture/picture13.png)
![contents](./picture/picture14.png)
![contents](./picture/picture15.png)
![contents](./picture/picture16.png)
### 3、管理员：
![contents](./picture/picture17.png)
![contents](./picture/picture18.png)
![contents](./picture/picture19.png)
![contents](./picture/picture20.png)
![contents](./picture/picture21.png)
![contents](./picture/picture22.png)
![contents](./picture/picture23.png)
![contents](./picture/picture24.png)
![contents](./picture/picture25.png)
![contents](./picture/picture26.png)
![contents](./picture/picture27.png)
![contents](./picture/picture28.png)
![contents](./picture/picture29.png)
![contents](./picture/picture30.png)
![contents](./picture/picture31.png)
![contents](./picture/picture32.png)

## 五、浏览地址
前台登录页面: http://localhost:9528/

- 用户账号/密码：zs111/123456
- 员工账号/密码：ze/123456
- 管理员账号/密码：admin/123456
## 六、部署教程

1. 使用Navicat或者其它工具，在mysql中创建对应名称的数据库，并执行项目的sql

2. 使用IDEA/Eclipse导入itmk-base-parent项目，导入时，若为maven项目请选择maven; 等待依赖下载完成

3. 修改resources目录下面application-test.yml里面的数据库配置

4. src/main/java/com/itmk/WyglApplication.java启动后端

5. vscode或idea打开vue-admin-template项目

6. 在编译器中打开terminal，执行npm install 依赖下载完成后执行 npm run dev,执行成功后会显示访问地址

