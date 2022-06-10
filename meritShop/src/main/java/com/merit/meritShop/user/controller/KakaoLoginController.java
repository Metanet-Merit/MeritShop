package com.merit.meritShop.user.controller;

import com.merit.meritShop.common.dto.JwtResponseDto;
import com.merit.meritShop.user.dto.UserSignInDto;
import com.merit.meritShop.user.service.LoginService;
import com.merit.meritShop.user.service.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/kakao")
public class KakaoLoginController extends LoginCommon {
    private final SignUpService signUpService;
    private final LoginService loginService;
    @Value("${kakao.redirect.uri}")
    private String kakaoRedirectUrl;

    @Value("${kakao.client.id}")
    private String kakaoClientId;

    @Value("${kakao.login.url}")
    private String kakaoLoginUrl;

    @Value("${kakao.auth.url}")
    private String kakaoAuthUrl;

    @Value("${kakao.request.url}")
    private String kakapRequestUrl;

    @GetMapping("/redirect")
    public void getToken(HttpServletResponse response) throws IOException {
        String query = "client_id=" + kakaoClientId + "&"
                + "redirect_uri=" + kakaoRedirectUrl + "&"
                + "response_type=" + "code";
        String url = kakaoLoginUrl + query;
        response.sendRedirect(url);
    }

    @GetMapping(value = "/login")
    public void redirectGoogleLogin(@RequestParam(value = "code") String authCode
            , HttpServletRequest request, HttpServletResponse response) throws IOException {
        String query = "client_id=" + kakaoClientId + "&"
                + "redirect_uri=" + kakaoRedirectUrl + "&"
                + "code=" + authCode;
        String url = kakaoAuthUrl + query;
        String token = getTokenByCode(url);
        //System.out.println(token);
        ResponseEntity<String> responseEntity = getEntityByToken(token, kakapRequestUrl);
       // System.out.println(responseEntity);
        UserSignInDto userSignInDto = signUpService.registerKakao(responseEntity);
        JwtResponseDto jwt = loginService.login(userSignInDto);
        setCookieAndRedirectMain(jwt, request, response);
    }
}
