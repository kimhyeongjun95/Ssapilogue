package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.request.CreateProjectReqDto;
import com.ssafy.ssapilogue.api.dto.response.FindProjectDetailResDto;
import com.ssafy.ssapilogue.api.dto.response.FindProjectResDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProjectService {

    List<FindProjectResDto> findProjects(String standard, String category, String userEmail);
    Long createProject(CreateProjectReqDto createProjectReqDto, String userEmail);
    FindProjectDetailResDto findProject(Long projectId, String userEmail);
    void updateProject(Long projectId, CreateProjectReqDto createProjectReqDto);
    void deleteProject(Long projectId, String userEmail);
    String uploadImage(MultipartFile file, String userEmail);
}
