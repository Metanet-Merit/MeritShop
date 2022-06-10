package com.merit.meritShop.user.controller;


import com.merit.meritShop.user.service.SignUpService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.SecureRandom;

@RequiredArgsConstructor
@RequestMapping(value = "/naver")
@Controller
public class NaverLoginController extends ApiLogin{
    private final SignUpService signUpService;

    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String uid;
    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String secret;
    @Value("${spring.security.oauth2.client.registration.naver.redirect-uri}")
    private String redirect;

    @Value("${spring.security.oauth2.client.provider.naver.user-info-uri}")
    private  String authUrl;

    @RequestMapping(value = "/redirect", method = RequestMethod.GET)
    public void getToken(HttpServletResponse response) throws IOException {
        String state = generateState();
        String encodedState="";
        try {
            encodedState = URLEncoder.encode(state, "UTF-8");
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        String query = "client_id=" + uid + "&"
                + "state=" + encodedState + "&"
                + "redirect_uri=" + redirect;
        String url = "https://nid.naver.com/oauth2.0/authorize?response_type=code&" + query;
        response.sendRedirect(url);
    }

    @GetMapping("/login")
    public ModelAndView callback(@RequestParam(name = "code") String code, @RequestParam(name = "state") String state, HttpServletRequest request, HttpServletResponse response) {
        String query = "client_id=" + uid + "&"
                + "client_secret=" + secret + "&"
                + "code=" + code + "&"
                + "state=" + state;
        String url = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&" + query;
        String token = getTokenByCode(url);
        //System.out.println(token);

        ResponseEntity<String> responseEntity = getEntityByToken(token, authUrl);
        //System.out.println(responseEntity);
        JSONObject jsonObject = new JSONObject(responseEntity.getBody());
        JSONObject json = jsonObject.getJSONObject("response");
        ModelAndView mav = new ModelAndView();
        mav.addObject("id", json.getString("id"));
        mav.addObject("gender", json.getString("gender"));
        mav.addObject("email", json.getString("email"));
        mav.addObject("mobile", json.getString("mobile"));
        mav.addObject("name", json.getString("name"));
        mav.addObject("birthday", json.getString("birthday"));
        mav.setViewName("naver");

        signUpService.registerNaver(responseEntity);
        return mav;
    }
    public String generateState()
    {
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }
}
