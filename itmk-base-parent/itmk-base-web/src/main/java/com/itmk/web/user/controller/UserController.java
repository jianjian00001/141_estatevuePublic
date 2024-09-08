package com.itmk.web.user.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itmk.config.jwt.JwtUtils;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.live_user.entity.LiveUser;
import com.itmk.web.live_user.service.LiveUserService;
import com.itmk.web.menu.entity.MakeMenuTree;
import com.itmk.web.menu.entity.Menu;
import com.itmk.web.menu.entity.RouterVO;
import com.itmk.web.menu.service.MenuService;
import com.itmk.web.user.entity.*;
import com.itmk.web.user.service.UserService;
import com.itmk.web.user_role.entity.UserRole;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 员工管理的控制器
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;

    @Resource
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MenuService menuService;
    @Autowired
    private LiveUserService liveUserService;
    /**
     * 重置密码
     */
    @PostMapping("/resetPassword")
    public ResultVo resetPassword(@RequestBody ChangePassword user,HttpServletRequest request){
        //获取token
        String token = request.getHeader("token");
        Claims claims = jwtUtils.getClaimsFromToken(token);
        //判断用户类型
        Object userType = claims.get("userType");
        if(userType.equals("0")){//0：业主
            LiveUser liveUser = liveUserService.getById(user.getUserId());
            //原来的密码
            String dataOldPassword = liveUser.getPassword();
            boolean encode = passwordEncoder.matches(user.getOldPassword(),dataOldPassword);
            if(!encode){
                return ResultUtils.error("旧密码错误!");
            }
            LiveUser liveUser1 = new LiveUser();
            liveUser1.setUserId(user.getUserId());
            liveUser1.setPassword(passwordEncoder.encode(user.getNewPassword()));
            boolean b = liveUserService.updateById(liveUser1);
            if(b){
                return ResultUtils.success("密码修改成功!");
            }
            return ResultUtils.error("密码修改失败!");
        }else{
            User liveUser = userService.getById(user.getUserId());
            //原来的密码
            String dataOldPassword = liveUser.getPassword();
            boolean encode = passwordEncoder.matches(user.getOldPassword(),dataOldPassword);
            if(!encode){
                return ResultUtils.error("旧密码错误!");
            }
            User liveUser1 = new User();
            liveUser1.setUserId(user.getUserId());
            liveUser1.setPassword(passwordEncoder.encode(user.getNewPassword()));
            boolean b = userService.updateById(liveUser1);
            if(b){
                return ResultUtils.success("密码修改成功!");
            }
            return ResultUtils.error("密码修改失败!");
        }
    }

    /**
     * 退出登录
     */
    @PostMapping("/loginOut")
    public ResultVo loginOut(HttpServletRequest request, HttpServletResponse response){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            new SecurityContextLogoutHandler().logout(request,response,authentication);
        }
        return ResultUtils.success("退出登录成功!");
    }

    /**
     * 获取菜单列表
     */
    @GetMapping("/getMenuList")
    public ResultVo getMenuList(HttpServletRequest request){
        //获取token
        String token = request.getHeader("token");
        //获取用户名
        String username = jwtUtils.getUsernameFromToken(token);
        //获取用户类型
        Claims claims = jwtUtils.getClaimsFromToken(token);
        Object userType = claims.get("userType");
        if(userType.equals("0")){//0：业主
            //获取用户信息
            LiveUser liveUser = liveUserService.loadUser(username);
            //查询业主的权限信息
            List<Menu> menuList = menuService.getMenuByUserIdForLiveUser(liveUser.getUserId());
            List<Menu> collect = menuList.stream().filter(item -> item != null && !item.getType().equals("2")).collect(Collectors.toList());
            //组装路由数据
            List<RouterVO> routerVOS = MakeMenuTree.makeRouter(collect, 0L);
            return ResultUtils.success("查询成功",routerVOS);
        }else{
            //获取用户信息
            User liveUser = userService.loadUser(username);
            //查询业主的权限信息
            List<Menu> menuList = menuService.getMenuByUserId(liveUser.getUserId());
            List<Menu> collect = menuList.stream().filter(item -> item != null && !item.getType().equals("2")).collect(Collectors.toList());
            //组装路由数据
            List<RouterVO> routerVOS = MakeMenuTree.makeRouter(collect, 0L);
            return ResultUtils.success("查询成功",routerVOS);
        }
    }


    /**
     * 根据用户id
     * 获取用户信息
     *
     */

    @GetMapping("/getInfo")
    public ResultVo getInfo(User user, HttpServletRequest request){
        //从头部获取token
        String token = request.getHeader("token");
        Claims claims = jwtUtils.getClaimsFromToken(token);
        Object userType = claims.get("userType");
        if(userType.equals("0")){ //0：业主
            LiveUser liveUser = liveUserService.getById(user.getUserId());
            UserInfo userInfo = new UserInfo();
            userInfo.setId(liveUser.getUserId());
            userInfo.setName(liveUser.getUsername());
            userInfo.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
            //查询业主的权限信息
            List<Menu> menuList = menuService.getMenuByUserIdForLiveUser(liveUser.getUserId());
            //获取权限字段
            List<String> collect = menuList.stream().filter(item -> item != null).map(item -> item.getMenuCode()).filter(item -> item != null).collect(Collectors.toList());
            //转成数组
            String[] strings = collect.toArray(new String[collect.size()]);
            userInfo.setRoles(strings);
            return ResultUtils.success("获取用户信息成功",userInfo);
        }else{ //物主
            //根据用户id查询,区分查的是哪一个
            User user1 = userService.getById(user.getUserId());
            UserInfo userInfo = new UserInfo();
            userInfo.setId(user1.getUserId());
            userInfo.setName(user1.getUsername());
            userInfo.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
            //根据用户id查询权限字段
            //查询用户权限信息
            List<Menu> menuList = menuService.getMenuByUserId(user.getUserId());
            //获取权限字段
            List<String> collect = menuList.stream().filter(item -> item != null).map(item -> item.getMenuCode()).filter(item -> item != null).collect(Collectors.toList());
            //转成数组
            String[] strings = collect.toArray(new String[collect.size()]);
            userInfo.setRoles(strings);
            return ResultUtils.success("获取用户信息成功",userInfo);
        }
    }
    /**
     * 用户登录
     */
    @PostMapping("/login")
    public ResultVo login(@RequestBody LoginParm parm)
    {
        if(StringUtils.isEmpty(parm.getUsername()) || StringUtils.isEmpty(parm.getPassword()) || StringUtils.isEmpty(parm.getUserType())){
            return ResultUtils.error("用户名、密码或用户类型不能为空!");
        }
        //加密后的密码
        String encode = passwordEncoder.encode(parm.getPassword());
        //spring security需要的token
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(parm.getUsername()+":"+parm.getUserType(),parm.getPassword());
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        if(parm.getUserType().equals("0")){
            LiveUser user = (LiveUser)authenticate.getPrincipal();
            String token = jwtUtils.generateToken(parm.getUsername(),parm.getUserType());
            //获取token过期的时间
            Long time = jwtUtils.getExpireTime(token);
            LoginResult result = new LoginResult();
            result.setUserId(user.getUserId());
            result.setToken(token);
            result.setExpireTime(time);
            return ResultUtils.success("登录成功",result);
        }else{
            User user = (User)authenticate.getPrincipal();
            String token = jwtUtils.generateToken(parm.getUsername(),parm.getUserType());
            //获取token过期的时间
            Long time = jwtUtils.getExpireTime(token);
            LoginResult result = new LoginResult();
            result.setUserId(user.getUserId());
            result.setToken(token);
            result.setExpireTime(time);
            return ResultUtils.success("登录成功",result);
        }
    }
