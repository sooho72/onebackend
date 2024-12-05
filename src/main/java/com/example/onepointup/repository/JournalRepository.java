package com.example.onepointup.repository;

import com.example.onepointup.model.Journal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface JournalRepository extends JpaRepository<Journal, Long> {
    // 특정 챌린지의 모든 기록 조회
    List<Journal> findByChallengeId(Long challengeId);

    // 특정 날짜의 기록 조회
    List<Journal> findByDate(LocalDate date);
}
