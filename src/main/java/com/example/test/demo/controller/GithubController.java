package com.example.test.demo.controller;

import com.example.test.demo.dto.request.GithubSearchRequest;
import com.example.test.demo.dto.response.GithubRepoResponse;
import com.example.test.demo.entity.GithubRepository;
import com.example.test.demo.service.GithubService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/github")
public class GithubController {

    private final GithubService githubService;

    public GithubController(GithubService githubService) {
        this.githubService = githubService;
    }

    @PostMapping("/search")
    public ResponseEntity<List<GithubRepoResponse>> searchRepo(@Valid @RequestBody GithubSearchRequest request) {
        List<GithubRepository> repositories = githubService.searchAndSave(
                request.getQuery(),
                request.getLanguage(),
                request.getSort());

        List<GithubRepoResponse> repoResponses = repositories.stream().map(this::mapToResponse).collect(Collectors.toList());
        return ResponseEntity.ok(repoResponses);

    }

    private GithubRepoResponse mapToResponse(GithubRepository entity) {

        GithubRepoResponse response = new GithubRepoResponse();

        response.setGithubRepoId(entity.getGithubRepoId());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        response.setOwnerName(entity.getOwnerName());
        response.setLanguage(entity.getLanguage());
        response.setStarsCount(entity.getStarsCount());
        response.setForkCount(entity.getForksCount());
        response.setLastUpdated(entity.getLastUpdated());
        return response;
    }

    @GetMapping("/repositories")
    public ResponseEntity<List<GithubRepoResponse>> getRepos(@RequestParam(required = false) String language,
                                                             @RequestParam(required = false) Integer minStars,
                                                             @RequestParam(required = false) String sort) {
        List<GithubRepository> repos = githubService.getRepo(language, minStars, sort);

        List<GithubRepoResponse> responses = repos.stream().map(this::mapToResponse).toList();
        return ResponseEntity.ok(responses);
    }
    @GetMapping("/ping")
    public String ping() {
        return "working";
    }
}
