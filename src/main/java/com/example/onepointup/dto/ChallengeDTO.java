package com.example.onepointup.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class ChallengeDTO {
    private Long id;             // 챌린지 ID
    private String title;        // 챌린지 제목
    private String description;  // 챌린지 설명
    private LocalDate startDate; // 시작 날짜
    private LocalDate endDate;   // 종료 날짜
    private Float progress;      // 진행률 (0~100%)
    private Boolean isCompleted; // 완료 여부
    private Boolean isPublic;    // 공개 여부
}