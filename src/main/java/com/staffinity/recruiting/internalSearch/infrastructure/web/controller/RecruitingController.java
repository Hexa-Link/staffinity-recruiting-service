package com.staffinity.recruiting.internalSearch.infrastructure.web.controller;

import com.staffinity.recruiting.internalSearch.application.dto.InternalSearchResponse;
import com.staffinity.recruiting.internalSearch.application.usecases.InternalSearchUseCase;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/recruiting/internal-search")
public class RecruitingController {

    private final InternalSearchUseCase internalSearchUseCase;

    public RecruitingController(InternalSearchUseCase internalSearchUseCase) {
        this.internalSearchUseCase = internalSearchUseCase;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<InternalSearchResponse.MatchItemDto>> internalSearchAll() {
        List<InternalSearchResponse.MatchItemDto> results = internalSearchUseCase.searchInternalEmployees();
        return ResponseEntity.ok(results);
    }

    @GetMapping("/{vacancyId}")
    public ResponseEntity<?> internalSearch(@PathVariable("vacancyId") UUID vacancyId) {
        InternalSearchResponse result = internalSearchUseCase.searchInternalEmployees(vacancyId);
        if (result == null || result.getMatches().isEmpty()) {
            ProblemDetail pd = ProblemDetail.forStatus(404);
            pd.setTitle("No matches found");
            pd.setDetail("No employees nor candidates found suited for the vacancy");
            pd.setType(java.net.URI.create("/errors/no-matches"));
            return ResponseEntity.status(404).body(pd);
        }
        return ResponseEntity.ok(result);
    }
}
