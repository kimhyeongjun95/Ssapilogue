package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.request.CreateProjectReqDto;
import com.ssafy.ssapilogue.api.dto.response.FindProjectDetailResDto;
import com.ssafy.ssapilogue.api.dto.response.FindProjectResDto;
import com.ssafy.ssapilogue.api.dto.response.FindProjectTitleResDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProjectService {

    List<FindProjectResDto> findProjects(String userEmail);
    Long createProject(CreateProjectReqDto createProjectReqDto, String userEmail);
    FindProjectDetailResDto findProjectDetail(Long projectId, String userEmail);
    void updateProject(Long projectId, CreateProjectReqDto createProjectReqDto);
    void deleteProject(Long projectId, String userEmail);
    void updateReadme(Long projectId);
    String uploadImage(MultipartFile file, String userEmail);
    List<FindProjectResDto> searchProjectsByTitle(String keyword, String userEmail);
    List<FindProjectTitleResDto> searchProjectTitles(String keyword);
}
