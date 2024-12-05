package com.example.onepointup.service;

import com.example.onepointup.dto.JournalDTO;
import com.example.onepointup.model.Challenge;
import com.example.onepointup.model.Journal;
import com.example.onepointup.model.Mood;
import com.example.onepointup.repository.ChallengeRepository;
import com.example.onepointup.repository.JournalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JournalServiceImpl implements JournalService {

    private final JournalRepository journalRepository;
    private final ChallengeRepository challengeRepository;

    @Override
    public JournalDTO createJournal(JournalDTO journalDTO) {
        Challenge challenge = challengeRepository.findById(journalDTO.getChallengeId())
                .orElseThrow(() -> new RuntimeException("Challenge not found"));

        Journal journal = Journal.builder()
                .challenge(challenge)
                .content(journalDTO.getContent())
                .mood(Mood.valueOf(journalDTO.getMood()))
                .progress(journalDTO.getProgress())
                .date(LocalDate.now())
                .build();

        journal = journalRepository.save(journal);
        return toDTO(journal);
    }

    @Override
    public JournalDTO getJournalById(Long journalId) {
        Journal journal = journalRepository.findById(journalId)
                .orElseThrow(() -> new RuntimeException("Journal not found"));
        return toDTO(journal);
    }

    @Override
    public List<JournalDTO> getJournalsByChallenge(Long challengeId) {
        List<Journal> journals = journalRepository.findByChallengeId(challengeId);
        return journals.stream().map(this::toDTO).toList();
    }

    @Override
    public JournalDTO updateJournal(Long journalId, JournalDTO journalDTO) {
        Journal journal = journalRepository.findById(journalId)
                .orElseThrow(() -> new RuntimeException("Journal not found"));

        journal.setContent(journalDTO.getContent());
        journal.setMood(Mood.valueOf(journalDTO.getMood()));
        journal.setProgress(journalDTO.getProgress());

        journal = journalRepository.save(journal);
        return toDTO(journal);
    }

    @Override
    public void deleteJournal(Long journalId) {
        Journal journal = journalRepository.findById(journalId)
                .orElseThrow(() -> new RuntimeException("Journal not found"));
        journalRepository.delete(journal);
    }

    private JournalDTO toDTO(Journal journal) {
        return JournalDTO.builder()
                .id(journal.getId())
                .challengeId(journal.getChallenge().getId())
                .content(journal.getContent())
                .mood(journal.getMood().name())
                .progress(journal.getProgress())
                .build();
    }
}
