package com.example.onepointup.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    private Long id;         // 사용자 고유 ID
    private String username; // 사용자 ID
    private String password; // 사용자 비밀번호
    private String name;     // 사용자 이름
    private String role;     // 사용자 역할 (예: "USER", "ADMIN")
}
