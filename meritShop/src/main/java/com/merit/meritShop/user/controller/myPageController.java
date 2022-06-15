package com.merit.meritShop.user.controller;

import com.merit.meritShop.user.dto.UserViewDto;
import com.merit.meritShop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Controller
public class myPageController {
    private final UserService userService;

    @RequestMapping("/myPage")
    public String userMypage(HttpServletRequest request, Model model){
        Long id = getIdFromCookies(request.getCookies());
        if (id == null)
            return "login/login";
        else {
            UserViewDto user = userService.getUserDetail(id);
            model.addAttribute("user", user);
            return "myPage/myPage";
        }
    }



    public Long getIdFromCookies(Cookie[] cookies) {
        if (cookies == null)
            return null;
        for(Cookie c : cookies) {
            if (c.getName().equals("userId")) {
                return Long.parseLong(c.getValue());
            }
        }
        return null;
    }
}
