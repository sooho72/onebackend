package com.example.onepointup.controller;

import com.example.onepointup.dto.UserDTO;
import com.example.onepointup.model.Role;
import com.example.onepointup.security.UserPrinciple;
import com.example.onepointup.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    // 로그인된 사용자 정보 반환
    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getCurrentUser(@AuthenticationPrincipal UserPrinciple userPrinciple) {
        UserDTO userDTO = userService.getUserByUsername(userPrinciple.getUsername());
        return new ResponseEntity<>(userDTO, HttpStatus.OK);

    }

    @PutMapping("/profile-image")
    public ResponseEntity<String> uploadProfileImage(
            @RequestParam("username") String username,
            @RequestParam("file") MultipartFile file) {
        try {
            // 파일 크기 제한 (예: 5MB)
            if (file.getSize() > 5 * 1024 * 1024) {
                return ResponseEntity.badRequest().body("파일 크기는 5MB를 초과할 수 없습니다.");
            }

            // 파일 형식 검증 (예: JPEG, PNG)
            String contentType = file.getContentType();
            if (!isSupportedContentType(contentType)) {
                return ResponseEntity.badRequest().body("지원되지 않는 파일 형식입니다. JPEG 또는 PNG 파일만 허용됩니다.");
            }

            userService.updateUserProfileImage(username, file);
            return ResponseEntity.ok("프로필 이미지가 성공적으로 업로드되었습니다.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("프로필 이미지 업로드 중 오류가 발생했습니다.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 프로필 이미지 조회
    @GetMapping("/profile-image/{username}")
    public ResponseEntity<byte[]> getProfileImage(@PathVariable String username) {
        try {
            byte[] image = userService.getUserProfileImage(username);
            if (image == null) {
                return ResponseEntity.notFound().build();
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // 또는 실제 이미지 타입에 맞게 설정
            return ResponseEntity.ok().headers(headers).body(image);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 지원되는 콘텐츠 타입 확인
    private boolean isSupportedContentType(String contentType) {
        return contentType.equals(MediaType.IMAGE_JPEG_VALUE) ||
                contentType.equals(MediaType.IMAGE_PNG_VALUE);
    }
}
