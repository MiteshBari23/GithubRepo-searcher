package com.example.test.demo.repository;

import com.example.test.demo.entity.GithubRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public interface GithubRepositoryRepo extends JpaRepository<GithubRepository, UUID> {

    Optional<GithubRepository> findByGithubRepoId(Long id);
}
