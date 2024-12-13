package com.example.onepointup.dto;

import lombok.*;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor // 모든 필드의 생성자 추가
public class ReportDTO {
    private Long id;         // 신고 ID
    private Long journalId;  // 신고된 기록 ID (optional)
    private Long commentId;  // 신고된 댓글 ID (optional)
    private String reason;   // 신고 사유
    private Long userId;     // 신고자 ID
}
