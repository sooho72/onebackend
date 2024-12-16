// src/main/java/com/example/onepointup/service/CommentServiceImpl.java

package com.example.onepointup.service;

import com.example.onepointup.dto.CommentDTO;
import com.example.onepointup.model.Challenge;
import com.example.onepointup.model.Comment;
import com.example.onepointup.model.User;
import com.example.onepointup.repository.ChallengeRepository;
import com.example.onepointup.repository.CommentRepository;
import com.example.onepointup.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;

    @Override
    public CommentDTO addComment(CommentDTO commentDTO) {
        Challenge challenge = challengeRepository.findById(commentDTO.getChallengeId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Challenge not found with id: " + commentDTO.getChallengeId()));

        User user = userRepository.findById(commentDTO.getUserId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found with id: " + commentDTO.getUserId()));

        Comment comment = Comment.builder()
                .challenge(challenge)
                .user(user)
                .content(commentDTO.getContent())
                .build();

        Comment savedComment = commentRepository.save(comment);
        return toDTO(savedComment);
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Comment not found with id: " + commentId));
        commentRepository.delete(comment);
    }
    @Override
    public List<CommentDTO> getCommentsByChallengeId(Long challengeId) {
        List<Comment> comments = commentRepository.findByChallengeId(challengeId);
        return comments.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private CommentDTO toDTO(Comment comment) {
        return CommentDTO.builder()
                .id(comment.getId())
                .userId(comment.getUser().getId())
                .username(comment.getUser().getUsername())
                .challengeId(comment.getChallenge().getId())
                .content(comment.getContent())
                .name(comment.getUser().getName())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }
}
