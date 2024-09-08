package com.itmk.web.menu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.menu.entity.Menu;
import com.itmk.web.menu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    //新增菜单
    @PostMapping
    public ResultVo addMenu(@RequestBody Menu menu){
        boolean save = menuService.save(menu);
        if(save){
            return ResultUtils.success("新增菜单成功!");
        }
        return ResultUtils.error("新增菜单失败!");
    }

    //编辑菜单
    @PutMapping
    public ResultVo editMenu(@RequestBody Menu menu){
        boolean save = menuService.updateById(menu);
        if(save){
            return ResultUtils.success("编辑菜单成功!");
        }
        return ResultUtils.error("编辑菜单失败!");
    }

    //删除菜单
    @DeleteMapping("/{menuId}")
    public ResultVo deleteMenu(@PathVariable("menuId") Long menuId){
        //如果有下级，不能删除
        QueryWrapper<Menu> query = new QueryWrapper<>();
        query.lambda().eq(Menu::getParentId,menuId);
        List<Menu> list = menuService.list(query);
        if(list.size() > 0){
            return ResultUtils.error("该菜单存在下级，不能删除!");
        }
        boolean save = menuService.removeById(menuId);
        if(save){
            return ResultUtils.success("删除菜单成功!");
        }
        return ResultUtils.error("删除菜单失败!");
    }

    //上级部门的查询
    @GetMapping("/parent")
    public ResultVo getParentList(){
        List<Menu> parentList = menuService.getParentList();
        return ResultUtils.success("查询成功",parentList);
    }

    //查询树形表格数据
    @GetMapping("/list")
    public ResultVo getList(){
        List<Menu> list = menuService.getList();
        return ResultUtils.success("查询成功",list);
    }
}
