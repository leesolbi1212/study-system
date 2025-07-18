package com.study.management.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Long userId;
    private String username;
    private String email;
    private String password;
    private String fullName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}