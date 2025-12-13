package com.staffinity.recruiting.candidates.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaCandidateRepository extends JpaRepository<CandidateEntity, UUID> {
}

