package com.merit.meritShop.common.config;

import com.merit.meritShop.user.domain.User;
import com.merit.meritShop.user.domain.UserToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component

public class WebAccessDeniedHandler implements AccessDeniedHandler {

    private static final Logger logger = LoggerFactory.getLogger(WebAccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse res, AccessDeniedException ade)
            throws IOException, ServletException {
        res.setStatus(HttpStatus.FORBIDDEN.value());
        logger.info("Access Denied\n");
        if(ade instanceof AccessDeniedException) {
            req.setAttribute("msg", "접근권한 없는 사용자입니다.");
            req.setAttribute("nextPage", "/c");
        } else {
            logger.info(ade.getClass().getCanonicalName());
        }
        req.getRequestDispatcher("/err/denied-page").forward(req, res);
    }
}
