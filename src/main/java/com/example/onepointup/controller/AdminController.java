package com.example.onepointup.controller;

import com.example.onepointup.dto.UserDTO;
import com.example.onepointup.model.Role;
import com.example.onepointup.security.UserPrinciple;
import com.example.onepointup.service.ChallengeService;
import com.example.onepointup.service.CommentService;
import com.example.onepointup.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {
    final UserService userService;
    final ChallengeService challengeService;
    final CommentService commentService;


    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getAllUsersAndCurrentUser(
            @AuthenticationPrincipal UserPrinciple userPrinciple) {
        // 모든 유저 불러오기
        List<UserDTO> users = userService.findAllUsers();

        // 로그인 중인 유저 정보 UserPrinciple에서 가져오기
        UserDTO currentUser = UserDTO.builder()
                .id(userPrinciple.getId())
                .username(userPrinciple.getUsername())
                .name(userPrinciple.getName())
                .build();

        // 데이터를 맵에 담아서 반환
        Map<String, Object> response = new HashMap<>();
        response.put("allUsers", users);
        response.put("currentUser", currentUser);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/change/{role}")
    public ResponseEntity<Object> changeRole(
            @AuthenticationPrincipal UserPrinciple userPrinciple,
            @PathVariable Role role) {
        if (!userPrinciple.getUser().getRole().equals(Role.ADMIN)) {
            throw new AccessDeniedException("권한 변경은 관리자만 수행할 수 있습니다.");
        }
        userService.changeRole(role, userPrinciple.getUsername());
        return ResponseEntity.ok(true);
    }

}
