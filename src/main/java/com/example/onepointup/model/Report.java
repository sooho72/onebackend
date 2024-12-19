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
    private Long id;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    @JsonIgnoreProperties("reports")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 신고한 사용자

    @Column(name = "reason", nullable = false, columnDefinition = "TEXT")
    private String reason;

}
