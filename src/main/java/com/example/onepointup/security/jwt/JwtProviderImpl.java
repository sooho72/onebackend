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
    private String JWT_SECRET;
    @Value("${app.jwt.expiration-in-ms}")
    private Long JWT_EXPIRATION_IN_MS;

    @Override
    public String generateToken(UserPrinciple auth) { //집한된 권한정보를 스트림으로 만든다
        String authorites=auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        Key key= Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8)); //키를 만듬

        return Jwts.builder() //토큰을 만듬
                .setSubject(auth.getUsername())
                .claim("roles", authorites)
                .claim("userId", auth.getId())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_IN_MS)) //현재시간을 밀리센카드로 만들고 토믄의 유효시간
                .signWith(key, SignatureAlgorithm.HS512) //패싱문자로만듬
                .compact();
    }

    @Override
    public Authentication getAuthentication(HttpServletRequest request) {
        Claims claims =extractClaims(request);//클레임추출
        if(claims==null){
            return null;
        }
        String username = claims.getSubject();  // 클레임에서 사용정보 추출
        Long userId = claims.get("userId", Long.class); // 토큰을 얻을때

        Set<GrantedAuthority> authorities = // 권한정보를 얻을때 get 을 set로 만듬
                Arrays.stream(claims.get("roles").toString().split(","))
                        .map(SecurityUtils::convertToAuthority)
                        .collect(Collectors.toSet());

        UserDetails userDetails=UserPrinciple.builder() //인증사용자객체 빌드
                .username(username)
                .authorities(authorities)
                .id(userId)
                .build();
        if(username ==null){ //유저네임이 널이면 널을 리턴하고
            log.info("username is null");
            return null;
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, authorities); //인증객체를 만들어줌
    }

    @Override
    public boolean isTokenValid(HttpServletRequest request) {
        Claims claims = extractClaims(request);
        if(claims==null){
            return false;
        }
        if(claims.getExpiration().before(new Date())){ //토큰유효시간이끝났으면 리턴(펄스를)해라 그렇지않으면 튜루를 리턴해라
            return false;
        }
        return true;
    }

    private Claims extractClaims(HttpServletRequest request) {
        String token = SecurityUtils.extractAuthTokenFromRequest(request);
        if(token == null) { //claims 호출하면
            return null;
        }
        Key key= Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}