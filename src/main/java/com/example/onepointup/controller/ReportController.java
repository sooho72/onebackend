package com.example.onepointup.controller;

import com.example.onepointup.dto.ReportDTO;
import com.example.onepointup.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ReportController {

    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<ReportDTO> createReport(@RequestBody ReportDTO reportDTO) {
        ReportDTO createdReport = reportService.createReport(reportDTO);
        return new ResponseEntity<>(createdReport, HttpStatus.CREATED);
    }

    @GetMapping("/journal/{journalId}")
    public ResponseEntity<List<ReportDTO>> getReportsByJournal(@PathVariable Long journalId) {
        List<ReportDTO> reports = reportService.getReportsByJournal(journalId);
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }
}

