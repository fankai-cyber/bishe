package com.nuedu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuedu.pojo.User;
import com.nuedu.service.IUserService;
import com.nuedu.util.JwtUtil;
import com.nuedu.util.ResultJson;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.nuedu.pojo.BasePojo;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController extends BasePojo {
    @Resource
    BCryptPasswordEncoder passwordEncoder;
    @Resource
    IUserService userService;
    @GetMapping("/index")
    ResultJson search(User user,String searchName,Integer active) throws InterruptedException {
        QueryWrapper<User> wrapper=new QueryWrapper<User>();
        if(StringUtils.isNotBlank(searchName)) {
            wrapper.and(i -> i.like("login_name",searchName)
                    .or().like("name",searchName)
                    .or().like("phone",searchName)
                    .or().like("mail",searchName));
        }
        if (active!=null){
            wrapper.eq("active",active);
        }
        return ResultJson.success(userService.page(new Page<User>(user.getPageNo(),user.getPageSize()),wrapper));
    }
    @GetMapping("/checkname")
    ResultJson checkName(String name){
        QueryWrapper<User> queryWrapper=new QueryWrapper<User>();
        queryWrapper.eq("name",name.trim());
        return  ResultJson.success(userService.getOne(queryWrapper)==null);
    }
    @GetMapping("/checkphone")
    ResultJson checkPhone(String phone){
        QueryWrapper<User> queryWrapper=new QueryWrapper<User>();
        queryWrapper.eq("phone",phone.trim());
        return ResultJson.success(userService.getOne(queryWrapper)==null);
    }
    @GetMapping("/checkloginname")
    ResultJson checkloginname(String loginName){
        QueryWrapper<User> queryWrapper=new QueryWrapper<User>();
        queryWrapper.eq("login_name",loginName.trim());
        return ResultJson.success(userService.getOne(queryWrapper)==null);
    }
    @GetMapping("/checkmail")
    ResultJson checkmail(String mail){
        QueryWrapper<User> queryWrapper=new QueryWrapper<User>();
        queryWrapper.eq("mail",mail.trim());
        return ResultJson.success(userService.getOne(queryWrapper)==null);
    }
    @PostMapping("/add")
    ResultJson add(User user){
        return ResultJson.success(userService.save(user));
    }
    @PostMapping("/login")
    ResultJson login(String username,String password){
        QueryWrapper<User> wrapper=new QueryWrapper<User>();
        wrapper.eq("login_name",username.trim())
                .or().eq("mail",username.trim())
                .or().eq("phone",username.trim());
            User user=userService.getOne(wrapper);
        if (user!=null){
            if (passwordEncoder.matches(password,user.getPassword())){
                String token = JwtUtil.creatToken(user.getName());
                return ResultJson.success(null,"登陆成功",token);
            }else {
                return ResultJson.error("用户名或密码错误");
            }
        }
        return ResultJson.error("用户名或密码错误");
    }
    @GetMapping("/del")
    ResultJson del(User user) {
        System.out.println(user);
        return ResultJson.success(userService.updateById(user));
    }
    @PostMapping("/updata")
    ResultJson updata(User user){
        return ResultJson.success(userService.updateById(user));
    }
}
