package com.staffinity.recruiting.candidates.application.usecases;

import com.staffinity.recruiting.candidates.application.dto.CandidateRequest;
import com.staffinity.recruiting.candidates.domain.model.Candidate;
import com.staffinity.recruiting.candidates.domain.ports.out.CandidateRepositoryPort;
import com.staffinity.recruiting.candidates.infrastructure.web.mapper.CandidateMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateCandidateServiceTest {

    @Mock
    private CandidateRepositoryPort candidateRepository;

    @Mock
    private CandidateMapper candidateMapper;

    @InjectMocks
    private CreateCandidateService createCandidateService;

    private CandidateRequest request;
    private Candidate candidate;

    @BeforeEach
    void setUp() {
        request = new CandidateRequest();
        request.vacancyId = UUID.randomUUID();
        request.firstName = "John";
        request.lastName = "Doe";
        request.email = "john.doe@example.com";
        request.phoneNumber = "+1234567890";
        request.resumeUrl = "http://example.com/resume.pdf";
        request.linkedinUrl = "http://linkedin.com/in/johndoe";
        request.portfolioUrl = "http://johndoe.com";
        request.applicationStatus = "APPLIED";
        request.source = "LinkedIn";
        request.rejectionReason = null;

        candidate = new Candidate(
                UUID.randomUUID(),
                request.vacancyId,
                request.firstName,
                request.lastName,
                request.email,
                request.phoneNumber,
                request.resumeUrl,
                request.linkedinUrl,
                request.portfolioUrl,
                request.applicationStatus,
                request.source,
                request.rejectionReason
        );
    }

    @Test
    void shouldCreateCandidateSuccessfully() {
        // Given
        when(candidateMapper.fromRequest(request)).thenReturn(candidate);
        when(candidateRepository.save(any(Candidate.class))).thenReturn(candidate);

        // When
        Candidate result = createCandidateService.createCandidate(request);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getFirstName()).isEqualTo("John");
        assertThat(result.getLastName()).isEqualTo("Doe");
        assertThat(result.getEmail()).isEqualTo("john.doe@example.com");

        verify(candidateMapper).fromRequest(request);
        verify(candidateRepository).save(any(Candidate.class));
    }

    @Test
    void shouldCallRepositorySaveWhenCreatingCandidate() {
        // Given
        when(candidateMapper.fromRequest(request)).thenReturn(candidate);
        when(candidateRepository.save(candidate)).thenReturn(candidate);

        // When
        createCandidateService.createCandidate(request);

        // Then
        verify(candidateRepository, times(1)).save(candidate);
    }
}
