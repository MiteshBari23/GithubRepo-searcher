package com.example.test.demo.dto.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class GithubRepoItemDto {
    private Long id;
    private String name;
    private String description;
    private String language;
    @JsonProperty("stargazers_count")
    private Integer stargazersCount;
    @JsonProperty("fork_count")
    private Integer forksCount;
    @JsonProperty("updated_at")
    private Instant updatedAt;
    private OwnerDto owner;
}
