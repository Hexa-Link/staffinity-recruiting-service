package com.staffinity.recruiting.candidates.application.usecases;

import com.staffinity.recruiting.candidates.domain.ports.in.UpdateCandidateUseCase;
import com.staffinity.recruiting.candidates.domain.ports.out.CandidateRepositoryPort;
import com.staffinity.recruiting.candidates.domain.model.Candidate;
import com.staffinity.recruiting.candidates.application.dto.CandidateRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateCandidateService implements UpdateCandidateUseCase {
    private final CandidateRepositoryPort candidateRepository;

    public UpdateCandidateService(CandidateRepositoryPort candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    @Override
    public Candidate updateCandidate(UUID id, CandidateRequest request) {
        Candidate existing = candidateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        // Update fields from request
        existing.setFirstName(request.firstName);
        existing.setLastName(request.lastName);
        existing.setEmail(request.email);
        existing.setPhoneNumber(request.phoneNumber);
        existing.setResumeUrl(request.resumeUrl);
        existing.setLinkedinUrl(request.linkedinUrl);
        existing.setPortfolioUrl(request.portfolioUrl);
        existing.setApplicationStatus(request.applicationStatus);
        existing.setSource(request.source);
        existing.setRejectionReason(request.rejectionReason);

        return candidateRepository.save(existing);
    }
}
