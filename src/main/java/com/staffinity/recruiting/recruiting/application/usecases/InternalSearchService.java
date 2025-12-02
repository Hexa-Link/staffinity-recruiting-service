package com.staffinity.recruiting.recruiting.application.usecases;

import com.staffinity.recruiting.candidates.infrastructure.adapters.Employee;
import com.staffinity.recruiting.candidates.infrastructure.adapters.EmployeeClient;
import com.staffinity.recruiting.recruiting.application.dto.InternalSearchResponse;
import com.staffinity.recruiting.recruiting.domain.strategy.EmployeeMatchingStrategy;
import com.staffinity.recruiting.vacancies.domain.model.Vacancy;
import com.staffinity.recruiting.vacancies.domain.ports.in.ListVacanciesUseCase;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InternalSearchService implements InternalSearchUseCase {

    private final ListVacanciesUseCase listVacanciesUseCase;
    private final EmployeeClient employeeClient;
    private final EmployeeMatchingStrategy matchingStrategy;

    public InternalSearchService(ListVacanciesUseCase listVacanciesUseCase,
                                EmployeeClient employeeClient,
                                EmployeeMatchingStrategy matchingStrategy) {
        this.listVacanciesUseCase = listVacanciesUseCase;
        this.employeeClient = employeeClient;
        this.matchingStrategy = matchingStrategy;
    }

    @Override
    public List<InternalSearchResponse> searchInternalEmployees() {
        List<Vacancy> vacancies = listVacanciesUseCase.listVacancies();
        List<Employee> employees = employeeClient.getEmployees();

        List<InternalSearchResponse> results = new ArrayList<>();

        for (Vacancy vacancy : vacancies) {
            // Calculate match scores for all employees for this vacancy
            List<InternalSearchResponse> vacancyMatches = employees.stream()
                .map(employee -> {
                    int score = matchingStrategy.calculateMatchScore(vacancy, employee);
                    return new InternalSearchResponse(vacancy, employee, score);
                })
                // Filter by minimum matching score
                .filter(result -> result.getMatchScore() >= matchingStrategy.getMinMatchingScore())
                // Sort by match score (highest first)
                .sorted(Comparator.comparingInt(InternalSearchResponse::getMatchScore).reversed())
                .collect(Collectors.toList());

            results.addAll(vacancyMatches);
        }

        return results;
    }
}
