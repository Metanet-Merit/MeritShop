package com.merit.meritShop.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class reviewPageController {

    @GetMapping("/reviewPage")
    public String reviewPage(){
        return "myPage/review";

    }
}
