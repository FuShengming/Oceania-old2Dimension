package com.old2dimension.OCEANIA.security;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.old2dimension.OCEANIA.po.JwtUser;
import com.old2dimension.OCEANIA.utils.JWTUtils;
import com.old2dimension.OCEANIA.vo.UserVO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        super.setFilterProcessesUrl("/user/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        // 从输入流中获取到登录的信息
        try {
            UserVO loginUser = new ObjectMapper().readValue(request.getInputStream(), UserVO.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUser.getName(), loginUser.getPwd())
            );
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 成功验证后调用的方法
    // 如果验证成功，就生成token并返回
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        JwtUser jwtUser = (JwtUser) authResult.getPrincipal();
        System.out.println("jwtUser:" + jwtUser.toString());

        List<String> role = Lists.newArrayList();
        Collection<? extends GrantedAuthority> authorities = jwtUser.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            role.add(authority.getAuthority());
        }

        String token = JWTUtils.createToken(jwtUser.getUsername(), role.get(0));

        Map<String, Object> map = new HashMap<>();
        map.put("status", "success");
        map.put("id", Integer.toString(jwtUser.getId()));
        map.put("role", role.get(0));

        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        response.addCookie(cookie);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setHeader("token", token);
        response.getWriter().write(JSONObject.toJSONString(map));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.getWriter().write("authentication failed, reason: " + failed.getMessage());
    }
}
