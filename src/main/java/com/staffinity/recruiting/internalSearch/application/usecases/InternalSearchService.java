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
    public List<InternalSearchResponse> searchInternalEmployees() {
        List<Vacancy> vacancies = listVacanciesUseCase.listVacancies();
        List<InternalSearchResponse> results = new ArrayList<>();

        List<Employee> employees = employeeClient.getEmployees();
        List<Candidate> candidates = listCandidatesService.listCandidates();

        for (Vacancy vacancy : vacancies) {
            List<InternalSearchResponse.MatchItem> vacancyMatches = new ArrayList<>();

            // 1. Employees
            if (employees != null) {
                for (Employee employee : employees) {
                    int score = matchingStrategy.calculateMatchScore(vacancy, employee);
                    if (score >= matchingStrategy.getMinMatchingScore()) {
                        vacancyMatches.add(new InternalSearchResponse.MatchItem(employee, score));
                    }
                }
            }

            // 2. Candidates
            if (candidates != null) {
                for (Candidate c : candidates) {
                    // Only candidates linked to this vacancy? Or all candidates?
                    // Previous logic checked: vacancy.getId().equals(c.getVacancyId())
                    if (c.getVacancyId() != null && c.getVacancyId().equals(vacancy.getId())) {
                        int score = candidateMatchingStrategy.calculateMatchScore(vacancy, c);
                        if (score >= candidateMatchingStrategy.getMinMatchingScore()) {
                            vacancyMatches.add(new InternalSearchResponse.MatchItem(c, score));
                        }
                    }
                }
            }

            // If we found matches (or even if not, depending on requirement. Let's include
            // if matches exist)
            // But usually search results include the vacancy even if no matches?
            // The previous code only added if score >= min.
            // Let's emulate "search matches": only return if there ARE matches?
            // "InternalSearchResponse" implies the response IS the search result for a
            // vacancy.
            // The single-vacancy endpoint returns 404 if no matches.
            // So for the list, we likely only want vacancies with matches.
            if (!vacancyMatches.isEmpty()) {
                vacancyMatches
                        .sort(Comparator.comparingInt(InternalSearchResponse.MatchItem::getMatchScore).reversed());
                results.add(new InternalSearchResponse(vacancy, vacancyMatches));
            }
        }

        // Sort overall results? Maybe by number of matches or vacancy title?
        // Current requirement doesn't specify sort order of vacancies, just matches
        // within.
        return results;
    }
}
