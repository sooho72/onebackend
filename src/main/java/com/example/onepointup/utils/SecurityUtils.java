package com.example.onepointup.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

@Log4j2
public class SecurityUtils {
    public static final String ROLE_PREFIX = "ROLE_";
    public static final String AUTH_HEADER = "authorization";
    public static final String AUTH_TOKEN_TYPE = "Bearer";
    public static final String AUTH_TOKEN_PREFIX = AUTH_TOKEN_TYPE + " ";

    public static SimpleGrantedAuthority convertToAuthority(String role) {
        String formatRole = role.startsWith(ROLE_PREFIX) ? role : ROLE_PREFIX+role;
        return new SimpleGrantedAuthority(formatRole);
    }

    public static String extractAuthTokenFromRequest(HttpServletRequest request) { //토큰을가져올때
        String bearerToken = request.getHeader(AUTH_HEADER); //이런이름을가진
        if(StringUtils.hasLength(bearerToken) && bearerToken.startsWith(AUTH_TOKEN_PREFIX)) {
            return bearerToken.substring(7); //토큰 리턴값 추출 7번째 부터 추출
        }
        //만약에 토큰에 랭스가 있으면 토큰에 Bearer로 시작한다면 저문자를 짤라서 토큰만 추출
        return null;
    }
}