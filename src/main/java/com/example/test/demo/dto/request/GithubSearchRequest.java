package com.example.test.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GithubSearchRequest {

    @NotBlank(message = "Query must not be blank")
    private String query;

    private String language;
    private String sort;
}
