package com.study.management.service;

import com.study.management.entity.StudyApplication;
import com.study.management.mapper.StudyApplicationMapper;
import com.study.management.mapper.StudyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyApplicationService {

    private final StudyApplicationMapper applicationMapper;
    private final StudyMapper studyMapper;

    public void applyToStudy(Long studyId, Long userId) {
        StudyApplication existingApplication = applicationMapper.findByStudyIdAndUserId(studyId, userId);
        if (existingApplication != null) {
            throw new IllegalArgumentException("Already applied to this study");
        }

        int currentApplications = applicationMapper.countByStudyId(studyId);
        var study = studyMapper.findById(studyId);
        
        if (currentApplications >= study.getMaxParticipants()) {
            throw new IllegalArgumentException("Study is full");
        }

        StudyApplication application = StudyApplication.builder()
                .studyId(studyId)
                .userId(userId)
                .status("APPLIED")
                .build();

        applicationMapper.insertApplication(application);
        studyMapper.updateCurrentParticipants(studyId, 1);
    }

    public void cancelApplication(Long applicationId, Long userId) {
        StudyApplication application = applicationMapper.findByStudyIdAndUserId(null, userId);
        if (application == null || !application.getApplicationId().equals(applicationId)) {
            throw new IllegalArgumentException("Invalid application");
        }

        applicationMapper.deleteApplication(applicationId);
        studyMapper.updateCurrentParticipants(application.getStudyId(), -1);
    }

    public List<StudyApplication> getApplicationsByUser(Long userId) {
        return applicationMapper.findByUserId(userId);
    }

    public List<StudyApplication> getApplicationsByStudy(Long studyId) {
        return applicationMapper.findByStudyId(studyId);
    }

    public boolean hasApplied(Long studyId, Long userId) {
        return applicationMapper.findByStudyIdAndUserId(studyId, userId) != null;
    }
}