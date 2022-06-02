package com.merit.meritShop.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class myPageController {

    @RequestMapping("/main")
    public String main(Model model){
        model.addAttribute("hello","hello");
        return "myPage/myPage";
    }

}
