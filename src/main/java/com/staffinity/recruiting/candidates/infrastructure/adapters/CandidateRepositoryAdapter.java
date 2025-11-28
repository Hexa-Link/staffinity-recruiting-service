package com.staffinity.recruiting.candidates.infrastructure.adapters;

import com.staffinity.recruiting.candidates.domain.model.Candidate;
import com.staffinity.recruiting.candidates.domain.ports.out.CandidateRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class CandidateRepositoryAdapter implements CandidateRepositoryPort {
    
    // TODO: Inject JpaCandidateRepository and CandidateMapper

    @Override
    public Candidate save(Candidate candidate) {
        // TODO: Implement
        return null;
    }

    @Override
    public Optional<Candidate> findById(UUID id) {
        // TODO: Implement
        return Optional.empty();
    }

    @Override
    public List<Candidate> findAll() {
        // TODO: Implement
        return List.of();
    }

    @Override
    public void deleteById(UUID id) {
        // TODO: Implement
    }
}

