package com.example.onepointup.service;

import com.example.onepointup.dto.ChallengeDTO;
import com.example.onepointup.model.Challenge;

import java.util.List;

public interface ChallengeService {
    ChallengeDTO createChallenge(ChallengeDTO challengeDTO, String username);

    ChallengeDTO getChallengeById(Long challengeId);

    List<ChallengeDTO> getChallengesByUser(Long userId);

    ChallengeDTO updateChallenge(Long challengeId, ChallengeDTO challengeDTO);

    void deleteChallenge(Long challengeId);

    List<ChallengeDTO> getAllChallenges();

    default ChallengeDTO entitytodto(Challenge challenge) {
        ChallengeDTO challengeDTO = ChallengeDTO.builder()
                .id(challenge.getId())
                .username(challenge.getUser().getUsername()) // 작성자 username
                .name(challenge.getUser().getName())         // 작성자 이름
                .title(challenge.getTitle())
                .description(challenge.getDescription())
                .startDate(challenge.getStartDate())
                .endDate(challenge.getEndDate())
                .progress(challenge.getProgress())
                .isCompleted(challenge.getIsCompleted())
                .isPublic(challenge.getIsPublic())
                .build();

        return challengeDTO;
    }

}