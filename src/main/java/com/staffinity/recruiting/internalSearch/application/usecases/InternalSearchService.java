package com.staffinity.recruiting.internalSearch.application.usecases;

import com.staffinity.recruiting.candidates.application.usecases.ListCandidatesService;
import com.staffinity.recruiting.candidates.domain.model.Candidate;
import com.staffinity.recruiting.internalSearch.infrastructure.adapters.employees.Employee;
import com.staffinity.recruiting.internalSearch.infrastructure.adapters.employees.EmployeeClient;
import com.staffinity.recruiting.internalSearch.application.dto.InternalSearchResponse;
import com.staffinity.recruiting.internalSearch.domain.strategy.EmployeeMatchingStrategy;
import com.staffinity.recruiting.internalSearch.domain.strategy.CandidateMatchingStrategy;
import com.staffinity.recruiting.vacancies.domain.model.Vacancy;
import com.staffinity.recruiting.vacancies.domain.ports.in.ListVacanciesUseCase;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class InternalSearchService implements InternalSearchUseCase {

    private final ListVacanciesUseCase listVacanciesUseCase;
    private final EmployeeClient employeeClient;
    private final EmployeeMatchingStrategy matchingStrategy;
    private final CandidateMatchingStrategy candidateMatchingStrategy;
    private final ListCandidatesService listCandidatesService;

    public InternalSearchService(ListVacanciesUseCase listVacanciesUseCase,
                                EmployeeClient employeeClient,
                                EmployeeMatchingStrategy matchingStrategy,
                                CandidateMatchingStrategy candidateMatchingStrategy,
                                ListCandidatesService listCandidatesService) {
        this.listVacanciesUseCase = listVacanciesUseCase;
        this.employeeClient = employeeClient;
        this.matchingStrategy = matchingStrategy;
        this.candidateMatchingStrategy = candidateMatchingStrategy;
        this.listCandidatesService = listCandidatesService;
    }

    @Override
    public InternalSearchResponse searchInternalEmployees(UUID vacancyId) {
        Optional<Vacancy> vacancyOpt = listVacanciesUseCase.listVacancies().stream()
                .filter(v -> v.getId().equals(vacancyId))
                .findFirst();
        if (vacancyOpt.isEmpty()) {
            return null;
        }
        Vacancy vacancy = vacancyOpt.get();

        List<InternalSearchResponse.MatchItem> allMatches = new ArrayList<>();

        // Try employees first
        List<Employee> employees = employeeClient.getEmployees();
        if (employees != null && !employees.isEmpty()) {
            List<InternalSearchResponse.MatchItem> employeeMatches = employees.stream()
                    .map(employee -> {
                        int score = matchingStrategy.calculateMatchScore(vacancy, employee);
                        return new InternalSearchResponse.MatchItem(employee, score);
                    })
                    .filter(item -> item.getMatchScore() >= matchingStrategy.getMinMatchingScore())
                    .collect(Collectors.toList());
            allMatches.addAll(employeeMatches);
        }

        // Fallback to candidates if no employee matches
        if (allMatches.isEmpty()) {
            List<Candidate> candidates = listCandidatesService.listCandidates();
            List<InternalSearchResponse.MatchItem> candidateMatches = candidates.stream()
                    .filter(c -> vacancy.getId().equals(c.getVacancyId()))
                    .map(c -> {
                        int score = candidateMatchingStrategy.calculateMatchScore(vacancy, c);
                        return new InternalSearchResponse.MatchItem(c, score);
                    })
                    .filter(item -> item.getMatchScore() >= candidateMatchingStrategy.getMinMatchingScore())
                    .collect(Collectors.toList());
            allMatches.addAll(candidateMatches);
        }

        // Sort all matches by score descending
        allMatches.sort(Comparator.comparingInt(InternalSearchResponse.MatchItem::getMatchScore).reversed());

        return new InternalSearchResponse(vacancy, allMatches);
    }

    @Override
    public List<com.staffinity.recruiting.internalSearch.application.dto.InternalSearchResponse.MatchItemDto> searchInternalEmployees() {
        List<Vacancy> vacancies = listVacanciesUseCase.listVacancies();
        List<com.staffinity.recruiting.internalSearch.application.dto.InternalSearchResponse.MatchItemDto> results = new ArrayList<>();

        List<Employee> employees = employeeClient.getEmployees();
        List<Candidate> candidates = listCandidatesService.listCandidates();

        for (Vacancy vacancy : vacancies) {
            // employees
            if (employees != null) {
                for (Employee employee : employees) {
                    int score = matchingStrategy.calculateMatchScore(vacancy, employee);
                    if (score >= matchingStrategy.getMinMatchingScore()) {
                        results.add(new com.staffinity.recruiting.internalSearch.application.dto.InternalSearchResponse.MatchItemDto(vacancy, employee, score));
                    }
                }
            }
            // candidates linked to vacancy
            if (candidates != null) {
                for (Candidate c : candidates) {
                    if (vacancy.getId().equals(c.getVacancyId())) {
                        int score = candidateMatchingStrategy.calculateMatchScore(vacancy, c);
                        if (score >= candidateMatchingStrategy.getMinMatchingScore()) {
                            results.add(new com.staffinity.recruiting.internalSearch.application.dto.InternalSearchResponse.MatchItemDto(vacancy, c, score));
                        }
                    }
                }
            }
        }

        results.sort(Comparator.comparingInt(com.staffinity.recruiting.internalSearch.application.dto.InternalSearchResponse.MatchItemDto::getMatchScore).reversed());
        return results;
    }
}
