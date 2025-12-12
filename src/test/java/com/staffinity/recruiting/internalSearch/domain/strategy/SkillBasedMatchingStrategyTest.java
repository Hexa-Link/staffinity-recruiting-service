package com.staffinity.recruiting.internalSearch.domain.strategy;

import com.staffinity.recruiting.internalSearch.infrastructure.adapters.employees.Employee;
import com.staffinity.recruiting.vacancies.domain.model.Vacancy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Skill-Based Matching Strategy Tests")
class SkillBasedMatchingStrategyTest {

    private SkillBasedMatchingStrategy strategy;
    private UUID sharedHeadquartersId;

    @BeforeEach
    void setUp() {
        com.staffinity.recruiting.internalSearch.domain.service.KeywordExtractionService keywordService = new com.staffinity.recruiting.internalSearch.domain.service.KeywordExtractionService();
        strategy = new SkillBasedMatchingStrategy(keywordService);
        sharedHeadquartersId = UUID.randomUUID();
    }

    @Test
    @DisplayName("Should calculate perfect match score for senior employee with senior vacancy")
    void shouldCalculatePerfectMatchForSeniorPosition() {
        // Given - Senior employee with 10 years experience
        Employee seniorEmployee = createEmployee(
                "Senior Developer John",
                LocalDate.of(2013, 1, 1), // 10+ years experience
                sharedHeadquartersId);

        // Senior vacancy, remote allowed
        Vacancy seniorVacancy = createVacancy("Senior Java Developer", "Senior", true);

        // When
        int score = strategy.calculateMatchScore(seniorVacancy, seniorEmployee);

        // Then - Should get all points: 40 (seniority) + 30 (location/remote) + 30
        // (experience)
        assertThat(score).isEqualTo(100);
    }

    @Test
    @DisplayName("Should calculate high match score for mid-level employee with mid-level vacancy")
    void shouldCalculateHighMatchForMidLevel() {
        // Given - Mid-level employee with 3 years experience
        Employee midEmployee = createEmployee(
                "Mid Developer Jane",
                LocalDate.of(2020, 6, 1), // 3-4 years experience
                sharedHeadquartersId);

        // Mid-level vacancy
        Vacancy midVacancy = createVacancy("Mid-Level Backend Developer", "Mid", true);

        // When
        int score = strategy.calculateMatchScore(midVacancy, midEmployee);

        // Then - Should get all points
        assertThat(score).isEqualTo(100);
    }

    @Test
    @DisplayName("Should calculate partial match for employee with some matching criteria")
    void shouldCalculatePartialMatch() {
        // Given - Junior employee with 1 year experience
        Employee juniorEmployee = createEmployee(
                "Junior Dev Bob",
                LocalDate.of(2022, 1, 1), // 1-2 years experience
                sharedHeadquartersId);

        // Senior vacancy - seniority doesn't match but location and some criteria do
        Vacancy seniorVacancy = createVacancy("Senior Developer", "Senior", true);

        // When
        int score = strategy.calculateMatchScore(seniorVacancy, juniorEmployee);

        // Then - Should get partial points (location matches, but not
        // seniority/experience for senior)
        assertThat(score).isLessThan(100);
        assertThat(score).isGreaterThanOrEqualTo(0);
    }

    @Test
    @DisplayName("Should calculate zero or low score when no criteria match")
    void shouldCalculateLowScoreForNoMatch() {
        // Given - Employee without key information
        Employee employeeWithNoInfo = new Employee(
                UUID.randomUUID(),
                "EMP999",
                "New Employee",
                "new@company.com",
                "password",
                "+1234567890",
                LocalDate.of(2000, 1, 1),
                null, // No hire date
                UUID.randomUUID(),
                "123456789",
                null,
                null, // No headquarters
                UUID.randomUUID(),
                UUID.randomUUID(),
                null, // No access level
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                false);

        Vacancy vacancy = createVacancy("Senior Developer", "Senior", false);

        // When
        int score = strategy.calculateMatchScore(vacancy, employeeWithNoInfo);

        // Then - Should get very low or zero score
        assertThat(score).isLessThan(50);
    }

    @Test
    @DisplayName("Should match remote vacancies with any employee location")
    void shouldMatchRemoteVacancyWithAnyLocation() {
        // Given - Employee with any location
        Employee employee = createEmployee(
                "Remote Worker",
                LocalDate.of(2015, 1, 1),
                UUID.randomUUID() // Different headquarters
        );

        // Remote vacancy
        Vacancy remoteVacancy = createVacancy("Developer", "Senior", true);

        // When
        int score = strategy.calculateMatchScore(remoteVacancy, employee);

        // Then - Should get location points because it's remote
        assertThat(score).isGreaterThanOrEqualTo(30);
    }

