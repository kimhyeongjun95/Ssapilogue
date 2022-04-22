package com.ssafy.ssapilogue.api.service;

public interface BookmarkService {
    void createBookmark(String userId, Long projectId);
    void deleteBookmark(String userId, Long projectId);
}
