package com.example.onepointup.service;

import com.example.onepointup.dto.CommentDTO;
import com.example.onepointup.model.Comment;
import com.example.onepointup.model.Journal;
import com.example.onepointup.model.User;
import com.example.onepointup.repository.CommentRepository;
import com.example.onepointup.repository.JournalRepository;
import com.example.onepointup.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final JournalRepository journalRepository;
    private final UserRepository userRepository;

    @Override
    public CommentDTO createComment(CommentDTO commentDTO) {
        Journal journal = journalRepository.findById(commentDTO.getJournalId())
                .orElseThrow(() -> new RuntimeException("Journal not found"));

        User user = userRepository.findById(commentDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Comment comment = Comment.builder()
                .journal(journal)
                .user(user)  // 작성자 설정
                .content(commentDTO.getContent())
                .isPositive(commentDTO.getIsPositive())
                .build();

        comment = commentRepository.save(comment);
        return toDTO(comment);
    }

    @Override
    public List<CommentDTO> getCommentsByJournal(Long journalId) {
        List<Comment> comments = commentRepository.findByJournalId(journalId);
        return comments.stream().map(this::toDTO).toList();
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        commentRepository.delete(comment);
    }

    private CommentDTO toDTO(Comment comment) {
        return CommentDTO.builder()
                .id(comment.getId())
                .journalId(comment.getJournal().getId())
                .userId(comment.getUser().getId())  // 작성자 ID 추가
                .content(comment.getContent())
                .isPositive(comment.getIsPositive())
                .build();
    }
}
