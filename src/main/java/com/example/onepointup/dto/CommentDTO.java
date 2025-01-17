package com.example.onepointup.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor // 모든 필드의 생성자 추가
public class CommentDTO {
    private Long id;         // 댓글 ID
    private Long challengeId;  // 연관된 챌린지글 ID
    private String username;  // 유저이름
    private Long userId;     // 작성자 ID
    private String name;     // 이름
    private String content;  // 댓글 내용
    private LocalDateTime createdAt; // 신고일자
}
