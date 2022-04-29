package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.response.FindProjectResDto;
import com.ssafy.ssapilogue.api.dto.response.FindProjectTitleResDto;

import java.util.List;

public interface TechStackService {

    List<FindProjectResDto> searchProjectsByTechStack(String keyword, String userEmail);
    List<FindProjectResDto> searchProjectsByTechStackSpecific(String keyword, String userEmail);
    List<FindProjectTitleResDto> searchProjectTitlesByTechStack(String keyword);
    List<String> searchTechStacks(String keyword);
}
