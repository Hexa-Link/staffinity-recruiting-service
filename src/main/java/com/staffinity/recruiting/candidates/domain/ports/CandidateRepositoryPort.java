package com.staffinity.recruiting.candidates.domain.ports;

import com.staffinity.recruiting.candidates.domain.model.Candidate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CandidateRepositoryPort {
    Candidate save(Candidate candidate);
    Optional<Candidate> findById(UUID id);
    List<Candidate> findAll();
    void deleteById(UUID id);
}

