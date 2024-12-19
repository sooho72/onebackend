package com.example.onepointup.repository;

import com.example.onepointup.dto.ReportDTO;
import com.example.onepointup.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    // 특정 댓글에 대한 모든 신고 조회
    List<Report> findByCommentId(Long commentId);

}

