package com.example.onepointup.service;

import com.example.onepointup.dto.ReportDTO;
import com.example.onepointup.model.Comment;
import com.example.onepointup.model.Journal;
import com.example.onepointup.model.Report;
import com.example.onepointup.repository.CommentRepository;
import com.example.onepointup.repository.JournalRepository;
import com.example.onepointup.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final JournalRepository journalRepository;
    private final CommentRepository commentRepository;

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

        Report report = Report.builder()
                .journal(journal)
                .comment(comment)
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
                .reason(report.getReason())
                .build();
    }
}