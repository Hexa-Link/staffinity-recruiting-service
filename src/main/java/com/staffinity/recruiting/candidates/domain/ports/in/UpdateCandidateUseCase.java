package com.staffinity.recruiting.candidates.domain.ports.in;

import com.staffinity.recruiting.candidates.domain.model.Candidate;
import com.staffinity.recruiting.candidates.application.dto.CandidateRequest;
import java.util.UUID;

public interface UpdateCandidateUseCase {
    Candidate updateCandidate(UUID id, CandidateRequest request);
}
