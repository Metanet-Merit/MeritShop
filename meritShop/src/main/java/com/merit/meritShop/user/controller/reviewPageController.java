package com.merit.meritShop.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class reviewPageController {

    @GetMapping("/reviewForm")
    public String reviewPage(){
        return "myPage/reviewForm";

    }
}
