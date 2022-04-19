package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.request.CreateProjectReqDto;

public interface ProjectService {
    Long createProject(CreateProjectReqDto createProjectReqDto);
    void deleteProject(Long projectId);
}
