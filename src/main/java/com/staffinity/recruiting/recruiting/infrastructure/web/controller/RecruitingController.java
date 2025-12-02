package com.staffinity.recruiting.recruiting.infrastructure.web.controller;

import com.staffinity.recruiting.recruiting.application.dto.InternalSearchResponse;
import com.staffinity.recruiting.recruiting.application.usecases.InternalSearchUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recruiting")
public class RecruitingController {

    private final InternalSearchUseCase internalSearchUseCase;

    public RecruitingController(InternalSearchUseCase internalSearchUseCase) {
        this.internalSearchUseCase = internalSearchUseCase;
    }

    @GetMapping("/internal-search")
    public ResponseEntity<List<InternalSearchResponse>> internalSearch() {
        List<InternalSearchResponse> results = internalSearchUseCase.searchInternalEmployees();
        return ResponseEntity.ok(results);
    }
}
