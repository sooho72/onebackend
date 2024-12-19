package com.example.onepointup.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor // 모든 필드의 생성자 추가
public class ChallengeDTO {
    private Long id;             // 챌린지 ID
    private String username;     //유저아디
    private String name;         //이름
    private String title;        // 챌린지 제목
    private String description;  // 챌린지 설명
    private LocalDate startDate; // 시작 날짜
    private LocalDate endDate;   // 종료 날짜
    private Float progress;      // 진행률 (0~100%)
    private Boolean isCompleted; // 완료 여부
    private Boolean isPublic; // 공개 여부
    private LocalDateTime createdAt; // 추가
    private LocalDateTime updatedAt; // 추가

}