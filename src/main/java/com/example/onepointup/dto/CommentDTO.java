package com.example.onepointup.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDTO {
    private Long id;         // 댓글 ID
    private Long journalId;  // 연관된 기록 ID
    private Long userId;     // 작성자 ID
    private String content;  // 댓글 내용
    private Boolean isPositive; // 긍정적 피드백 여부
}
