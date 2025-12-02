package com.staffinity.recruiting.recruiting.application.usecases;

import com.staffinity.recruiting.candidates.infrastructure.adapters.Employee;
import com.staffinity.recruiting.candidates.infrastructure.adapters.EmployeeClient;
import com.staffinity.recruiting.recruiting.application.dto.InternalSearchResponse;
import com.staffinity.recruiting.recruiting.domain.strategy.EmployeeMatchingStrategy;
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

    @InjectMocks
    private InternalSearchService internalSearchService;

    private Vacancy vacancy1;
    private Vacancy vacancy2;
    private Employee employee1;
    private Employee employee2;

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
    }

    @Test
    @DisplayName("Should return internal search results with match scores above threshold")
    void shouldReturnInternalSearchResultsWithMatchScores() {
        // Given
        when(listVacanciesUseCase.listVacancies()).thenReturn(Arrays.asList(vacancy1, vacancy2));
        when(employeeClient.getEmployees()).thenReturn(Arrays.asList(employee1, employee2));
        when(matchingStrategy.calculateMatchScore(any(Vacancy.class), any(Employee.class))).thenReturn(75);
        when(matchingStrategy.getMinMatchingScore()).thenReturn(50);

        // When
        List<InternalSearchResponse> results = internalSearchService.searchInternalEmployees();

        // Then
        assertThat(results).isNotNull();
        assertThat(results).isNotEmpty();
        assertThat(results).hasSize(4); // 2 vacancies * 2 employees
        assertThat(results.get(0).getMatchScore()).isEqualTo(75);

        verify(listVacanciesUseCase).listVacancies();
        verify(employeeClient).getEmployees();
        verify(matchingStrategy, times(4)).calculateMatchScore(any(Vacancy.class), any(Employee.class));
    }

    @Test
    @DisplayName("Should filter out employees with scores below minimum threshold")
    void shouldFilterByMinimumMatchingScore() {
        // Given
        when(listVacanciesUseCase.listVacancies()).thenReturn(Arrays.asList(vacancy1));
        when(employeeClient.getEmployees()).thenReturn(Arrays.asList(employee1, employee2));
        // employee1 has good match, employee2 has low match
        when(matchingStrategy.calculateMatchScore(vacancy1, employee1)).thenReturn(80);
        when(matchingStrategy.calculateMatchScore(vacancy1, employee2)).thenReturn(30);
        when(matchingStrategy.getMinMatchingScore()).thenReturn(50);

        // When
        List<InternalSearchResponse> results = internalSearchService.searchInternalEmployees();

        // Then
        assertThat(results).hasSize(1); // Only employee1 should be included
        assertThat(results.get(0).getEmployee().getId()).isEqualTo(employee1.getId());
        assertThat(results.get(0).getMatchScore()).isEqualTo(80);
    }

    @Test
    @DisplayName("Should sort results by match score in descending order")
    void shouldSortResultsByMatchScoreDescending() {
        // Given
        when(listVacanciesUseCase.listVacancies()).thenReturn(Arrays.asList(vacancy1));
        when(employeeClient.getEmployees()).thenReturn(Arrays.asList(employee1, employee2));
        when(matchingStrategy.calculateMatchScore(vacancy1, employee1)).thenReturn(60);
        when(matchingStrategy.calculateMatchScore(vacancy1, employee2)).thenReturn(90);
        when(matchingStrategy.getMinMatchingScore()).thenReturn(50);

        // When
        List<InternalSearchResponse> results = internalSearchService.searchInternalEmployees();

        // Then
        assertThat(results).hasSize(2);
        assertThat(results.get(0).getMatchScore()).isEqualTo(90); // Highest first
        assertThat(results.get(0).getEmployee().getId()).isEqualTo(employee2.getId());
        assertThat(results.get(1).getMatchScore()).isEqualTo(60);
        assertThat(results.get(1).getEmployee().getId()).isEqualTo(employee1.getId());
    }

    @Test
    @DisplayName("Should return empty list when no vacancies exist")
    void shouldReturnEmptyListWhenNoVacancies() {
        // Given
        when(listVacanciesUseCase.listVacancies()).thenReturn(Arrays.asList());
        when(employeeClient.getEmployees()).thenReturn(Arrays.asList(employee1, employee2));

        // When
        List<InternalSearchResponse> results = internalSearchService.searchInternalEmployees();

        // Then
        assertThat(results).isNotNull();
        assertThat(results).isEmpty();

        verify(listVacanciesUseCase).listVacancies();
        verify(employeeClient).getEmployees();
        verify(matchingStrategy, never()).calculateMatchScore(any(), any());
    }

    @Test
    @DisplayName("Should return empty list when no employees exist")
    void shouldReturnEmptyListWhenNoEmployees() {
        // Given
        when(listVacanciesUseCase.listVacancies()).thenReturn(Arrays.asList(vacancy1, vacancy2));
        when(employeeClient.getEmployees()).thenReturn(Arrays.asList());

        // When
        List<InternalSearchResponse> results = internalSearchService.searchInternalEmployees();

        // Then
        assertThat(results).isNotNull();
        assertThat(results).isEmpty();

        verify(listVacanciesUseCase).listVacancies();
        verify(employeeClient).getEmployees();
        verify(matchingStrategy, never()).calculateMatchScore(any(), any());
    }

    @Test
    @DisplayName("Should return empty list when no matches meet minimum score")
    void shouldReturnEmptyListWhenNoMatchesMeetMinimumScore() {
        // Given
        when(listVacanciesUseCase.listVacancies()).thenReturn(Arrays.asList(vacancy1));
        when(employeeClient.getEmployees()).thenReturn(Arrays.asList(employee1, employee2));
        when(matchingStrategy.calculateMatchScore(any(), any())).thenReturn(20); // Below threshold
        when(matchingStrategy.getMinMatchingScore()).thenReturn(50);

        // When
        List<InternalSearchResponse> results = internalSearchService.searchInternalEmployees();

        // Then
        assertThat(results).isEmpty();
    }

    @Test
    @DisplayName("Should handle multiple vacancies and calculate scores for each")
    void shouldHandleMultipleVacancies() {
        // Given
        when(listVacanciesUseCase.listVacancies()).thenReturn(Arrays.asList(vacancy1, vacancy2));
        when(employeeClient.getEmployees()).thenReturn(Arrays.asList(employee1));
        when(matchingStrategy.calculateMatchScore(vacancy1, employee1)).thenReturn(70);
        when(matchingStrategy.calculateMatchScore(vacancy2, employee1)).thenReturn(80);
        when(matchingStrategy.getMinMatchingScore()).thenReturn(50);

        // When
        List<InternalSearchResponse> results = internalSearchService.searchInternalEmployees();

        // Then
        assertThat(results).hasSize(2);
        // Results for vacancy1
        assertThat(results.stream().anyMatch(r -> r.getVacancy().getId().equals(vacancy1.getId()))).isTrue();
        // Results for vacancy2
        assertThat(results.stream().anyMatch(r -> r.getVacancy().getId().equals(vacancy2.getId()))).isTrue();
    }
}
