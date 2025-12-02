package com.staffinity.recruiting.internalSearch.application.usecases;

import com.staffinity.recruiting.internalSearch.infrastructure.adapters.employees.Employee;
import com.staffinity.recruiting.internalSearch.infrastructure.adapters.employees.EmployeeClient;
import com.staffinity.recruiting.internalSearch.application.dto.InternalSearchResponse;
import com.staffinity.recruiting.internalSearch.application.usecases.InternalSearchService;
import com.staffinity.recruiting.internalSearch.domain.strategy.EmployeeMatchingStrategy;
import com.staffinity.recruiting.internalSearch.domain.strategy.CandidateMatchingStrategy;
import com.staffinity.recruiting.candidates.application.usecases.ListCandidatesService;
import com.staffinity.recruiting.candidates.domain.model.Candidate;
import com.staffinity.recruiting.vacancies.domain.model.Vacancy;
import com.staffinity.recruiting.vacancies.domain.ports.in.ListVacanciesUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Internal Search Service Tests")
class InternalSearchServiceTest {

    @Mock
    private ListVacanciesUseCase listVacanciesUseCase;

    @Mock
    private EmployeeClient employeeClient;

    @Mock
    private EmployeeMatchingStrategy matchingStrategy;

    @Mock
    private CandidateMatchingStrategy candidateMatchingStrategy;

    @Mock
    private ListCandidatesService listCandidatesService;

    @InjectMocks
    private InternalSearchService internalSearchService;

    private Vacancy vacancy1;
    private Vacancy vacancy2;
    private Employee employee1;
    private Employee employee2;
    private Candidate candidate1;
    private Candidate candidate2;
    private Candidate candidate3;

    @BeforeEach
    void setUp() {
        vacancy1 = new Vacancy(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                "Senior Java Developer",
                "We need a senior Java developer",
                "5+ years Java experience",
                "Remote",
                true,
                "Senior",
                "OPEN",
                new BigDecimal("80000"),
                new BigDecimal("120000"),
                "USD",
                null
        );

        vacancy2 = new Vacancy(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                "Junior Developer",
                "Entry level developer",
                "Basic programming skills",
                "Office",
                false,
                "Junior",
                "OPEN",
                new BigDecimal("40000"),
                new BigDecimal("60000"),
                "USD",
                null
        );

        employee1 = new Employee(
                UUID.randomUUID(),
                "EMP001",
                "John Doe",
                "john.doe@company.com",
                "hashedpassword",
                "+1234567890",
                LocalDate.of(1985, 5, 15),
                LocalDate.of(2010, 1, 1),
                UUID.randomUUID(),
                "123456789",
                null,
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                false
        );

        employee2 = new Employee(
                UUID.randomUUID(),
                "EMP002",
                "Jane Smith",
                "jane.smith@company.com",
                "hashedpassword2",
                "+0987654321",
                LocalDate.of(1990, 8, 20),
                LocalDate.of(2015, 6, 15),
                UUID.randomUUID(),
                "987654321",
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                false
        );

        // Sample candidates linked to vacancy1
        candidate1 = new Candidate(UUID.randomUUID(), vacancy1.getId(), "Miguel", "Herrera",
            "miguel.herrera@email.com", "+51987654331",
            "https://resume.io/miguel-herrera", "https://linkedin.com/in/miguel-herrera", null,
            "INTERVIEWING", "LINKEDIN", null);
        candidate2 = new Candidate(UUID.randomUUID(), vacancy1.getId(), "Isabella", "Rojas",
            "isabella.rojas@email.com", "+51987654332",
            "https://resume.io/isabella-rojas", "https://linkedin.com/in/isabella-rojas", "https://irojas.datascience.io",
            "REJECTED", "COMPANY_WEBSITE", "Limited experience with Apache Spark");
        candidate3 = new Candidate(UUID.randomUUID(), vacancy1.getId(), "Fernando", "Chavez",
            "fernando.chavez@email.com", "+51987654333",
            "https://resume.io/fernando-chavez", "https://linkedin.com/in/fernando-chavez", null,
            "UNDER_REVIEW", "REFERRAL", null);
    }

