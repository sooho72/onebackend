package com.example.onepointup.controller;

import com.example.onepointup.dto.ChallengeDTO;
import com.example.onepointup.service.ChallengeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/challenges")
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeService challengeService;

    @PostMapping
    public ResponseEntity<ChallengeDTO> createChallenge(
            @RequestBody ChallengeDTO challengeDTO,
            @RequestParam Long userId) {
        ChallengeDTO createdChallenge = challengeService.createChallenge(challengeDTO, userId);
        return new ResponseEntity<>(createdChallenge, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChallengeDTO> getChallengeById(@PathVariable Long id) {
        ChallengeDTO challenge = challengeService.getChallengeById(id);
        return new ResponseEntity<>(challenge, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChallengeDTO> updateChallenge(
            @PathVariable Long id,
            @RequestBody ChallengeDTO challengeDTO) {
        ChallengeDTO updatedChallenge = challengeService.updateChallenge(id, challengeDTO);
        return new ResponseEntity<>(updatedChallenge, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChallenge(@PathVariable Long id) {
        challengeService.deleteChallenge(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
