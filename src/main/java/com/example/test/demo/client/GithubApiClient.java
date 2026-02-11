package com.example.test.demo.client;

import com.example.test.demo.dto.external.GithubApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GithubApiClient {
    private final RestTemplate restTemplate;
    private final String baseUrl;

    public GithubApiClient(RestTemplate restTemplate, @Value("${github.api.base-url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public GithubApiResponse searchRepository(String query, String language, String sort) {
        String q = query;

        if (language != null && !language.isBlank()) {
            q = q + "+language:" + language;
        }

        String url = baseUrl + "/search/repositories?q=" + q;

        if (sort != null && !sort.isBlank()) {
            url += "&sort=" + sort;
        }

        GithubApiResponse apiResponse;
        apiResponse = restTemplate.getForObject(url, GithubApiResponse.class);
        return apiResponse;
    }
}
