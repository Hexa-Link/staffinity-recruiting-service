package com.staffinity.recruiting.internalSearch.domain.strategy;

import com.staffinity.recruiting.internalSearch.infrastructure.adapters.employees.Employee;
import com.staffinity.recruiting.vacancies.domain.model.Vacancy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public class DebugStrategyTest {
    public static void main(String[] args) {
        com.staffinity.recruiting.internalSearch.domain.service.KeywordExtractionService keywordService = new com.staffinity.recruiting.internalSearch.domain.service.KeywordExtractionService();
        SkillBasedMatchingStrategy strategy = new SkillBasedMatchingStrategy(keywordService);

        // Create mid-level employee with 3 years experience
        Employee midEmployee = new Employee(
                UUID.randomUUID(),
                "EMP123",
                "Mid Developer Jane",
                "mid.developer.jane@company.com",
                "hashedpassword",
                "+1234567890",
                LocalDate.of(1985, 5, 15), // Birth date
                LocalDate.of(2020, 6, 1), // Hire date - 3-4 years experience
                UUID.randomUUID(), // Identification type
                "123456789",
                null,
                UUID.randomUUID(), // Headquarters
                UUID.randomUUID(), // Gender
                UUID.randomUUID(), // Status
                UUID.randomUUID(), // Access level (not null means has access)
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                false);

        // Mid-level vacancy
        Vacancy midVacancy = new Vacancy(
                UUID.randomUUID(),
                UUID.randomUUID(), // Hiring manager
                UUID.randomUUID(), // Recruiter
                "Mid-Level Backend Developer",
                "Job description for Mid-Level Backend Developer",
                "Requirements for Mid-Level Backend Developer",
                "Remote",
                true, // Remote allowed
                "Mid",
                "OPEN",
                new BigDecimal("60000"),
                new BigDecimal("120000"),
                "USD",
                null);

        int score = strategy.calculateMatchScore(midVacancy, midEmployee);
        System.out.println("Score: " + score);
        System.out.println("Expected: 100");
        System.out.println(
                "Seniority match: " + (midVacancy.getSeniority() != null && midEmployee.getAccessLevelId() != null));
        System.out.println("Location match (remote): " + midVacancy.isRemoteAllowed());
        System.out.println(
                "Experience years: " + java.time.Period.between(midEmployee.getHireDate(), LocalDate.now()).getYears());
    }
}
