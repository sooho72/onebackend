package com.example.onepointup.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Data
@Entity
@Table(name = "comments")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
// 댓글 엔티티의 기본 키 (자동 생성)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "challenge_id", nullable = false)
    @JsonIgnoreProperties("comments") // Challenge와의 양방향 관계에서 순환 참조 방지
// 댓글이 속한 Challenge 엔티티와의 다대일 관계
    private Challenge challenge;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
// 댓글 작성자(User 엔티티)와의 다대일 관계
    private User user;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
// 댓글의 내용 (TEXT 타입으로 저장)
    private String content;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("comment") // Report와의 양방향 관계에서 순환 참조 방지
// 댓글에 달린 신고(Report)와의 일대다 관계 (댓글 삭제 시 연관된 신고도 삭제됨)
    private List<Report> reports = new ArrayList<>();


}
