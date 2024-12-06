package com.example.onepointup.service;

import com.example.onepointup.dto.UserDTO;
import com.example.onepointup.model.Role;
import com.example.onepointup.model.User;

import java.util.List;

public interface UserService {
    User findByUsername(String username);
    User saveUser(User user);
    UserDTO getUserById(Long id);        // 사용자 조회
    UserDTO getUserByUsername(String username); // 사용자 조회 (username)
    UserDTO updateUser(Long id, UserDTO userDTO); // 사용자 정보 수정
    void deleteUser(Long id);           // 사용자 삭제
    void changeRole(Role newRole, String username);

    List<User> findAllUsers();
}