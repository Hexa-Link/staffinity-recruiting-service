package com.staffinity.recruiting.candidates.application.usecases;

import com.staffinity.recruiting.candidates.application.dto.CandidateRequest;
import com.staffinity.recruiting.candidates.domain.model.Candidate;

public interface UpdateCandidateUseCase {
    Candidate updateCandidate(String id, CandidateRequest request);
}

