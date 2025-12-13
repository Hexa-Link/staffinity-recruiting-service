package com.staffinity.recruiting.vacancies.infrastructure.web.controller;

import com.staffinity.recruiting.vacancies.application.dto.CreateVacancyRequest;
import com.staffinity.recruiting.vacancies.application.dto.VacancyResponse;
import com.staffinity.recruiting.vacancies.domain.model.Vacancy;
import com.staffinity.recruiting.vacancies.domain.ports.in.CreateVacancyUseCase;
import com.staffinity.recruiting.vacancies.domain.ports.in.ListVacanciesUseCase;
import com.staffinity.recruiting.vacancies.infrastructure.web.mapper.VacancyMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vacancies")
public class VacancyController {

    private final CreateVacancyUseCase createVacancyUseCase;
    private final ListVacanciesUseCase listVacanciesUseCase;
    private final VacancyMapper vacancyMapper;

    public VacancyController(
            CreateVacancyUseCase createVacancyUseCase,
            ListVacanciesUseCase listVacanciesUseCase,
            VacancyMapper vacancyMapper) {
        this.createVacancyUseCase = createVacancyUseCase;
        this.listVacanciesUseCase = listVacanciesUseCase;
        this.vacancyMapper = vacancyMapper;
    }

    @PostMapping
    public ResponseEntity<VacancyResponse> createVacancy(@RequestBody CreateVacancyRequest request) {
        Vacancy vacancy = createVacancyUseCase.createVacancy(request);
        VacancyResponse response = vacancyMapper.toResponse(vacancy);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<VacancyResponse>> listVacancies() {
        List<Vacancy> vacancies = listVacanciesUseCase.listVacancies();
        List<VacancyResponse> responses = vacancyMapper.toResponseList(vacancies);
        return ResponseEntity.ok(responses);
    }
}
