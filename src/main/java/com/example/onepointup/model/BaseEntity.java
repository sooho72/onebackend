package com.example.onepointup.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;


// BaseEntity 클래스는 모든 엔티티 클래스에서 공통으로 사용될 필드와 메서드를 정의한 추상 클래스입니다.
// 이를 통해 코드 재사용성을 높이고, 엔티티 생성 및 수정 시 자동으로 타임스탬프를 관리합니다.
@MappedSuperclass // 이 클래스를 JPA 엔티티의 부모 클래스로 설정하여 필드가 하위 엔티티에 매핑되도록 지정합니다.
@Getter
@Setter
public abstract class BaseEntity {

    // 엔티티 생성 시 자동으로 설정되는 생성일자 필드입니다.
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // 엔티티 수정 시 자동으로 설정되는 수정일자 필드입니다.
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // 엔티티가 처음 저장되기 전 실행되는 메서드로, 생성일자와 수정일자를 현재 시각으로 설정합니다.
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // 엔티티가 수정되기 전 실행되는 메서드로, 수정일자를 현재 시각으로 업데이트합니다.
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}