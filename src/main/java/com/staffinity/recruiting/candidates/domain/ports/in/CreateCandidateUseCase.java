package com.staffinity.recruiting.candidates.domain.ports.in;

import com.staffinity.recruiting.candidates.domain.model.Candidate;
import com.staffinity.recruiting.candidates.application.dto.CandidateRequest;

public interface CreateCandidateUseCase {
    Candidate createCandidate(CandidateRequest request);
}
