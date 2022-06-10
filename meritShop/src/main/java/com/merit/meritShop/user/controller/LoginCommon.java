package com.merit.meritShop.user.controller;

import com.merit.meritShop.common.dto.JwtResponseDto;
import com.merit.meritShop.user.dto.ApiLoginRequestDto;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;

public class LoginCommon {
    //restTemplate으로 token 받아오기 (google용)
    public String getTokenByCodeWithParams(ApiLoginRequestDto requestParams, String url) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        HttpEntity<ApiLoginRequestDto> httpRequestEntity = new HttpEntity<>(requestParams, httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpRequestEntity, String.class);

        JSONObject jsonObject;
        jsonObject = new JSONObject(responseEntity.getBody());
        return (String)jsonObject.get("access_token");
    }
    //restTemplate으로 token 받아오기 (kakao, naver용)
    public String getTokenByCode(String url) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        HttpEntity entity = new HttpEntity(httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST,
                entity, String.class);
        JSONObject jsonObject;
        jsonObject = new JSONObject(responseEntity.getBody());
        return (String)jsonObject.get("access_token");
    }

    //restTemplate으로 로그인API서버에 token을 주며 통신
    public ResponseEntity<String> getEntityByToken(String token, String url) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + token);
        HttpEntity entity = new HttpEntity(httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        return (restTemplate.exchange(url, HttpMethod.GET, entity, String.class));
    }

    public void setCookieAndRedirectMain(JwtResponseDto jwt, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie jwtToken = new Cookie("Authorization", jwt.getToken());
        Cookie userId = new Cookie("userId", jwt.getUserId().toString());

        Cookie intra = new Cookie("userName",URLEncoder.encode(jwt.getUserName(), "utf-8"));
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
