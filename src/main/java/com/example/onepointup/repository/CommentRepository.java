package com.example.onepointup.repository;

import com.example.onepointup.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 특정 기록에 대한 모든 댓글 조회
    List<Comment> findByJournalId(Long journalId);

    // 특정 사용자가 작성한 모든 댓글 조회
    List<Comment> findByUserId(Long userId);
}