//    @PostMapping("/login")
//    public ResultVo login(@RequestBody LoginParm parm)
//    {
//        if(StringUtils.isEmpty(parm.getUsername()) || StringUtils.isEmpty(parm.getPassword()) || StringUtils.isEmpty(parm.getUserType())){
//            return ResultUtils.error("用户名、密码或用户类型不能为空!");
//        }
//        //加密前端传来的密码
//        String password = DigestUtils.md5DigestAsHex(parm.getPassword().getBytes());
//        //构造查询条件
//        QueryWrapper<User> query = new QueryWrapper<>();
//        query.lambda().eq(User::getLoginName,parm.getUsername())
//                .eq(User::getPassword,password);
//        //现在用户类型 userType还么有使用到
//        User user = userService.getOne(query);
//        //如果没有查到用户
//        if(user == null){
//            return ResultUtils.error("用户名、密码或者用户类型错误!");
//        }
//        //生成token返回给前端
//        String token = jwtUtils.generateToken(user.getUsername());
//        //获取token过期的时间
//        Long time = jwtUtils.getExpireTime(token);
//        LoginResult result = new LoginResult();
//        result.setUserId(user.getUserId());
//        result.setToken(token);
//        result.setExpireTime(time);
//        return ResultUtils.success("登录成功",result);
//    }
    /**
     * 新增员工
     *
     * @param user
     * @return
     */
    @PostMapping
    @PreAuthorize("hasAuthority('sys:user:add')")
    public ResultVo addUser(@RequestBody User user) {
        //判断登录名 的唯一性
        if (StringUtils.isNotEmpty(user.getUsername())) {
            QueryWrapper<User> query = new QueryWrapper<>();
            query.lambda().eq(User::getUsername, user.getUsername());
            User one = userService.getOne(query);
            if (one != null) {
                return ResultUtils.error("登录名已经被占用!", 500);
            }
        }
        //如果密码存在，MD5加密
        if (StringUtils.isNotEmpty(user.getPassword())) {
//            user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        boolean save = userService.save(user);
        if (save) {
            return ResultUtils.success("新增员工成功");
        }
        return ResultUtils.error("新增员工失败");
    }

    /**
     * 编辑员工
     *
     * @param user
     * @return
     */
    @PreAuthorize("hasAuthority('sys:user:edit')")
    @PutMapping
    public ResultVo editUser(@RequestBody User user) {
        //判断登录名 的唯一性
        if (StringUtils.isNotEmpty(user.getUsername())) {
            QueryWrapper<User> query = new QueryWrapper<>();
            query.lambda().eq(User::getUsername, user.getUsername());
            User one = userService.getOne(query);
            if (one != null && one.getUserId() != user.getUserId()) {
                return ResultUtils.error("登录名已经被占用!", 500);
            }
        }
        //如果密码存在，MD5加密
        if (StringUtils.isNotEmpty(user.getPassword())) {
//            user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        boolean b = userService.updateById(user);
        if (b) {
            return ResultUtils.success("编辑员工成功");
        }
        return ResultUtils.error("编辑员工失败");
    }

    /**
     * 删除员工
     *
     * @param userId
     * @return
     */
    @PreAuthorize("hasAuthority('sys:user:delete')")
    @DeleteMapping("/{userId}")
    public ResultVo deleteUser(@PathVariable("userId") Long userId) {
        boolean b = userService.removeById(userId);
        if (b) {
            return ResultUtils.success("删除员工成功");
        }
        return ResultUtils.error("删除员工失败");
    }

    /**
     * 查询员工列表
     *
     * @param parm
     * @return
     */
    @GetMapping("/list")
    public ResultVo list(UserParm parm) {
        System.out.println(passwordEncoder.encode("123456"));
        IPage<User> list = userService.list(parm);
        //前端不展示密码
        list.getRecords().stream().forEach(item -> item.setPassword(""));
        return ResultUtils.success("查询成功", list);
    }

    /**
     * 根据用户id查询角色
     */
    @GetMapping("/getRoleByUserId")
    public ResultVo getRoleByUserId(UserRole userRole){
        UserRole userRole1 = userService.getRoleByUserId(userRole);
        return ResultUtils.success("查询成功",userRole1);
    }

    /**
     * 保存用户角色
     */

    @PostMapping("/saveRole")
    public ResultVo saveRole(@RequestBody UserRole userRole){
        userService.saveRole(userRole);
        return ResultUtils.success("分配角色成功!");
    }
	
	  /**
     * 导出接口
     */
//    @PreAuthorize("hasAuthority('sys:user:export')")
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        List<User> list = userService.list();
        // 通过工具类创建writer 写出到磁盘路径
//        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/用户信息.xlsx");
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        writer.addHeaderAlias("loginName", "物业员工姓名");
        writer.addHeaderAlias("username", "登录昵称");
        writer.addHeaderAlias("phone", "电话");
        writer.addHeaderAlias("sex", "性别");
        writer.addHeaderAlias("idCard", "身份证");
        writer.addHeaderAlias("isAdmin", "在职情况");
        writer.addHeaderAlias("status", "是否启用");


        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();

    }

    /**
     * excel 导入
     * @param file
     * @throws Exception
     */
//    @PreAuthorize("hasAuthority('sys:user:import')")
    @PostMapping("/import")
    public Boolean imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        // 方式1：(推荐) 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
//        List<User> list = reader.readAll(User.class);

        // 方式2：忽略表头的中文，直接读取表的内容
        List<List<Object>> list = reader.read(1);
        List<User> users = CollUtil.newArrayList();
        for (List<Object> row : list) {
            User user = new User();
            user.setLoginName(row.get(0).toString());
            user.setUsername(row.get(1).toString());
            user.setPhone(row.get(2).toString());
            user.setSex(row.get(3).toString());
            user.setIdCard(row.get(4).toString());
            user.setIsAdmin(row.get(5).toString());
            user.setStatus(row.get(6).toString());
            users.add(user);
        }

        userService.saveBatch(users);
        return true;
    }
}
