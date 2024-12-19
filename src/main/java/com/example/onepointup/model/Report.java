package com.example.onepointup.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
@Getter
@Setter
@Data
@Entity
@Table(name = "reports")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Report extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
// Report 엔티티의 기본 키 (자동 생성)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    @JsonIgnoreProperties("reports") // Comment와의 순환 참조 방지
// 신고된 댓글 (Comment 엔티티와의 다대일 관계)
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
// 신고를 작성한 사용자 (User 엔티티와의 다대일 관계)
    private User user;

    @Column(name = "reason", nullable = false, columnDefinition = "TEXT")
// 신고 사유 (TEXT 타입으로 저장)
    private String reason;


}
