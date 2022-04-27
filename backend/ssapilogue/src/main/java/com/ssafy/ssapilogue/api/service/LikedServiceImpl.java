package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.core.domain.Liked;
import com.ssafy.ssapilogue.core.domain.Project;
import com.ssafy.ssapilogue.core.domain.User;
import com.ssafy.ssapilogue.core.repository.LikedRepository;
import com.ssafy.ssapilogue.core.repository.ProjectRepository;
import com.ssafy.ssapilogue.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class LikedServiceImpl implements LikedService{

    private final LikedRepository likedRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    @Override
    public void createLiked(String userEmail, Long projectId) {
        User user = userRepository.findByEmail(userEmail);
        Project project = projectRepository.getById(projectId);
        Liked liked = Liked.builder()
                .user(user)
                .project(project)
                .build();
        likedRepository.save(liked);
    }

    @Override
    public void deleteLiked(String userEmail, Long projectId) {
        User user = userRepository.findByEmail(userEmail);
        Project project = projectRepository.getById(projectId);
        likedRepository.deleteByUserAndProject(user, project);
    }
}
