package com.example.onepointup.security.jwt;

import com.example.onepointup.security.UserPrinciple;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

/**
 * JWT 관련 기능을 제공하는 인터페이스.
 * JWT 토큰 생성, 인증 객체 반환, 토큰 유효성 검증 등의 기능을 정의함.
 */

public interface JwtProvider {
    /**
     * 사용자 정보를 기반으로 JWT 토큰을 생성하는 메서드.
     *
     * @param auth 인증된 사용자 정보 (UserPrinciple 객체)
     * @return 생성된 JWT 토큰
     */
    String generateToken(UserPrinciple auth);

    /**
     * HTTP 요청으로부터 인증 객체를 가져오는 메서드.
     *
     * @param request 클라이언트의 HTTP 요청
     * @return Authentication 객체
     */
    Authentication getAuthentication(HttpServletRequest request);

    /**
     * HTTP 요청에 포함된 JWT 토큰의 유효성을 검증하는 메서드.
     *
     * @param request 클라이언트의 HTTP 요청
     * @return 토큰이 유효하면 true, 그렇지 않으면 false
     */
    boolean isTokenValid(HttpServletRequest request);
}
