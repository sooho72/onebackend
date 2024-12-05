package com.example.onepointup.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "journals")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Journal extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "challenge_id", nullable = false)
    private Challenge challenge;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "mood", nullable = false)
    private Mood mood; // 감정 상태 ("GOOD", "NORMAL", "SAD")

    @Column(name = "progress", nullable = false)
    private Float progress; // 기록 시 달성률
}
