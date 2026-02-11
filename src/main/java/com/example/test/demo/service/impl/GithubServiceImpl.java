package com.example.test.demo.service.impl;

import com.example.test.demo.client.GithubApiClient;
import com.example.test.demo.dto.external.GithubApiResponse;
import com.example.test.demo.dto.external.GithubRepoItemDto;
import com.example.test.demo.entity.GithubRepository;
import com.example.test.demo.repository.GithubRepositoryRepo;
import com.example.test.demo.service.GithubService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GithubServiceImpl implements GithubService {
    private final GithubApiClient githubApiClient;
    private final GithubRepositoryRepo repository;

    public GithubServiceImpl(GithubApiClient githubApiClient, GithubRepositoryRepo githubRepositoryRepo) {
        this.githubApiClient = githubApiClient;
        this.repository = githubRepositoryRepo;
    }

    @Override
    public List<GithubRepository> searchAndSave(String query, String language, String sort) {

        GithubApiResponse response = githubApiClient.searchRepository(query, language, sort);
        if(response == null || response.getItems() == null || response.getItems().isEmpty()){
            return Collections.emptyList();
        }

        List<GithubRepository> savedRepo = new ArrayList<>();

        for(GithubRepoItemDto item: response.getItems()){
            Optional<GithubRepository> existing = repository.findByGithubRepoId(item.getId());

            GithubRepository entity;

            if(existing.isPresent()){
                entity = existing.get();
            }else {
                entity = new GithubRepository();
                entity.setGithubRepoId(item.getId());
            }

            entity.setName(item.getName());
            entity.setDescription(item.getDescription());

            entity.setOwnerName(
                    item.getOwner() != null && item.getOwner().getLogin() != null
                            ? item.getOwner().getLogin()
                            : "Unknown"
            );

            entity.setLanguage(
                    item.getLanguage() != null
                            ? item.getLanguage()
                            : "Unknown"
            );

            entity.setStarsCount(
                    item.getStargazersCount() != null
                            ? item.getStargazersCount()
                            : 0
            );

            entity.setForksCount(
                    item.getForksCount() != null
                            ? item.getForksCount()
                            : 0
            );

            entity.setLastUpdated(item.getUpdatedAt());


            GithubRepository saved = repository.save(entity);
            savedRepo.add(entity);
        }
        return savedRepo;

    }


    @Override
    public List<GithubRepository> getRepo(String language, Integer minStar, String sort) {
        List<GithubRepository> repos = repository.findAll();
        if(language != null && !language.isBlank()){
            repos = repos.stream()
                    .filter(repo -> repo.getLanguage() != null && repo.getLanguage()
                            .equalsIgnoreCase(language)).toList();
        }

        if(minStar != null){
            repos = repos.stream()
                    .filter(repo -> repo.getStarsCount() >= minStar)
                    .toList();
        }

        if(sort != null){
            switch (sort.toLowerCase()){
                case "stars" ->
                    repos.sort((a, b) ->Integer.compare(b.getStarsCount(),a.getStarsCount()));

                case "forks" ->
                    repos.sort((a, b) -> Integer.compare(b.getForksCount(), a.getForksCount()));

                case "updated" ->
                    repos.sort((a, b) -> b.getLastUpdated().compareTo(a.getLastUpdated()));
            }
        }
        return repos;
    }
}
