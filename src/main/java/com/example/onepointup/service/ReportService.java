package com.example.onepointup.service;

import com.example.onepointup.dto.ReportDTO;

import java.util.List;

public interface ReportService {
    ReportDTO createReport(ReportDTO reportDTO);
    List<ReportDTO> getReportsByJournal(Long journalId);
    List<ReportDTO> getReportsByComment(Long commentId);
}
