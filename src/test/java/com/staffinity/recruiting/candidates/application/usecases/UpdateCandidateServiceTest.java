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

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateCandidateServiceTest {

    @Mock
    private CandidateRepositoryPort candidateRepository;

    @Mock
    private CandidateMapper candidateMapper;

    @InjectMocks
    private UpdateCandidateService updateCandidateService;

    private UUID candidateId;
    private Candidate existingCandidate;
    private CandidateRequest updateRequest;
    private Candidate updatedCandidate;

    @BeforeEach
    void setUp() {
        candidateId = UUID.randomUUID();

        existingCandidate = new Candidate(
                candidateId,
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

        updateRequest = new CandidateRequest();
        updateRequest.vacancyId = existingCandidate.getVacancyId();
        updateRequest.firstName = "John Updated";
        updateRequest.lastName = "Doe Updated";
        updateRequest.email = "john.updated@example.com";
        updateRequest.phoneNumber = "+0987654321";
        updateRequest.resumeUrl = "http://example.com/updated-resume.pdf";
        updateRequest.linkedinUrl = "http://linkedin.com/in/johndoeupdated";
        updateRequest.portfolioUrl = "http://johndoeupdated.com";
        updateRequest.applicationStatus = "INTERVIEWING";
        updateRequest.source = "Indeed";
        updateRequest.rejectionReason = null;

        updatedCandidate = new Candidate(
                candidateId,
                updateRequest.vacancyId,
                updateRequest.firstName,
                updateRequest.lastName,
                updateRequest.email,
                updateRequest.phoneNumber,
                updateRequest.resumeUrl,
                updateRequest.linkedinUrl,
                updateRequest.portfolioUrl,
                updateRequest.applicationStatus,
                updateRequest.source,
                updateRequest.rejectionReason
        );
    }

    @Test
    void shouldUpdateCandidateSuccessfully() {
        // Given
        when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(existingCandidate));
        when(candidateRepository.save(any(Candidate.class))).thenReturn(updatedCandidate);

        // When
        Candidate result = updateCandidateService.updateCandidate(candidateId, updateRequest);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getFirstName()).isEqualTo("John Updated");
        assertThat(result.getLastName()).isEqualTo("Doe Updated");
        assertThat(result.getEmail()).isEqualTo("john.updated@example.com");
        assertThat(result.getApplicationStatus()).isEqualTo("INTERVIEWING");

        verify(candidateRepository).findById(candidateId);
        verify(candidateRepository).save(any(Candidate.class));
    }

    @Test
    void shouldThrowExceptionWhenCandidateNotFound() {
        // Given
        when(candidateRepository.findById(candidateId)).thenReturn(Optional.empty());

        // When & Then
        try {
            updateCandidateService.updateCandidate(candidateId, updateRequest);
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo("Candidate not found");
        }

        verify(candidateRepository).findById(candidateId);
        verify(candidateRepository, never()).save(any(Candidate.class));
    }
}
