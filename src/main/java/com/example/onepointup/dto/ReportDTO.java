package com.example.onepointup.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor // 모든 필드의 생성자 추가
public class ReportDTO {
    private Long id;         // 신고 ID
    private Long commentId;  // 신고된 댓글 ID (optional)
    private String reason;   // 신고 사유
    private Long user;     // 신고자 ID
    private String username; // 신고자 유저네임
    private String name;     // 신고자 네임
    private LocalDateTime createdAt; // 신고 작성 날짜
}
