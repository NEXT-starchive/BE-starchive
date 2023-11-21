package com.example.starchive.entity;


import com.example.starchive.config.auth.LoginCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    private String userId;

    @Column
    private String textballoon;

    @Column
    private String name;

    @Column
    private LocalDateTime firstday;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @CreatedDate
    @Column(updatable = false) // 생성일자(createdDate)에 대한 정보는 생성시에만 할당 가능, 갱신 불가
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    public void changeTextBalloon(String textballoon) {
        this.textballoon = textballoon;
    }

    public void changeFirstday(LocalDateTime firstday) {
        this.firstday = firstday;
    }

    @Builder
    public User(String userId, String name, String textballoon, LocalDateTime firstday, Role role) {
        this.userId = userId;
        this.name = name;
        this.textballoon = textballoon;
        this.firstday = firstday;
        this.role = role;
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }

    public void register() {
        this.role = Role.CUSTOMER;
    }


}
