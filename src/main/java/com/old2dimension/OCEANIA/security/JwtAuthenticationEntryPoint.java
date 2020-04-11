package com.old2dimension.OCEANIA.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

/**
 * 认证失败处理，返回401
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = 8613557806388879833L;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        System.out.println("认证失败：" + authException.getMessage());
//        response.setStatus(200);
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("text");
//        PrintWriter printWriter = response.getWriter();
//        String body = "You should sign in first!";
//        printWriter.write(body);
//        printWriter.flush();
        response.sendRedirect("/login");
    }
}
