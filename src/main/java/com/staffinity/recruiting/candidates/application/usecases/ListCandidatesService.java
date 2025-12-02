package com.staffinity.recruiting.candidates.application.usecases;

import com.staffinity.recruiting.candidates.domain.ports.in.ListCandidatesUseCase;
import com.staffinity.recruiting.candidates.domain.ports.out.CandidateRepositoryPort;
import com.staffinity.recruiting.candidates.domain.model.Candidate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListCandidatesService implements ListCandidatesUseCase {
    private final CandidateRepositoryPort candidateRepository;

    public ListCandidatesService(CandidateRepositoryPort candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    @Override
    public List<Candidate> listCandidates() {
        return candidateRepository.findAll();
    }
}
