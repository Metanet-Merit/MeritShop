package com.merit.meritShop.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class adminController {
    @GetMapping("/admin")
    public String index(){
        return "admin/admin";
    }
}