    @Test
    @DisplayName("Should respect minimum matching score threshold")
    void shouldHaveMinimumMatchingScoreThreshold() {
        // When
        int minScore = strategy.getMinMatchingScore();

        // Then
        assertThat(minScore).isEqualTo(50);
    }

    @Test
    @DisplayName("Should match junior employee with junior vacancy")
    void shouldMatchJuniorPositions() {
        // Given - Junior employee with less than 2 years experience
        Employee juniorEmployee = createEmployee(
                "Junior Dev Alice",
                LocalDate.of(2022, 6, 1), // ~1.5 years
                sharedHeadquartersId);

        Vacancy juniorVacancy = createVacancy("Junior Developer", "Junior", true);

        // When
        int score = strategy.calculateMatchScore(juniorVacancy, juniorEmployee);

        // Then - Should get good match score
        assertThat(score).isGreaterThanOrEqualTo(70);
    }

    @Test
    @DisplayName("Should calculate different scores for different employee-vacancy combinations")
    void shouldCalculateDifferentScoresForDifferentCombinations() {
        // Given
        Employee seniorEmployee = createEmployee("Senior Dev", LocalDate.of(2010, 1, 1), sharedHeadquartersId);
        Employee juniorEmployee = createEmployee("Junior Dev", LocalDate.of(2022, 1, 1), sharedHeadquartersId);

        Vacancy seniorVacancy = createVacancy("Senior Position", "Senior", true);
        Vacancy juniorVacancy = createVacancy("Junior Position", "Junior", true);

        // When
        int seniorToSenior = strategy.calculateMatchScore(seniorVacancy, seniorEmployee);
        int juniorToJunior = strategy.calculateMatchScore(juniorVacancy, juniorEmployee);
        int seniorToJunior = strategy.calculateMatchScore(juniorVacancy, seniorEmployee);
        int juniorToSenior = strategy.calculateMatchScore(seniorVacancy, juniorEmployee);

        // Then - Perfect matches should score higher than mismatches
        assertThat(seniorToSenior).isGreaterThan(juniorToSenior);
        assertThat(juniorToJunior).isGreaterThan(seniorToJunior);
    }

    @Test
    @DisplayName("Should handle null or missing seniority gracefully")
    void shouldHandleNullSeniorityGracefully() {
        // Given
        Employee employee = createEmployee("Developer", LocalDate.of(2015, 1, 1), sharedHeadquartersId);
        Vacancy vacancyWithoutSeniority = new Vacancy(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                "Developer",
                "Description",
                "Requirements",
                "Remote",
                true,
                null, // No seniority specified
                "OPEN",
                new BigDecimal("60000"),
                new BigDecimal("100000"),
                "USD",
                null);

        // When
        int score = strategy.calculateMatchScore(vacancyWithoutSeniority, employee);

        // Then - Should still calculate partial score based on other criteria
        assertThat(score).isGreaterThanOrEqualTo(0);
        assertThat(score).isLessThan(100);
    }

    // Helper methods

    private Employee createEmployee(String name, LocalDate hireDate, UUID headquartersId) {
        return new Employee(
                UUID.randomUUID(),
                "EMP" + name.hashCode(),
                name,
                name.toLowerCase().replace(" ", ".") + "@company.com",
                "hashedpassword",
                "+1234567890",
                LocalDate.of(1985, 5, 15), // Birth date
                hireDate,
                UUID.randomUUID(), // Identification type
                "123456789",
                null,
                headquartersId,
                UUID.randomUUID(), // Gender
                UUID.randomUUID(), // Status
                UUID.randomUUID(), // Access level (not null means has access)
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                false);
    }

    private Vacancy createVacancy(String title, String seniority, boolean remoteAllowed) {
        return new Vacancy(
                UUID.randomUUID(),
                UUID.randomUUID(), // Hiring manager
                UUID.randomUUID(), // Recruiter
                title,
                "Job description for " + title,
                "Requirements for " + title,
                remoteAllowed ? "Remote" : "Office",
                remoteAllowed,
                seniority,
                "OPEN",
                new BigDecimal("60000"),
                new BigDecimal("120000"),
                "USD",
                null);
    }
}
