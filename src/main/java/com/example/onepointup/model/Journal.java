package com.example.onepointup.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
@Getter
@Setter
@Data
@Entity
@Table(name = "journals")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Journal extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
// Journal 엔티티의 기본 키 (자동 생성)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("journals") // Challenge와의 순환 참조 방지
    @JoinColumn(name = "challenge_id", nullable = false)
// 해당 기록이 속한 Challenge 엔티티와의 다대일 관계
    private Challenge challenge;

    @Column(name = "date", nullable = false)
// 기록 날짜
    private LocalDate date;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
// 기록의 상세 내용 (TEXT 타입으로 저장)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "mood", nullable = false)
// 기록 당시 감정 상태 (GOOD, NORMAL, SAD 중 하나)
    private Mood mood;

    @Column(name = "progress", nullable = false)
// 기록 작성 시점의 도전 과제 진행률 (0.0 ~ 100.0)
    private Float progress;


}
