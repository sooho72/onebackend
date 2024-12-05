package com.example.onepointup.service;

import com.example.onepointup.dto.CommentDTO;

import java.util.List;

public interface CommentService {
    CommentDTO createComment(CommentDTO commentDTO);
    List<CommentDTO> getCommentsByJournal(Long journalId);
    void deleteComment(Long commentId);
}
