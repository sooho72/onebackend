// src/main/java/com/example/onepointup/controller/CommentController.java

package com.example.onepointup.controller;

import com.example.onepointup.dto.CommentDTO;
import com.example.onepointup.model.Comment;
import com.example.onepointup.model.User;
import com.example.onepointup.repository.CommentRepository;
import com.example.onepointup.repository.UserRepository;
import com.example.onepointup.service.CommentService;
import com.example.onepointup.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CommentController {

    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    private final CommentService commentService;
    private final CommentRepository commentRepository;
    private final UserService userService;

    // 댓글 추가하기
    @PostMapping()
    public ResponseEntity<CommentDTO> addComment(@RequestBody CommentDTO commentDTO, Principal principal) {
        if (principal == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }

        // 현재 사용자의 ID를 설정 (실제 구현에서는 Principal을 통해 사용자 정보를 가져와야 합니다)
        // 예를 들어, UserDetails를 사용하여 사용자 정보를 가져올 수 있습니다.
        // 여기서는 예시로 commentDTO에 userId를 설정하는 방식으로 처리합니다.
        // 주의: 클라이언트로부터 받은 userId를 신뢰하지 말고, 서버에서 인증된 사용자 정보를 사용하세요.
        User user = userService.findByUsername(principal.getName());



        commentDTO.setUserId(user.getId());

        CommentDTO savedComment = commentService.addComment(commentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
    }

    // 댓글 삭제하기
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId, Principal principal) {
        if (principal == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Comment not found with id: " + commentId));

        // 현재 사용자가 댓글 작성자인지 확인
        if (!comment.getUser().getUsername().equals(principal.getName())) {
            logger.warn("User {} attempted to delete comment {} without permission", principal.getName(), commentId);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "삭제 권한이 없습니다.");
        }

        commentService.deleteComment(commentId);
        logger.info("User {} deleted comment {}", principal.getName(), commentId);
        return ResponseEntity.noContent().build();
    }

    private Long getUserIdFromPrincipal(Principal principal) {
        // 실제 구현에서는 Principal을 통해 사용자 정보를 가져와야 합니다.
        // 예를 들어, UserDetails를 사용하여 사용자 ID를 가져올 수 있습니다.
        // 여기서는 예시로 1L을 반환합니다. 실제 구현 시 수정하세요.
        return 1L;
    }
    @GetMapping("/challenge/{challengeId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByChallengeId(@PathVariable Long challengeId) {
        try {
            List<CommentDTO> comments = commentService.getCommentsByChallengeId(challengeId);
            return ResponseEntity.ok(comments);
        } catch (Exception ex) {
            logger.error("댓글 조회 중 오류 발생: {}", ex.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "댓글을 조회하는 중 오류가 발생했습니다.");
        }
    }
}
