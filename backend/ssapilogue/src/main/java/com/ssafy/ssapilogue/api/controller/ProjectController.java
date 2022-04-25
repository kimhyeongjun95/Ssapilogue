package com.ssafy.ssapilogue.api.controller;

import com.ssafy.ssapilogue.api.dto.request.CreateProjectReqDto;
import com.ssafy.ssapilogue.api.dto.response.FindProjectDetailResDto;
import com.ssafy.ssapilogue.api.dto.response.FindProjectResDto;
import com.ssafy.ssapilogue.api.service.BookmarkService;
import com.ssafy.ssapilogue.api.service.JwtTokenProvider;
import com.ssafy.ssapilogue.api.service.LikedService;
import com.ssafy.ssapilogue.api.service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "Project", value = "프로젝트 API")
@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final LikedService likedService;
    private final BookmarkService bookmarkService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping
    @ApiOperation(value = "프로젝트 전체조회", notes = "전체 프로젝트를 조회한다.")
    public ResponseEntity<Map<String, Object>> findProjects(
            @RequestParam @ApiParam(value = "기준") String standard,
            @RequestParam @ApiParam(value = "카테고리") String category,
            HttpServletRequest request) {

        Map<String, Object> result = new HashMap<>();

        String token = jwtTokenProvider.resolveToken(request);
        String userEmail = jwtTokenProvider.getUserEmail(token);

        List<FindProjectResDto> projectList = projectService.findProjects(standard, category, userEmail);
        result.put("projectList", projectList);

        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "프로젝트 등록", notes = "새로운 프로젝트를 등록한다.")
    public ResponseEntity<Map<String, Object>> createProject(
            @RequestBody @ApiParam(value = "프로젝트 정보", required = true) CreateProjectReqDto createProjectReqDto,
            HttpServletRequest request) {

        Map<String, Object> result = new HashMap<>();

        String token = jwtTokenProvider.resolveToken(request);
        String userEmail = jwtTokenProvider.getUserEmail(token);

        Long projectId = projectService.createProject(createProjectReqDto, userEmail);
        result.put("projectId", projectId);

        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}")
    @ApiOperation(value = "프로젝트 상세조회", notes = "프로젝트를 조회한다.")
    public ResponseEntity<Map<String, Object>> findProject(
            @PathVariable @ApiParam(value = "프로젝트 id", required = true, example = "1") Long projectId,
            HttpServletRequest request) {

        Map<String, Object> result = new HashMap<>();

        String token = jwtTokenProvider.resolveToken(request);
        String userEmail = jwtTokenProvider.getUserEmail(token);

        FindProjectDetailResDto project = projectService.findProject(projectId, userEmail);
        result.put("project", project);

        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }

    @PutMapping("/{projectId}")
    @ApiOperation(value = "프로젝트 수정", notes = "프로젝트를 수정한다.")
    public ResponseEntity<Map<String, Object>> updateProject(
            @PathVariable @ApiParam(value = "프로젝트 id", required = true, example = "1") Long projectId,
            @RequestBody @ApiParam(value = "프로젝트 정보", required = true) CreateProjectReqDto createProjectReqDto) {

        Map<String, Object> result = new HashMap<>();

        projectService.updateProject(projectId, createProjectReqDto);
        result.put("status", "SUCCESS");

        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}")
    @ApiOperation(value = "프로젝트 삭제", notes = "프로젝트를 삭제한다.")
    public ResponseEntity<Map<String, Object>> deleteProject(
            @PathVariable @ApiParam(value = "프로젝트 id", required = true, example = "1") Long projectId,
            HttpServletRequest request) {

        Map<String, Object> result = new HashMap<>();

        String token = jwtTokenProvider.resolveToken(request);
        String userEmail = jwtTokenProvider.getUserEmail(token);

        projectService.deleteProject(projectId, userEmail);
        result.put("status", "SUCCESS");

        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }

    @PostMapping("/{projectId}/like")
    @ApiOperation(value = "좋아요 등록", notes = "좋아요를 등록한다.")
    public ResponseEntity<Map<String, Object>> createLike(
            @PathVariable @ApiParam(value = "프로젝트 id", required = true, example = "1") Long projectId,
            HttpServletRequest request) {

        Map<String, Object> result = new HashMap<>();

        String token = jwtTokenProvider.resolveToken(request);
        String userEmail = jwtTokenProvider.getUserEmail(token);

        likedService.createLiked(userEmail, projectId);
        result.put("status", "SUCCESS");

        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/{projectId}/like")
    @ApiOperation(value = "좋아요 취소", notes = "좋아요를 취소한다.")
    public ResponseEntity<Map<String, Object>> deleteLike(
            @PathVariable @ApiParam(value = "프로젝트 id", required = true, example = "1") Long projectId,
            HttpServletRequest request) {

        Map<String, Object> result = new HashMap<>();

        String token = jwtTokenProvider.resolveToken(request);
        String userEmail = jwtTokenProvider.getUserEmail(token);

        likedService.deleteLiked(userEmail, projectId);
        result.put("status", "SUCCESS");

        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }

    @PostMapping("/{projectId}/bookmark")
    @ApiOperation(value = "북마크 등록", notes = "북마크를 등록한다.")
    public ResponseEntity<Map<String, Object>> createBookmark(
            @PathVariable @ApiParam(value = "프로젝트 id", required = true, example = "1") Long projectId,
            HttpServletRequest request) {

        Map<String, Object> result = new HashMap<>();

        String token = jwtTokenProvider.resolveToken(request);
        String userEmail = jwtTokenProvider.getUserEmail(token);

        bookmarkService.createBookmark(userEmail, projectId);
        result.put("status", "SUCCESS");

        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/{projectId}/bookmark")
    @ApiOperation(value = "북마크 취소", notes = "북마크를 취소한다.")
    public ResponseEntity<Map<String, Object>> deleteBookmark(
            @PathVariable @ApiParam(value = "프로젝트 id", required = true, example = "1") Long projectId,
            HttpServletRequest request) {

        Map<String, Object> result = new HashMap<>();

        String token = jwtTokenProvider.resolveToken(request);
        String userEmail = jwtTokenProvider.getUserEmail(token);

        bookmarkService.deleteBookmark(userEmail, projectId);
        result.put("status", "SUCCESS");

        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }
}
