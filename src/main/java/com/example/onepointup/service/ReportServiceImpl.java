package com.example.onepointup.service;

import com.example.onepointup.dto.ReportDTO;
import com.example.onepointup.model.Comment;
import com.example.onepointup.model.Journal;
import com.example.onepointup.model.Report;
import com.example.onepointup.model.User;
import com.example.onepointup.repository.CommentRepository;
import com.example.onepointup.repository.JournalRepository;
import com.example.onepointup.repository.ReportRepository;
import com.example.onepointup.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final JournalRepository journalRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Override
    public ReportDTO createReport(ReportDTO reportDTO) {
        Journal journal = null;
        Comment comment = null;

        if (reportDTO.getJournalId() != null) {
            journal = journalRepository.findById(reportDTO.getJournalId())
                    .orElseThrow(() -> new RuntimeException("Journal not found"));
        }

        if (reportDTO.getCommentId() != null) {
            comment = commentRepository.findById(reportDTO.getCommentId())
                    .orElseThrow(() -> new RuntimeException("Comment not found"));
        }

        // User 정보 가져오기
        User user = userRepository.findById(reportDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Report report = Report.builder()
                .journal(journal)
                .comment(comment)
                .user(user) // 신고한 사용자 정보 설정
                .reason(reportDTO.getReason())
                .build();

        report = reportRepository.save(report);
        return toDTO(report);
    }

    @Override
    public List<ReportDTO> getReportsByJournal(Long journalId) {
        List<Report> reports = reportRepository.findByJournalId(journalId);
        return reports.stream().map(this::toDTO).toList();
    }

    @Override
    public List<ReportDTO> getReportsByComment(Long commentId) {
        List<Report> reports = reportRepository.findByCommentId(commentId);
        return reports.stream().map(this::toDTO).toList();
    }

    private ReportDTO toDTO(Report report) {
        return ReportDTO.builder()
                .id(report.getId())
                .journalId(report.getJournal() != null ? report.getJournal().getId() : null)
                .commentId(report.getComment() != null ? report.getComment().getId() : null)
                .userId(report.getUser() != null ? report.getUser().getId() : null)
                .reason(report.getReason())
                .build();
    }
}
