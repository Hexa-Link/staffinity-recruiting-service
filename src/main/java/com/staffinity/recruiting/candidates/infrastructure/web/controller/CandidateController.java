package com.staffinity.recruiting.candidates.infrastructure.web.controller;

import com.staffinity.recruiting.candidates.application.dto.CandidateRequest;
import com.staffinity.recruiting.candidates.application.dto.CandidateResponse;
import com.staffinity.recruiting.candidates.domain.model.Candidate;
import com.staffinity.recruiting.candidates.domain.ports.in.CreateCandidateUseCase;
import com.staffinity.recruiting.candidates.domain.ports.in.ListCandidatesUseCase;
import com.staffinity.recruiting.candidates.domain.ports.in.UpdateCandidateUseCase;
import com.staffinity.recruiting.candidates.infrastructure.web.mapper.CandidateMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/candidates")
public class CandidateController {

    private final CreateCandidateUseCase createCandidateUseCase;
    private final ListCandidatesUseCase listCandidatesUseCase;
    private final UpdateCandidateUseCase updateCandidateUseCase;
    private final CandidateMapper candidateMapper;

    public CandidateController(
            CreateCandidateUseCase createCandidateUseCase,
            ListCandidatesUseCase listCandidatesUseCase,
            UpdateCandidateUseCase updateCandidateUseCase,
            CandidateMapper candidateMapper) {
        this.createCandidateUseCase = createCandidateUseCase;
        this.listCandidatesUseCase = listCandidatesUseCase;
        this.updateCandidateUseCase = updateCandidateUseCase;
        this.candidateMapper = candidateMapper;
    }

    @PostMapping
    public ResponseEntity<CandidateResponse> createCandidate(@RequestBody CandidateRequest request) {
        Candidate candidate = createCandidateUseCase.createCandidate(request);
        CandidateResponse response = candidateMapper.toResponse(candidate);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CandidateResponse>> listCandidates() {
        List<Candidate> candidates = listCandidatesUseCase.listCandidates();
        List<CandidateResponse> responses = candidateMapper.toResponseList(candidates);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CandidateResponse> updateCandidate(@PathVariable UUID id, @RequestBody CandidateRequest request) {
        Candidate candidate = updateCandidateUseCase.updateCandidate(id, request);
        CandidateResponse response = candidateMapper.toResponse(candidate);
        return ResponseEntity.ok(response);
    }
}
