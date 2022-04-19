package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.request.CreateProjectReqDto;
import com.ssafy.ssapilogue.api.dto.response.FindProjectResDto;

import java.util.List;

public interface ProjectService {

    List<FindProjectResDto> findProjects(String category);
    Long createProject(CreateProjectReqDto createProjectReqDto);
    void deleteProject(Long projectId);
}
