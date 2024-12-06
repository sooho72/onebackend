package com.example.onepointup.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthorizationFilter extends OncePerRequestFilter {
//    OncePerRequestFilter Class : Http Request의 한 번의 요청에 대해 한 번만 실행하는 Filter
//    예: 컨트롤러에서 Forwarding이 발생하면 Filter Chain이 다시 동작하여 인증처럼 딱 한번만 수
//    행하도록 하여 Logic이 불필요하게 여러 번 수행될 수 있는 것을 막아 줌.
//    private JwtProvider jwtProvider;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request
            , HttpServletResponse response
            , FilterChain filterChain)
            throws ServletException, IOException { //FilterChain: 다음 필터로 요청을 전달하기 위한 객체.
        Authentication authentication = jwtProvider.getAuthentication(request); //인증(Authentication) 객체 가져오기
        if(authentication != null && jwtProvider.isTokenValid(request)){ //JWT 유효성 검증 및 인증 설정
            SecurityContextHolder.getContext().setAuthentication(authentication); //인증된 사용자 정보를 SecurityContext에 설정
        }
        filterChain.doFilter(request, response); //다음 필터로 요청 전달
    }
}