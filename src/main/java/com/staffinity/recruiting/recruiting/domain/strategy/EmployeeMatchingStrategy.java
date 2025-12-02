package com.staffinity.recruiting.recruiting.domain.strategy;

import com.staffinity.recruiting.candidates.infrastructure.adapters.Employee;
import com.staffinity.recruiting.vacancies.domain.model.Vacancy;

/**
 * Strategy interface for calculating how well an employee matches a vacancy.
 * Implementations define different matching algorithms.
 */
public interface EmployeeMatchingStrategy {

    /**
     * Calculates a match score between an employee and a vacancy.
     *
     * @param vacancy the vacancy to match against
     * @param employee the employee to evaluate
     * @return a score from 0 to 100, where higher scores indicate better matches
     */
    int calculateMatchScore(Vacancy vacancy, Employee employee);

    /**
     * Returns the minimum score threshold for considering a match valid.
     *
     * @return minimum matching score (0-100)
     */
    int getMinMatchingScore();
}

