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
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Override
    public ReportDTO createReport(ReportDTO reportDTO) {
        Long commentId = reportDTO.getCommentId();
        String reason = reportDTO.getReason();

        // 현재 인증된 사용자 정보 획득
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // 로그인한 사용자 이름
        log.info("Creating report for user: " + username + ", commentId: " + commentId);

        User user = userRepository.findByUsername(username);


        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        Report report = Report.builder()
                .user(user)       // 인증 유저
                .comment(comment) // 요청 받은 commentId로 조회한 댓글
                .reason(reason)
                .build();

        report = reportRepository.save(report);
        log.info("Report created with ID: " + report.getId());

        return toDTO(report);
    }

    @Override
    public List<ReportDTO> getAllReports() {
        List<Report> reports = reportRepository.findAll();
        return reports.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private ReportDTO toDTO(Report report) {
        return ReportDTO.builder()
                .id(report.getId())
                .commentId(report.getComment().getId())
                .reason(report.getReason())
                .username(report.getUser().getUsername())
                .name(report.getUser().getName())
                .createdAt(report.getCreatedAt())
                .build();
    }
}
