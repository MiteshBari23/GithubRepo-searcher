package com.example.test.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "github_repositories")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GithubRepository {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "github_repo_id", nullable = false, unique = true)
    private Long githubRepoId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "owner_name", nullable = false)
    private String ownerName;

    @Column(name = "language", nullable = false)
    private String language;

    @Column(name = "stars_count")
    private Integer starsCount;

    @Column(name = "fork_count")
    private Integer forksCount;

    @Column(name = "last_updated", nullable = false)
    private Instant lastUpdated;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GithubRepository that)) return false;
        return Objects.equals(githubRepoId, that.githubRepoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(githubRepoId);
    }
}
