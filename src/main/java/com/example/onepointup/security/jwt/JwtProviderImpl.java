package com.example.onepointup.security.jwt;

import com.example.onepointup.security.UserPrinciple;
import com.example.onepointup.utils.SecurityUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@Component
public class JwtProviderImpl implements JwtProvider {
    @Value("${app.jwt.secret}")
    private String JWT_SECRET; // JWT 암호화를 위한 비밀 키
    @Value("${app.jwt.expiration-in-ms}")
    private Long JWT_EXPIRATION_IN_MS; // JWT 만료 시간 (밀리초 단위)

    /**
     * 사용자 정보를 기반으로 JWT 토큰을 생성.
     *
     * @param auth 인증된 사용자 정보 (UserPrinciple 객체)
     * @return 생성된 JWT 토큰
     */
    @Override
    public String generateToken(UserPrinciple auth) {
        // 사용자의 권한 정보를 콤마(,)로 구분된 문자열로 변환
        String authorites = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        // 비밀 키 생성
        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));

        // JWT 토큰 생성
        return Jwts.builder()
                .setSubject(auth.getUsername()) // 사용자 이름 설정
                .claim("roles", authorites) // 사용자 권한 정보 추가
                .claim("userId", auth.getId()) // 사용자 ID 추가
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_IN_MS)) // 만료 시간 설정
                .signWith(key, SignatureAlgorithm.HS512) // HMAC SHA-512 알고리즘으로 서명
                .compact();
    }

    /**
     * HTTP 요청으로부터 인증 객체를 생성.
     *
     * @param request 클라이언트의 HTTP 요청
     * @return 인증 객체 (Authentication)
     */
    @Override
    public Authentication getAuthentication(HttpServletRequest request) {
        Claims claims = extractClaims(request); // 클레임 추출
        if (claims == null) {
            return null; // 클레임이 없으면 null 반환
        }

        // 사용자 이름과 ID 추출
        String username = claims.getSubject();
        Long userId = claims.get("userId", Long.class);

        // 권한 정보를 Set으로 변환
        Set<GrantedAuthority> authorities = Arrays.stream(claims.get("roles").toString().split(","))
                .map(SecurityUtils::convertToAuthority)
                .collect(Collectors.toSet());

        // UserDetails 객체 생성
        UserDetails userDetails = UserPrinciple.builder()
                .username(username)
                .authorities(authorities)
                .id(userId)
                .build();

        if (username == null) {
            log.info("username is null");
            return null;
        }

        // 인증 객체 반환
        return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
    }

    /**
     * HTTP 요청에 포함된 JWT 토큰의 유효성을 검증.
     *
     * @param request 클라이언트의 HTTP 요청
     * @return 유효한 토큰이면 true, 그렇지 않으면 false
     */
    @Override
    public boolean isTokenValid(HttpServletRequest request) {
        Claims claims = extractClaims(request); // 클레임 추출
        if (claims == null) {
            return false;
        }
        // 토큰 만료 여부 확인
        if (claims.getExpiration().before(new Date())) {
            return false;
        }
        return true;
    }

    /**
     * HTTP 요청으로부터 JWT 토큰을 추출하고 클레임을 반환.
     *
     * @param request 클라이언트의 HTTP 요청
     * @return JWT 클레임 객체
     */
    private Claims extractClaims(HttpServletRequest request) {
        String token = SecurityUtils.extractAuthTokenFromRequest(request); // 요청에서 토큰 추출
        if (token == null) {
            return null;
        }
        // 비밀 키 생성
        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
        // JWT 토큰 파싱 및 클레임 반환
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}