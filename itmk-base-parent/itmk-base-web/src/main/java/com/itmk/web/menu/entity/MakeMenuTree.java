package com.itmk.web.menu.entity;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//构造树形工具
public class MakeMenuTree {

    public static List<Menu> makeTree(List<Menu> menuList,Long pid){
        //存放的是组装之后的树
        List<Menu> list = new ArrayList<>();
        //组装树数据
        Optional.ofNullable(menuList).orElse(new ArrayList<>())
                .stream()
                .filter(item -> item != null && item.getParentId() == pid)
                .forEach(dom ->{  //目的就是查找每一条数据的下级
                    Menu menu = new Menu();
                    //把过滤出来的数据放到新的menu里面
                    BeanUtils.copyProperties(dom,menu);
                    //找出当前数据的下级
                    List<Menu> children = makeTree(menuList, dom.getMenuId());
                    menu.setChildren(children);
                    list.add(menu);
                });
        return list;
    }
    /**
     * 生成路由数据格式
     */
    public static List<RouterVO> makeRouter(List<Menu> menuList,Long pid){
        //接受生产的路由数据
        List<RouterVO> list = new ArrayList<>();
        //组装数据
        Optional.ofNullable(menuList).orElse(new ArrayList<>())
                .stream()
                .filter(item ->item != null && item.getParentId() == pid)
                .forEach(item ->{
                    RouterVO router = new RouterVO();
                    router.setName(item.getName());
                    router.setPath(item.getPath());
                    //判断是否是一级菜单
                    if(item.getParentId() == 0L){
                        router.setComponent("Layout");
                        router.setAlwaysShow(true);
                    }else{
                        router.setComponent(item.getUrl());
                        router.setAlwaysShow(false);
                    }
                    //设置meta
                    router.setMeta(router.new Meta(
                            item.getMenuLabel(),
                            item.getIcon(),
                            item.getMenuCode().split(",")
                    ));
                    //设置children
                    List<RouterVO> children = makeRouter(menuList, item.getMenuId());
                    router.setChildren(children);
                    list.add(router);
                });
        return list;
    }

}
