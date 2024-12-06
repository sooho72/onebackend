package com.example.onepointup.service;

import com.example.onepointup.dto.ChallengeDTO;

import java.util.List;

public interface ChallengeService {
    ChallengeDTO createChallenge(ChallengeDTO challengeDTO, String username);
    ChallengeDTO getChallengeById(Long challengeId);
    List<ChallengeDTO> getChallengesByUser(Long userId);
    ChallengeDTO updateChallenge(Long challengeId, ChallengeDTO challengeDTO);
    void deleteChallenge(Long challengeId);
}
