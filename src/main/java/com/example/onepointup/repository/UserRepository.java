package com.example.onepointup.repository;

import com.example.onepointup.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 추가 메서드 예시
    Optional<User> findByUsername(String username); // 사용자 이름으로 조회
}
