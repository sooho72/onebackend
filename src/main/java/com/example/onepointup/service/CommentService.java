package com.example.onepointup.service;

import com.example.onepointup.dto.CommentDTO;

import java.util.List;

public interface CommentService {
    CommentDTO addComment(CommentDTO commentDTO);
    void deleteComment(Long commentId);
    List<CommentDTO> getCommentsByChallengeId(Long challengeId);
}