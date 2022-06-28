package com.merit.meritShop.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException,
            ServletException {
        response.setContentType("text/html; charset=UTF-8");
        request.setAttribute("msg", "로그인이 필요한 서비스입니다. 로그인 해주세요");
        //request.setAttribute("nextPage", "/c");
        request.getRequestDispatcher("/err/denied-page").forward(request, response);
//        PrintWriter out = response.getWriter();
//        out.println("<script>alert('로그인이 필요한 서비스입니다. 로그인 해주세요'); " +
//                "location.href='http://localhost:8080/user/login';</script>"); out.flush();
    }
}
