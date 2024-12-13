package com.example.onepointup.dto;

import lombok.*;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor // 모든 필드의 생성자 추가
public class JournalDTO {
    private Long id;          // 기록 ID
    private Long challengeId; // 연관된 챌린지 ID
    private String content;   // 기록 내용
    private String mood;      // 감정 상태 (예: "GOOD", "NORMAL", "SAD")
    private Float progress;   // 기록 시 달성률
}
