package com.ssafy.ssapilogue.api.service;

public interface LikedService {

    void createLiked(String userEmail, Long projectId);
    void deleteLiked(String userEmail, Long projectId);
}
