package com.example.onepointup.controller;

import com.example.onepointup.model.User;
import com.example.onepointup.service.AuthenticationService;
import com.example.onepointup.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/authentication")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping("sign-up") //회원가입
    public ResponseEntity<Object> signUp(@RequestBody User user) { //RequestBody 제이선으로 오는데이터를 유저라는모델에 넣어준다
        if(userService.findByUsername(user.getUsername()) != null) { //유저네임이 같은게있으면 에러
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED); //인증실패예외처리 그렇지않으면 유저를저장하고 유저데이터를 포스트에 보여줌
    }
    @PostMapping("sign-in") //로그인
    public ResponseEntity<Object> signIn(@RequestBody User user){
        return new ResponseEntity<>(authenticationService.signInAndReturnJWT(user), HttpStatus.OK); //로그인 체크

    }
}