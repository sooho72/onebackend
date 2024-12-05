package com.example.onepointup.service;

import com.example.onepointup.dto.UserDTO;

public interface UserService {
    UserDTO createUser(UserDTO userDTO); // 회원가입
    UserDTO getUserById(Long id);        // 사용자 조회
    UserDTO getUserByUsername(String username); // 사용자 조회 (username)
    UserDTO updateUser(Long id, UserDTO userDTO); // 사용자 정보 수정
    void deleteUser(Long id);           // 사용자 삭제
}
