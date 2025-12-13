package com.staffinity.recruiting.candidates.application.usecases;

import com.staffinity.recruiting.candidates.domain.model.Candidate;
import com.staffinity.recruiting.candidates.domain.ports.out.CandidateRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListCandidatesServiceTest {

    @Mock
    private CandidateRepositoryPort candidateRepository;

    @InjectMocks
    private ListCandidatesService listCandidatesService;

    private Candidate candidate1;
    private Candidate candidate2;
    private List<Candidate> candidates;

    @BeforeEach
    void setUp() {
        candidate1 = new Candidate(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "John",
                "Doe",
                "john.doe@example.com",
                "+1234567890",
                "http://example.com/resume.pdf",
                "http://linkedin.com/in/johndoe",
                "http://johndoe.com",
                "APPLIED",
                "LinkedIn",
                null
        );

        candidate2 = new Candidate(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "Jane",
                "Smith",
                "jane.smith@example.com",
                "+0987654321",
                "http://example.com/resume2.pdf",
                "http://linkedin.com/in/janesmith",
                "http://janesmith.com",
                "INTERVIEWING",
                "Indeed",
                null
        );

        candidates = Arrays.asList(candidate1, candidate2);
    }

    @Test
    void shouldReturnAllCandidates() {
        // Given
        when(candidateRepository.findAll()).thenReturn(candidates);

        // When
        List<Candidate> result = listCandidatesService.listCandidates();

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getFirstName()).isEqualTo("John");
        assertThat(result.get(1).getFirstName()).isEqualTo("Jane");

        verify(candidateRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnEmptyListWhenNoCandidates() {
        // Given
        when(candidateRepository.findAll()).thenReturn(Arrays.asList());

        // When
        List<Candidate> result = listCandidatesService.listCandidates();

        // Then
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();

        verify(candidateRepository, times(1)).findAll();
    }
}
