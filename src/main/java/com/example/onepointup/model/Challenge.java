package com.example.onepointup.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "challenges")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Challenge extends BaseEntity {

    // Challenge 엔티티의 기본 키
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Challenge 작성자 (User 엔티티와 다대일 관계)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 도전 과제의 제목
    @Column(name = "title", nullable = false)
    private String title;

    // 도전 과제의 상세 설명 (TEXT 타입)
    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    // 도전 과제 시작 날짜
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    // 도전 과제 종료 날짜
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    // 도전 과제의 진행 상태 (0.0 ~ 100.0)
    @Column(name = "progress", nullable = false)
    private Float progress;

    // 도전 과제가 완료되었는지 여부
    @Column(name = "is_completed", nullable = false)
    private Boolean isCompleted = false;

    // 도전 과제가 공개 여부 (true: 공개, false: 비공개)
    @Column(name = "is_public", nullable = false)
    private Boolean isPublic;

    // Journal 엔티티와의 일대다 관계 (Cascade 설정으로 Challenge 삭제 시 관련 Journal도 삭제됨)
    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("challenge")
    private List<Journal> journals = new ArrayList<>();

    // Comment 엔티티와의 일대다 관계 (Cascade와 orphanRemoval 설정으로 Challenge 삭제 시 관련 Comment도 삭제됨)
    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("challenge")
    private List<Comment> comments = new ArrayList<>();
}