    @Test
    @DisplayName("Should return matches with scores above threshold for a vacancy")
    void shouldReturnInternalSearchResultsWithMatchScores() {
        // Given
        UUID vacancyId = vacancy1.getId();
        when(listVacanciesUseCase.listVacancies()).thenReturn(Arrays.asList(vacancy1, vacancy2));
        when(employeeClient.getEmployees()).thenReturn(Arrays.asList(employee1, employee2));
        when(matchingStrategy.calculateMatchScore(vacancy1, employee1)).thenReturn(75);
        when(matchingStrategy.calculateMatchScore(vacancy1, employee2)).thenReturn(75);
        when(matchingStrategy.getMinMatchingScore()).thenReturn(50);

        // When
        InternalSearchResponse response = internalSearchService.searchInternalEmployees(vacancyId);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getVacancy().getId()).isEqualTo(vacancyId);
        assertThat(response.getMatches()).isNotEmpty();
        assertThat(response.getMatches()).hasSize(2);
        assertThat(response.getMatches().get(0).getMatchScore()).isEqualTo(75);

        verify(listVacanciesUseCase).listVacancies();
        verify(employeeClient).getEmployees();
        verify(matchingStrategy, times(2)).calculateMatchScore(eq(vacancy1), any(Employee.class));
    }

    @Test
    @DisplayName("Should filter out employees with scores below minimum threshold")
    void shouldFilterByMinimumMatchingScore() {
        // Given
        UUID vacancyId = vacancy1.getId();
        when(listVacanciesUseCase.listVacancies()).thenReturn(Arrays.asList(vacancy1));
        when(employeeClient.getEmployees()).thenReturn(Arrays.asList(employee1, employee2));
        when(matchingStrategy.calculateMatchScore(vacancy1, employee1)).thenReturn(80);
        when(matchingStrategy.calculateMatchScore(vacancy1, employee2)).thenReturn(30);
        when(matchingStrategy.getMinMatchingScore()).thenReturn(50);

        // When
        InternalSearchResponse response = internalSearchService.searchInternalEmployees(vacancyId);

        // Then
        assertThat(response.getMatches()).hasSize(1); // Only employee1 should be included
        assertThat(response.getMatches().get(0).getEmployee().getId()).isEqualTo(employee1.getId());
        assertThat(response.getMatches().get(0).getMatchScore()).isEqualTo(80);
    }

    @Test
    @DisplayName("Should sort matches by score in descending order")
    void shouldSortResultsByMatchScoreDescending() {
        // Given
        UUID vacancyId = vacancy1.getId();
        when(listVacanciesUseCase.listVacancies()).thenReturn(Arrays.asList(vacancy1));
        when(employeeClient.getEmployees()).thenReturn(Arrays.asList(employee1, employee2));
        when(matchingStrategy.calculateMatchScore(vacancy1, employee1)).thenReturn(60);
        when(matchingStrategy.calculateMatchScore(vacancy1, employee2)).thenReturn(90);
        when(matchingStrategy.getMinMatchingScore()).thenReturn(50);

        // When
        InternalSearchResponse response = internalSearchService.searchInternalEmployees(vacancyId);

        // Then
        assertThat(response.getMatches()).hasSize(2);
        assertThat(response.getMatches().get(0).getMatchScore()).isEqualTo(90); // Highest first
        assertThat(response.getMatches().get(0).getEmployee().getId()).isEqualTo(employee2.getId());
        assertThat(response.getMatches().get(1).getMatchScore()).isEqualTo(60);
        assertThat(response.getMatches().get(1).getEmployee().getId()).isEqualTo(employee1.getId());
    }

    @Test
    @DisplayName("Should return null when vacancy not found")
    void shouldReturnNullWhenNoVacancy() {
        // Given
        UUID vacancyId = UUID.randomUUID();
        when(listVacanciesUseCase.listVacancies()).thenReturn(Arrays.asList());

        // When
        InternalSearchResponse response = internalSearchService.searchInternalEmployees(vacancyId);

        // Then
        assertThat(response).isNull();
        verify(listVacanciesUseCase).listVacancies();
        verify(employeeClient, never()).getEmployees();
        verify(matchingStrategy, never()).calculateMatchScore(any(), any());
    }

    @Test
    @DisplayName("Should return empty matches when no employees and no candidates")
    void shouldReturnEmptyWhenNoEmployeesNoCandidates() {
        // Given
        UUID vacancyId = vacancy1.getId();
        when(listVacanciesUseCase.listVacancies()).thenReturn(Arrays.asList(vacancy1));
        when(employeeClient.getEmployees()).thenReturn(Arrays.asList());
        // Note: candidate fallback is in service via ListCandidatesService; not mocked here -> results empty

        // When
        InternalSearchResponse response = internalSearchService.searchInternalEmployees(vacancyId);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getMatches()).isEmpty();
        verify(listVacanciesUseCase).listVacancies();
        verify(employeeClient).getEmployees();
        verify(matchingStrategy, never()).calculateMatchScore(any(), any());
    }

    @Test
    @DisplayName("Should return empty matches when scores below threshold")
    void shouldReturnEmptyWhenNoMatchesMeetMinimumScore() {
        // Given
        UUID vacancyId = vacancy1.getId();
        when(listVacanciesUseCase.listVacancies()).thenReturn(Arrays.asList(vacancy1));
        when(employeeClient.getEmployees()).thenReturn(Arrays.asList(employee1, employee2));
        when(matchingStrategy.calculateMatchScore(any(), any())).thenReturn(20); // Below threshold
        when(matchingStrategy.getMinMatchingScore()).thenReturn(50);

        // When
        InternalSearchResponse response = internalSearchService.searchInternalEmployees(vacancyId);

        // Then
        assertThat(response.getMatches()).isEmpty();
    }

    @Test
    @DisplayName("Should return results for the requested vacancy only")
    void shouldHandleSingleVacancyById() {
        // Given
        UUID vacancyId = vacancy2.getId();
        when(listVacanciesUseCase.listVacancies()).thenReturn(Arrays.asList(vacancy1, vacancy2));
        when(employeeClient.getEmployees()).thenReturn(Arrays.asList(employee1));
        when(matchingStrategy.calculateMatchScore(vacancy2, employee1)).thenReturn(80);
        when(matchingStrategy.getMinMatchingScore()).thenReturn(50);

        // When
        InternalSearchResponse response = internalSearchService.searchInternalEmployees(vacancyId);

        // Then
        assertThat(response.getVacancy().getId()).isEqualTo(vacancyId);
        assertThat(response.getMatches()).hasSize(1);
        assertThat(response.getMatches().get(0).getMatchScore()).isEqualTo(80);
    }

    @Test
    @DisplayName("Should fallback to candidates with differentiated scores and source field")
    void shouldFallbackToCandidatesWithScoresAndSource() {
        // Given
        UUID vacancyId = vacancy1.getId();
        when(listVacanciesUseCase.listVacancies()).thenReturn(Arrays.asList(vacancy1));
        // No employees available triggers candidate fallback in service
        when(employeeClient.getEmployees()).thenReturn(Arrays.asList());
        // Provide candidates via ListCandidatesService
        when(listCandidatesService.listCandidates()).thenReturn(Arrays.asList(candidate1, candidate2, candidate3));
        // Stub candidate scoring to produce differentiated scores and a min threshold
        when(candidateMatchingStrategy.calculateMatchScore(vacancy1, candidate1)).thenReturn(87);
        when(candidateMatchingStrategy.calculateMatchScore(vacancy1, candidate2)).thenReturn(64);
        when(candidateMatchingStrategy.calculateMatchScore(vacancy1, candidate3)).thenReturn(84);
        when(candidateMatchingStrategy.getMinMatchingScore()).thenReturn(50);

        // When
        InternalSearchResponse response = internalSearchService.searchInternalEmployees(vacancyId);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getVacancy().getId()).isEqualTo(vacancyId);
        assertThat(response.getMatches()).isNotNull();
        // Should have candidate matches sorted by score desc and source marked
        assertThat(response.getMatches()).hasSize(3);
        assertThat(response.getMatches().get(0).getSource()).isEqualTo("candidates");
        assertThat(response.getMatches()).extracting(m -> m.getMatchScore())
            .containsExactly(87, 84, 64);
        // First match corresponds to candidate1 (highest score)
        assertThat(response.getMatches().get(0).getCandidate().getId()).isEqualTo(candidate1.getId());

        verify(listVacanciesUseCase).listVacancies();
        verify(employeeClient).getEmployees();
        verify(listCandidatesService).listCandidates();
        verify(candidateMatchingStrategy, atLeastOnce()).getMinMatchingScore();
        verify(candidateMatchingStrategy, times(3)).calculateMatchScore(eq(vacancy1), any(Candidate.class));
    }
}
