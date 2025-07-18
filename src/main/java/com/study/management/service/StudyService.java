package com.study.management.service;

import com.study.management.entity.Study;
import com.study.management.mapper.StudyMapper;
import com.study.management.mapper.StudyApplicationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyService {

    private final StudyMapper studyMapper;
    private final StudyApplicationMapper applicationMapper;

    public List<Study> getAllStudies(int page, int size) {
        int offset = page * size;
        return studyMapper.findAllStudies(offset, size);
    }

    public List<Study> searchStudies(String keyword, int page, int size) {
        int offset = page * size;
        return studyMapper.searchStudies(keyword, offset, size);
    }

    public Study getStudyById(Long studyId) {
        return studyMapper.findById(studyId);
    }

    public Study getStudyWithApplicationStatus(Long studyId, Long userId) {
        Study study = studyMapper.findById(studyId);
        if (study != null && userId != null) {
            study.setAlreadyApplied(applicationMapper.findByStudyIdAndUserId(studyId, userId) != null);
            study.setCanApply(study.getCurrentParticipants() < study.getMaxParticipants() 
                && LocalDate.now().isBefore(study.getDeadline()) 
                && !study.isAlreadyApplied());
        }
        return study;
    }

    public void createStudy(Study study) {
        study.setStatus("RECRUITING");
        study.setCurrentParticipants(0);
        studyMapper.insertStudy(study);
    }

    public void updateStudy(Study study) {
        studyMapper.updateStudy(study);
    }

    public void deleteStudy(Long studyId) {
        studyMapper.deleteStudy(studyId);
    }

    public List<Study> getStudiesByCreator(Long creatorId) {
        return studyMapper.findByCreatorId(creatorId);
    }

    public int getTotalStudyCount() {
        return studyMapper.countAllStudies();
    }

    public int getSearchStudyCount(String keyword) {
        return studyMapper.countSearchStudies(keyword);
    }
}