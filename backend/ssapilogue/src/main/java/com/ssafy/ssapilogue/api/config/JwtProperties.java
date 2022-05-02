package com.ssafy.ssapilogue.api.config;

public interface JwtProperties {
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
    String REFRESH_TOKEN_HEADER_STRING = "RefreshToken";
}
