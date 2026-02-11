package com.example.test.demo.dto.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class GithubApiResponse {
    @JsonProperty("total_count")
    private Integer totalCount;
    List<GithubRepoItemDto> items;
}
