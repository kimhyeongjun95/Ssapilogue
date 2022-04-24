package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.request.CreateProjectReqDto;
import com.ssafy.ssapilogue.api.dto.response.FindProjectDetailResDto;
import com.ssafy.ssapilogue.api.dto.response.FindProjectResDto;

import java.util.List;

public interface ProjectService {

    List<FindProjectResDto> findProjects(String standard, String category, String userId);
    Long createProject(CreateProjectReqDto createProjectReqDto);
    FindProjectDetailResDto findProject(Long projectId, String userId);
    void updateProject(Long projectId, CreateProjectReqDto createProjectReqDto);
    void deleteProject(Long projectId);
}
