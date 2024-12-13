package com.example.onepointup.controller;

import com.example.onepointup.dto.JournalDTO;
import com.example.onepointup.service.JournalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Log4j2
@RestController
@RequestMapping("/api/journals")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class JournalController {

    private final JournalService journalService;

    @PostMapping
    public ResponseEntity<JournalDTO> createJournal(@RequestBody JournalDTO journalDTO) {
        log.info(journalDTO);
        JournalDTO createdJournal = journalService.createJournal(journalDTO);
        return new ResponseEntity<>(createdJournal, HttpStatus.CREATED);
    }

    @GetMapping("/challenge/{challengeId}")
    public ResponseEntity<List<JournalDTO>> getJournalsByChallengeId(@PathVariable Long challengeId) {
        List<JournalDTO> journals = journalService.getJournalsByChallenge(challengeId);
        return new ResponseEntity<>(journals, HttpStatus.OK);
    }
}
