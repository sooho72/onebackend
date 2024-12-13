package com.example.onepointup.controller;

import com.example.onepointup.dto.CommentDTO;
import com.example.onepointup.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO) {
        CommentDTO createdComment = commentService.createComment(commentDTO);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @GetMapping("/journal/{journalId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByJournal(@PathVariable Long journalId) {
        List<CommentDTO> comments = commentService.getCommentsByJournal(journalId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

}
