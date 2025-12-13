package com.staffinity.recruiting.candidates.domain.ports.in;

import com.staffinity.recruiting.candidates.domain.model.Candidate;

import java.util.List;

public interface ListCandidatesUseCase {
    List<Candidate> listCandidates();
}
