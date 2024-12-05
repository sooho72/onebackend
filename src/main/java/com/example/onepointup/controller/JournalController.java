package com.example.onepointup.controller;

import com.example.onepointup.dto.JournalDTO;
import com.example.onepointup.service.JournalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/journals")
@RequiredArgsConstructor
public class JournalController {

    private final JournalService journalService;

    @PostMapping
    public ResponseEntity<JournalDTO> createJournal(@RequestBody JournalDTO journalDTO) {
        JournalDTO createdJournal = journalService.createJournal(journalDTO);
        return new ResponseEntity<>(createdJournal, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JournalDTO> getJournalById(@PathVariable Long id) {
        JournalDTO journal = journalService.getJournalById(id);
        return new ResponseEntity<>(journal, HttpStatus.OK);
    }
}

