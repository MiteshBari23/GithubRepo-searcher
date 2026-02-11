package com.example.test.demo.service;


import com.example.test.demo.entity.GithubRepository;

import java.util.List;

public interface GithubService {
    List<GithubRepository> searchAndSave(String query, String language, String sort);
    List<GithubRepository> getRepo(String language, Integer minStar, String sort);
}
