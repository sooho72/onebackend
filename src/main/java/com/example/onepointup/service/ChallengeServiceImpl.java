package com.example.onepointup.service;

import com.example.onepointup.dto.ChallengeDTO;
import com.example.onepointup.model.Challenge;
import com.example.onepointup.model.User;
import com.example.onepointup.repository.ChallengeRepository;
import com.example.onepointup.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChallengeServiceImpl implements ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public ChallengeDTO createChallenge(ChallengeDTO challengeDTO, String username) {
        User user = userRepository.findByUsername(username);

        Challenge challenge = Challenge.builder()
                .title(challengeDTO.getTitle())
                .description(challengeDTO.getDescription())
                .startDate(challengeDTO.getStartDate())
                .endDate(challengeDTO.getEndDate())
                .isCompleted(challengeDTO.getIsCompleted())
                .isPublic(challengeDTO.getIsPublic())
                .progress(0.0f)
                .user(user)
                .build();

        challenge = challengeRepository.save(challenge);
        return toDTO(challenge);
    }

    @Override
    public ChallengeDTO getChallengeById(Long challengeId) {
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new RuntimeException("Challenge not found"));
        return toDTO(challenge);
    }

    @Override
    public List<ChallengeDTO> getChallengesByUser(Long userId) {
        List<Challenge> challenges = challengeRepository.findByUserId(userId);
        return challenges.stream().map(this::toDTO).toList();
    }

    @Override
    public ChallengeDTO updateChallenge(Long challengeId, ChallengeDTO challengeDTO) {
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new RuntimeException("Challenge not found"));

        challenge.setTitle(challengeDTO.getTitle());
        challenge.setDescription(challengeDTO.getDescription());
        challenge.setStartDate(challengeDTO.getStartDate());
        challenge.setEndDate(challengeDTO.getEndDate());
        challenge.setIsPublic(challengeDTO.getIsPublic());

        challenge = challengeRepository.save(challenge);
        return toDTO(challenge);
    }

    @Override
    public void deleteChallenge(Long challengeId) {
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new RuntimeException("Challenge not found"));
        challengeRepository.delete(challenge);
    }

    private ChallengeDTO toDTO(Challenge challenge) {
        return ChallengeDTO.builder()
                .id(challenge.getId())
                .title(challenge.getTitle())
                .description(challenge.getDescription())
                .startDate(challenge.getStartDate())
                .endDate(challenge.getEndDate())
                .progress(challenge.getProgress())
                .isCompleted(challenge.getIsCompleted())
                .isPublic(challenge.getIsPublic())
                .build();
    }
    @Override
    public List<ChallengeDTO> getAllChallenges() {
        return challengeRepository.findAll()
                .stream()
                .map(challenge -> entitytodto(challenge))
                .collect(Collectors.toList());

    }

}
