package com.study.management.entity;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Study {
    private Long studyId;
    private String title;
    private String description;
    private Integer maxParticipants;
    private Integer currentParticipants;
    private LocalDate deadline;
    private String status;
    private Long creatorId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Additional fields for display
    private String creatorName;
    private boolean canApply;
    private boolean alreadyApplied;
}