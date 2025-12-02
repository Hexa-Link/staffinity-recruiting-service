package com.staffinity.recruiting.candidates.application.usecases;

import com.staffinity.recruiting.candidates.domain.ports.in.CreateCandidateUseCase;
import com.staffinity.recruiting.candidates.domain.ports.out.CandidateRepositoryPort;
import com.staffinity.recruiting.candidates.domain.model.Candidate;
import com.staffinity.recruiting.candidates.application.dto.CandidateRequest;
import com.staffinity.recruiting.candidates.infrastructure.web.mapper.CandidateMapper;
import org.springframework.stereotype.Service;

@Service
public class CreateCandidateService implements CreateCandidateUseCase {
    private final CandidateRepositoryPort candidateRepository;
    private final CandidateMapper candidateMapper;

    public CreateCandidateService(CandidateRepositoryPort candidateRepository, CandidateMapper candidateMapper) {
        this.candidateRepository = candidateRepository;
        this.candidateMapper = candidateMapper;
    }

    @Override
    public Candidate createCandidate(CandidateRequest request) {
        // Convert request to domain model
        Candidate candidate = candidateMapper.fromRequest(request);

        // Save through the repository port
        return candidateRepository.save(candidate);
    }
}
