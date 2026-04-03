package com.example.app.controller;

import com.example.pojo.UserLogin;
import com.example.app.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * 登录页面跳转
 */
@Controller
public class UserLoginController {

    @Autowired
    UserLoginService userLoginService;

    // 跳登录
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    // 登录
    @RequestMapping("/LoginSuccess")
    public String LoginSuccess(Model model,@RequestParam Map<String, String> params){
        UserLogin userLogin1 = userLoginService.queryByName(params.get("username"));
        if (userLogin1 != null){
            System.out.println(userLogin1);
            return "success";
        }else {
            model.addAttribute("data","该用户不存在");
            return "login";
        }
    }

    // 跳注册
    @RequestMapping("/toRegister")
    public String toRegister(){
        return "register";
    }

    // 注册
    @RequestMapping("/RegisterSuccess")
    public String toRegisterSuccess(Model model,UserLogin userLogin){
        // 将账号密码加入到数据库中
        int add = userLoginService.add(userLogin);
        model.addAttribute("data","注册成功,请登录");
        return "login";
    }


}
