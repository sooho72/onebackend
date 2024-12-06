package com.example.onepointup.controller;

import com.example.onepointup.dto.UserDTO;
import com.example.onepointup.model.Role;
import com.example.onepointup.security.UserPrinciple;
import com.example.onepointup.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("change/{role}")
    public ResponseEntity<Object> changeRole(
            @AuthenticationPrincipal UserPrinciple userPrinciple,
            @PathVariable Role role) {
        userService.changeRole(role,userPrinciple.getUsername());
        return ResponseEntity.ok(true);
    }
}
