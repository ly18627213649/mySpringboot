package com.example.yw.controller;

import com.example.proto.NotControllerResponseAdvice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;


/**
 * 模板跳转测试
 */
@Controller
public class TemplateController {

    @RequestMapping(value = "/toFtlTest",method = RequestMethod.GET)
    public String hello(HttpServletRequest req, Model model){
        model.addAttribute("code","200");
        model.addAttribute("msg","成功");
        return "ftlTest";
    }

}
