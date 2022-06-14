package com.merit.meritShop.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class myPageController {

    @RequestMapping("/myPage")
    public String main(Model model,
                        @CookieValue(name = "userId", required = false) Long userId){
        model.addAttribute("userId",userId);
        model.addAttribute("hello","hello");
        return "myPage/myPage";
    }

}
