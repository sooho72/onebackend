package com.example.onepointup.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReportDTO {
    private Long id;         // 신고 ID
    private Long journalId;  // 신고된 기록 ID (optional)
    private Long commentId;  // 신고된 댓글 ID (optional)
    private String reason;   // 신고 사유
    private Long userId;     // 신고자 ID
}
