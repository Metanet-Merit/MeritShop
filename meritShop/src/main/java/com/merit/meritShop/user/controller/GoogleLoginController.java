package com.merit.meritShop.user.controller;

import com.merit.meritShop.user.dto.ApiLoginRequestDto;
import com.merit.meritShop.user.dto.UserSignUpDto;
import com.merit.meritShop.user.service.LoginService;
import com.merit.meritShop.user.service.SignUpService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/google")
public class GoogleLoginController extends ApiLogin {
    private final SignUpService signUpService;

    @Value("${google.auth.url}")
    private String googleAuthUrl;

    @Value("${google.login.url}")
    private String googleLoginUrl;

    @Value("${google.redirect.uri}")
    private String googleRedirectUrl;

    @Value("${google.client.id}")
    private String googleClientId;

    @Value("${google.secret}")
    private String googleSecret;

    @Value("${google.auth.scope}")
    private String scopes;

    @GetMapping("/redirect")
    public void getToken(HttpServletResponse response) throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("client_id", googleClientId);
        params.put("redirect_uri", googleRedirectUrl);
        params.put("response_type", "code");
        params.put("scope", getScopeUrl());

        String paramStr = params.entrySet().stream()
                .map(param -> param.getKey() + "=" + param.getValue())
                .collect(Collectors.joining("&"));
        String url = googleLoginUrl
                + "/o/oauth2/v2/auth"
                + "?"
                + paramStr;
        response.sendRedirect(url);
    }

    @GetMapping(value = "/login")
    public ModelAndView redirectGoogleLogin(
            @RequestParam(value = "code") String authCode) {
        ApiLoginRequestDto requestParams = ApiLoginRequestDto.builder()
                .clientId(googleClientId)
                .clientSecret(googleSecret)
                .code(authCode)
                .redirectUri(googleRedirectUrl)
                .grantType("authorization_code")
                .build();

        String url = googleAuthUrl + "/token";
        String token = getTokenByCodeWithParams(requestParams, url);

        String GOOGLE_USERINFO_REQUEST_URL="https://www.googleapis.com/oauth2/v2/userinfo";

        ResponseEntity<String> responseEntity = getEntityByToken(token, GOOGLE_USERINFO_REQUEST_URL);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("google");
        mav.addObject("response", responseEntity.toString());
        signUpService.registerGoogle(responseEntity);
        return mav;
    }



    // scope의 값을 보내기 위해 띄어쓰기 값을 UTF-8로 변환하는 로직 포함
    public String getScopeUrl() {
        return scopes.replaceAll(",", "%20");
    }
}
