package com.nuedu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuedu.pojo.Role;
import com.nuedu.service.IRoleService;
import com.nuedu.service.IUserService;
import com.nuedu.util.ResultJson;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.nuedu.pojo.BasePojo;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;


@RestController
@RequestMapping("/role")
public class RoleController extends BasePojo {
    @Resource
    IRoleService roleService;
    @GetMapping("/index")
    public ResultJson index( Role role){
        QueryWrapper<Role> wrapper=new QueryWrapper<Role>();
        if(StringUtils.isNotBlank(role.getName())){
            wrapper.like("name",role.getName());
            System.out.println("1111");
        }
        if (role.getActive()!=null){
            wrapper.eq("active",role.getActive());
        }
        return ResultJson.success(roleService.page(new Page<Role>(getPageNo(),getPageSize()),wrapper));
    }
    @GetMapping("/checkname")
    ResultJson checkname(String name){
        QueryWrapper<Role> wrapper=new QueryWrapper<Role>();
        wrapper.eq("name",name);
        return ResultJson.success(roleService.getOne(wrapper)==null);
    }
    @PostMapping("/add")
    ResultJson add(Role role){
        return ResultJson.success(roleService.save(role));

    }



}
