package com.staffinity.recruiting.candidates.application.usecases;

import com.staffinity.recruiting.candidates.domain.model.Candidate;
import java.util.List;

public interface ListCandidatesUseCase {
    List<Candidate> listCandidates();
}

