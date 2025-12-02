package com.staffinity.recruiting.candidates.infrastructure.adapters;

import com.staffinity.recruiting.candidates.domain.model.Candidate;
import com.staffinity.recruiting.candidates.domain.ports.out.CandidateRepositoryPort;
import com.staffinity.recruiting.candidates.infrastructure.persistence.JpaCandidateRepository;
import com.staffinity.recruiting.candidates.infrastructure.persistence.CandidateEntity;
import com.staffinity.recruiting.candidates.infrastructure.web.mapper.CandidateMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class CandidateRepositoryAdapter implements CandidateRepositoryPort {

    private final JpaCandidateRepository jpaCandidateRepository;
    private final CandidateMapper candidateMapper;

    public CandidateRepositoryAdapter(JpaCandidateRepository jpaCandidateRepository, CandidateMapper candidateMapper) {
        this.jpaCandidateRepository = jpaCandidateRepository;
        this.candidateMapper = candidateMapper;
    }

    @Override
    public Candidate save(Candidate candidate) {
        CandidateEntity entity = candidateMapper.toEntity(candidate);
        CandidateEntity savedEntity = jpaCandidateRepository.save(entity);
        return candidateMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Candidate> findById(UUID id) {
        return jpaCandidateRepository.findById(id)
                .map(candidateMapper::toDomain);
    }

    @Override
    public List<Candidate> findAll() {
        return candidateMapper.toDomainList(jpaCandidateRepository.findAll());
    }

    @Override
    public void deleteById(UUID id) {
        jpaCandidateRepository.deleteById(id);
    }
}
