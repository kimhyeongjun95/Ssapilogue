package com.ssafy.ssapilogue.api.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;


import java.net.URI;
import java.util.HashMap;

@RestController
public class UserInfoController {

    @GetMapping("/mmdata")
    public String callAPI() {

        URI uri = UriComponentsBuilder
                .fromUriString("https://meeting.ssafy.com")
                .path("/api/v4/channels/tu7uoyka9jfx3cthbo9uscospo/members")
                .queryParam("per_page", 200)
                .queryParam("page", 1)
                .encode()
                .build()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();

        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .header("authorization", "Bearer 7ma7ftyh9pb6bm5hqpo6wey4co")
                .build();

        ResponseEntity<String> result = restTemplate.exchange(req, String.class);
        return result.getBody();

    }
}
