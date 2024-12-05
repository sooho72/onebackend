package com.example.onepointup.service;

import com.example.onepointup.dto.JournalDTO;

import java.util.List;

public interface JournalService {
    JournalDTO createJournal(JournalDTO journalDTO);
    JournalDTO getJournalById(Long journalId);
    List<JournalDTO> getJournalsByChallenge(Long challengeId);
    JournalDTO updateJournal(Long journalId, JournalDTO journalDTO);
    void deleteJournal(Long journalId);
}
