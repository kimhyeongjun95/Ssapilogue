package com.ssafy.ssapilogue.api.controller;

import com.ssafy.ssapilogue.api.dto.request.CreateProjectReqDto;
import com.ssafy.ssapilogue.api.service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "Project", value = "프로젝트 API")
@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    @ApiOperation(value = "프로젝트 등록", notes = "새로운 프로젝트를 등록한다.")
    public ResponseEntity<Map<String, Object>> createProject(
            @RequestBody @ApiParam(value = "프로젝트 정보", required = true) CreateProjectReqDto createProjectReqDto) {

        Map<String, Object> result = new HashMap<>();

        Long projectId = projectService.createProject(createProjectReqDto);
        result.put("projectId", projectId);

        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{projectId}")
    @ApiOperation(value = "프로젝트 삭제", notes = "프로젝트를 삭제한다.")
    public ResponseEntity<Map<String, Object>> deleteProject(
            @PathVariable @ApiParam(value = "프로젝트 id", required = true) Long projectId) {

        Map<String, Object> result = new HashMap<>();

        projectService.deleteProject(projectId);
        result.put("status", "SUCCESS");

        return ResponseEntity.ok().body(result);
    }
}
