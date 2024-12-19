package com.example.onepointup.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
@Getter
@Setter
@Data
@Entity
@Table(name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
// User 엔티티의 기본 키 (자동 생성)
    private Long id;

    @Column(name = "username", unique = true, nullable = false, length = 100)
// 사용자 이름 (고유 값, 최대 길이 100자)
    private String username;

    @Column(name = "password", nullable = false)
// 사용자 비밀번호
    private String password;

    @Column(name = "name", nullable = false)
// 사용자 실명
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
// 사용자 권한 (USER, ADMIN 등 Role Enum 값)
    private Role role;

    @Transient
// 토큰 필드 (DB에 저장하지 않음)
    private String token;

    @Lob
    @Column(name = "profile_image")
// 사용자 프로필 이미지 (byte 배열로 저장, 대용량 객체)
    private byte[] profileImage;

    }
