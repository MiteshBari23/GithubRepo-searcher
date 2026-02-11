package com.example.test.demo.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class GithubRepoResponse {

    private Long githubRepoId;
    private String name;
    private String description;
    private String ownerName;
    private String language;
    private Integer starsCount;
    private Integer forkCount;
    private Instant lastUpdated;
}
