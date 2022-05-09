package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.request.CreateProjectCommentReqDto;
import com.ssafy.ssapilogue.api.exception.CustomException;
import com.ssafy.ssapilogue.api.exception.ErrorCode;
import com.ssafy.ssapilogue.core.domain.Project;
import com.ssafy.ssapilogue.core.domain.ProjectComment;
import com.ssafy.ssapilogue.core.domain.User;
import com.ssafy.ssapilogue.core.repository.ProjectCommentRepository;
import com.ssafy.ssapilogue.core.repository.ProjectRepository;
import com.ssafy.ssapilogue.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class ProjectCommentServiceImpl implements ProjectCommentService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ProjectCommentRepository projectCommentRepository;

    // 댓글 등록
    @Override
    public Long createComment(Long projectId, CreateProjectCommentReqDto createProjectCommentReqDto, String userEmail) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new CustomException(ErrorCode.PROJECT_NOT_FOUND));

        User user = userRepository.findByEmail(userEmail);

        ProjectComment projectComment = ProjectComment.builder()
                .content(createProjectCommentReqDto.getContent())
                .project(project)
                .user(user)
                .build();
        ProjectComment saveComment = projectCommentRepository.save(projectComment);

        return saveComment.getId();
    }

    // 댓글 삭제
    @Override
    public void deleteComment(Long commentId) {
        projectCommentRepository.deleteById(commentId);
    }
}
