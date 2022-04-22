package com.ssafy.ssapilogue.api.service;

public interface LikedService {
    void createLiked(String userId, Long projectId);
    void deleteLiked(String userId, Long projectId);
}
