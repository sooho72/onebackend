package com.example.onepointup.service;

import com.example.onepointup.model.User;
import com.example.onepointup.security.UserPrinciple;
import com.example.onepointup.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Override
    public User signInAndReturnJWT(User signInRequest) {
        Authentication authentication = authenticationManager.authenticate( //사용자 인증 처리
                // username과 password를 담은 인증 토큰 생성
                new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword())
        );
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal(); // 인증성공한 사용자 정보 얻기
        String jwt = jwtProvider.generateToken(userPrinciple); // UserPrinciple 정보를 기반으로 JWT 생성
        User signInUser = userPrinciple.getUser();
        log.info("로그인 username" + signInUser);
        signInUser.setToken(jwt);
        return signInUser;
    }
}