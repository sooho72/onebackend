package com.example.onepointup.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "reports")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Report extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "journal_id", nullable = true)
    private Journal journal; // 신고된 일지 (optional)

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = true)
    private Comment comment; // 신고된 댓글 (optional)

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 신고한 사용자

    @Column(name = "reason", nullable = false, columnDefinition = "TEXT")
    private String reason;
}
