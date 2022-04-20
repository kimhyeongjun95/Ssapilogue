package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.request.CreateProjectReqDto;
import com.ssafy.ssapilogue.api.dto.response.FindProjectDetailResDto;
import com.ssafy.ssapilogue.api.dto.response.FindProjectResDto;

import java.util.List;

public interface ProjectService {

    List<FindProjectResDto> findProjects(String category);
    Long createProject(CreateProjectReqDto createProjectReqDto);
    FindProjectDetailResDto findProject(Long projectId);
    void updateProject(Long projectId, CreateProjectReqDto createProjectReqDto);
    void deleteProject(Long projectId);
}
