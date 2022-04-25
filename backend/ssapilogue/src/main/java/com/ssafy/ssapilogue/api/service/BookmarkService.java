package com.ssafy.ssapilogue.api.service;

public interface BookmarkService {

    void createBookmark(String userEmail, Long projectId);
    void deleteBookmark(String userEmail, Long projectId);
}
