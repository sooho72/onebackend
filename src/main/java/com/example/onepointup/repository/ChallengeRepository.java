package com.example.onepointup.repository;

import com.example.onepointup.dto.ChallengeDTO;
import com.example.onepointup.model.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    // 특정 사용자의 모든 챌린지를 조회하는 메서드
    @Query("SELECT c FROM Challenge c WHERE c.user.id = :userId")
    List<Challenge> findByUserId(Long userId);

    // 공개된 챌린지를 조회하는 메서드
    List<Challenge> findByIsPublicTrue();

    // 특정 챌린지의 총 진행률을 계산하는 쿼리
    // Journal 엔티티의 progress 필드를 합산하여 반환, 결과가 없을 경우 0을 반환
    @Query("SELECT COALESCE(SUM(j.progress), 0) FROM Journal j WHERE j.challenge.id = :challengeId")
    Float getTotalProgressByChallengeId(Long challengeId);
}

