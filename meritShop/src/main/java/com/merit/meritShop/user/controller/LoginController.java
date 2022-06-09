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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class LoginController {

    private final LoginService loginService;

    @Autowired
    JwtUtil jwtTokenProvider;
    @GetMapping("/login")
    public String login(){
        return "login/login";
    }

    @PostMapping("/login")
    public void login(UserSignInDto userSignInDto, HttpServletRequest request, HttpServletResponse response) throws IOException {
        JwtResponseDto jwt = loginService.login(userSignInDto);

        Cookie jwtToken = new Cookie("Authorization", jwt.getToken());
        Cookie userId = new Cookie("userId", jwt.getUserId().toString());
        Cookie intra = new Cookie("userName", jwt.getUserName());
        jwtToken.setMaxAge(2 * 60 * 60);
        userId.setMaxAge(2 * 60 * 60);
        intra.setMaxAge(2 * 60 * 60);
        jwtToken.setPath("/");
        userId.setPath("/");
        intra.setPath("/");
        response.addCookie(jwtToken);
        response.addCookie(userId);
        response.addCookie(intra);
        response.sendRedirect("http://localhost:8080/main");
    }
}
