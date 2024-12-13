package com.example.onepointup.controller;

import com.example.onepointup.dto.ChallengeDTO;
import com.example.onepointup.model.User;
import com.example.onepointup.security.UserPrinciple;
import com.example.onepointup.service.ChallengeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Log4j2
@RestController
@RequestMapping("/api/challenges")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ChallengeController {

    private final ChallengeService challengeService;

    @PostMapping("/register")
    public ResponseEntity<ChallengeDTO> createChallenge(
            @RequestBody ChallengeDTO challengeDTO,
            @AuthenticationPrincipal UserPrinciple userPrincipal) {
        log.info("Creating challenge: " + challengeDTO);
        ChallengeDTO createdChallenge = challengeService.createChallenge(challengeDTO, userPrincipal.getUsername());
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

    // **모든 챌린지 가져오기** 추가
    @GetMapping
    public ResponseEntity<List<ChallengeDTO>> getAllChallenges()
    {
        List<ChallengeDTO> challenges = challengeService.getAllChallenges();
        log.info("Challenges: "+challenges.get(0)) ;
        return new ResponseEntity<>(challenges, HttpStatus.OK);
    }

}