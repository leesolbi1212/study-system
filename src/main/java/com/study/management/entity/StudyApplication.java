package com.study.management.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudyApplication {
    private Long applicationId;
    private Long studyId;
    private Long userId;
    private String status;
    private LocalDateTime appliedAt;
    
    // Additional fields for display
    private String studyTitle;
    private String userName;
}