package com.study.management.mapper;

import com.study.management.entity.StudyApplication;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudyApplicationMapper {
    void insertApplication(StudyApplication application);
    StudyApplication findByStudyIdAndUserId(@Param("studyId") Long studyId, @Param("userId") Long userId);
    List<StudyApplication> findByUserId(@Param("userId") Long userId);
    List<StudyApplication> findByStudyId(@Param("studyId") Long studyId);
    void deleteApplication(@Param("applicationId") Long applicationId);
    int countByStudyId(@Param("studyId") Long studyId);
}