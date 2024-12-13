package com.example.onepointup.repository;

import com.example.onepointup.dto.ChallengeDTO;
import com.example.onepointup.model.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    // 특정 사용자의 모든 챌린지 조회
   List<Challenge> findByUserId(Long userId);

    // 공개된 챌린지 조회
    List<Challenge> findByIsPublicTrue();
}
