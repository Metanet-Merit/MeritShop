package com.merit.meritShop.user.controller;

import com.merit.meritShop.common.config.JwtUtil;
import com.merit.meritShop.common.dto.JwtResponseDto;
import com.merit.meritShop.user.dto.UserSignInDto;
import com.merit.meritShop.user.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping(value = "/user")
@Controller
public class LoginController extends LoginCommon {

    private final LoginService loginService;

    @Autowired
    JwtUtil jwtTokenProvider;
    @GetMapping("/login")
    public String login(){
        System.out.println("외않되");
        return "login/login";
    }

    @PostMapping("/login")
    public void login(UserSignInDto userSignInDto, HttpServletRequest request, HttpServletResponse response) throws IOException {
        userSignInDto.setLoginType("local");
        JwtResponseDto jwt = loginService.login(userSignInDto);
        setCookieAndRedirectMain(jwt, request, response);
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {//
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("Authorization") || cookie.getName().equals("userId")
            || cookie.getName().equals("userName") || cookie.getName().equals("JSESSIONID")) {
                    cookie.setMaxAge(0); // 쿠키의 expiration 타임을 0으로 하여 없앤다.
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
            }
        }
        else
            return "login/login";
        return "redirect:/main";
    }




}
