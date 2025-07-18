package com.study.management.mapper;

import com.study.management.entity.Study;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudyMapper {
    List<Study> findAllStudies(@Param("offset") int offset, @Param("limit") int limit);
    List<Study> searchStudies(@Param("keyword") String keyword, @Param("offset") int offset, @Param("limit") int limit);
    Study findById(@Param("studyId") Long studyId);
    void insertStudy(Study study);
    void updateStudy(Study study);
    void deleteStudy(@Param("studyId") Long studyId);
    List<Study> findByCreatorId(@Param("creatorId") Long creatorId);
    int countAllStudies();
    int countSearchStudies(@Param("keyword") String keyword);
    void updateCurrentParticipants(@Param("studyId") Long studyId, @Param("increment") int increment);
}