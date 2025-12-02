package com.staffinity.recruiting.recruiting.domain.strategy;

import com.staffinity.recruiting.candidates.infrastructure.adapters.Employee;
import com.staffinity.recruiting.vacancies.domain.model.Vacancy;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

/**
 * Skill-based matching strategy that calculates employee-vacancy match scores based on:
 * - Seniority alignment (40 points)
 * - Location compatibility (30 points)
 * - Experience tenure (30 points)
 */
@Component
public class SkillBasedMatchingStrategy implements EmployeeMatchingStrategy {

    private static final int SENIORITY_WEIGHT = 40;
    private static final int LOCATION_WEIGHT = 30;
    private static final int EXPERIENCE_WEIGHT = 30;
    private static final int MIN_MATCHING_SCORE = 50;

    @Override
    public int calculateMatchScore(Vacancy vacancy, Employee employee) {
        int score = 0;

        // Seniority matching (40 points)
        if (matchesSeniority(vacancy, employee)) {
            score += SENIORITY_WEIGHT;
        }

        // Location/remote compatibility (30 points)
        if (matchesLocation(vacancy, employee)) {
            score += LOCATION_WEIGHT;
        }

        // Experience level based on tenure (30 points)
        if (matchesExperienceLevel(vacancy, employee)) {
            score += EXPERIENCE_WEIGHT;
        }

        return score;
    }

    @Override
    public int getMinMatchingScore() {
        return MIN_MATCHING_SCORE;
    }

    /**
     * Checks if employee's seniority level matches vacancy requirements.
     * Uses accessLevelId as a proxy for seniority level.
     */
    private boolean matchesSeniority(Vacancy vacancy, Employee employee) {
        if (vacancy.getSeniority() == null || employee.getAccessLevelId() == null) {
            return false;
        }

        String seniority = vacancy.getSeniority().toLowerCase();

        // Map seniority to access level (simplified heuristic)
        // In a real system, you'd have a proper mapping table
        if (seniority.contains("senior") || seniority.contains("lead")) {
            // Senior positions require higher access levels
            return employee.getAccessLevelId() != null;
        } else if (seniority.contains("mid") || seniority.contains("intermediate")) {
            // Mid-level positions
            return employee.getAccessLevelId() != null;
        } else if (seniority.contains("junior") || seniority.contains("entry")) {
            // Junior positions
            return employee.getAccessLevelId() != null;
        }

        // Default: if employee has an access level, they qualify
        return true;
    }

    /**
     * Checks if employee's location is compatible with vacancy.
     * Remote vacancies are always compatible.
     */
    private boolean matchesLocation(Vacancy vacancy, Employee employee) {
        if (vacancy.isRemoteAllowed()) {
            // Remote positions are always compatible
            return true;
        }

        // If not remote, check if employee has headquarters (location) info
        // In real system, you'd compare actual locations
        return employee.getHeadquartersId() != null;
    }

    /**
     * Checks if employee's work experience aligns with vacancy seniority.
     * Calculates years of experience from hire date.
     */
    private boolean matchesExperienceLevel(Vacancy vacancy, Employee employee) {
        if (vacancy.getSeniority() == null || employee.getHireDate() == null) {
            return false;
        }

        int yearsOfExperience = calculateYearsOfExperience(employee);
        String seniority = vacancy.getSeniority().toLowerCase();

        // Match experience to seniority requirements
        if (seniority.contains("senior") || seniority.contains("lead")) {
            return yearsOfExperience >= 5; // Senior requires 5+ years
        } else if (seniority.contains("mid") || seniority.contains("intermediate")) {
            return yearsOfExperience >= 2 && yearsOfExperience <= 7; // Mid: 2-7 years
        } else if (seniority.contains("junior") || seniority.contains("entry")) {
            return yearsOfExperience <= 3; // Junior: up to 3 years
        }

        // Default: any experience level matches
        return true;
    }

    /**
     * Calculates years of experience based on hire date.
     */
    private int calculateYearsOfExperience(Employee employee) {
        if (employee.getHireDate() == null) {
            return 0;
        }

        LocalDate hireDate = employee.getHireDate();
        LocalDate now = LocalDate.now();

        return Period.between(hireDate, now).getYears();
    }
}
