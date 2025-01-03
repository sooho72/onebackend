package com.example.onepointup.repository;

import com.example.onepointup.model.Role;
import com.example.onepointup.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 사용자 이름으로 사용자 정보를 조회하는 메서드
    User findByUsername(String username);

    // 사용자 역할(Role)을 업데이트하는 메서드
    @Modifying
    @Query("update User set role=:role where username=:username")
    void updateUserRole(@Param("username") String username, @Param("role") Role role);

    // 사용자의 프로필 이미지를 업데이트하는 메서드
    @Modifying
    @Query("UPDATE User u SET u.profileImage = :profileImage WHERE u.username = :username")
    void updateUserProfileImage(@Param("username") String username, @Param("profileImage") byte[] profileImage);

    // ID를 사용해 사용자 정보를 조회하는 메서드
    Optional<User> findById(Long id);
}