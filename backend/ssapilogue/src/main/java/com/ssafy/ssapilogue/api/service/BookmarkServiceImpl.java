package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.core.domain.Bookmark;
import com.ssafy.ssapilogue.core.domain.Project;
import com.ssafy.ssapilogue.core.domain.User;
import com.ssafy.ssapilogue.core.repository.BookmarkRepsitory;
import com.ssafy.ssapilogue.core.repository.ProjectRepository;
import com.ssafy.ssapilogue.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class BookmarkServiceImpl implements BookmarkService{

    private final BookmarkRepsitory bookmarkRepsitory;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    @Override
    public void createBookmark(String userId, Long projectId) {
        User user = userRepository.getById(userId);
        Project project = projectRepository.getById(projectId);
        Bookmark bookmark = Bookmark.builder()
                .user(user)
                .project(project)
                .build();
        bookmarkRepsitory.save(bookmark);
    }

    @Override
    public void deleteBookmark(String userId, Long projectId) {
        User user = userRepository.getById(userId);
        Project project = projectRepository.getById(projectId);
        bookmarkRepsitory.deleteByUserAndProject(user, project);
    }
}
