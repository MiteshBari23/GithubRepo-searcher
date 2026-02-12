package com.example.test.demo;

import com.example.test.demo.client.GithubApiClient;
import com.example.test.demo.dto.external.GithubApiResponse;
import com.example.test.demo.dto.external.GithubRepoItemDto;
import com.example.test.demo.dto.external.OwnerDto;
import com.example.test.demo.entity.GithubRepository;
import com.example.test.demo.repository.GithubRepositoryRepo;
import com.example.test.demo.service.impl.GithubServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GithubServiceImplTest {

    @Mock
    private GithubApiClient githubApiClient;

    @Mock
    private GithubRepositoryRepo githubRepositoryRepo;

    @InjectMocks
    private GithubServiceImpl githubService;

    @Test
    void shouldSaveRepoFromGithub() {

        GithubRepoItemDto item = new GithubRepoItemDto();
        item.setId(1L);
        item.setName("test");
        item.setDescription("testing");
        item.setLanguage("Java");
        item.setForksCount(10);
        item.setStargazersCount(20);
        item.setUpdatedAt(Instant.now());

        OwnerDto ownerDto = new OwnerDto();
        ownerDto.setLogin("testUser");
        item.setOwner(ownerDto);

        GithubApiResponse githubApiResponse = new GithubApiResponse();
        githubApiResponse.setItems(List.of(item));

        when(githubApiClient.searchRepository(anyString(), anyString(), anyString())).thenReturn(githubApiResponse);

        when(githubRepositoryRepo.save(any(GithubRepository.class)))
                .thenAnswer(invocation -> invocation.getArgument(0, GithubRepository.class));

        List<GithubRepository> result = githubService.searchAndSave("spring","java", "stars");

        assertEquals(1, result.size());
        verify(githubRepositoryRepo, times(1)).save(any());
    }
}

