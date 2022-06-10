package com.merit.meritShop.user.controller;

import com.merit.meritShop.user.service.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/kakao")
public class KakaoLoginController extends ApiLogin {
    private final SignUpService signUpService;
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
    public ModelAndView redirectGoogleLogin(
            @RequestParam(value = "code") String authCode) {
        String query = "client_id=" + kakaoClientId + "&"
                + "redirect_uri=" + kakaoRedirectUrl + "&"
                + "code=" + authCode;
        String url = kakaoAuthUrl + query;
        String token = getTokenByCode(url);
        //System.out.println(token);
        ResponseEntity<String> responseEntity = getEntityByToken(token, kakapRequestUrl);
       // System.out.println(responseEntity);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("kakao");
        mav.addObject("response", responseEntity.toString());
        signUpService.registerKakao(responseEntity);
        return mav;
    }
}
