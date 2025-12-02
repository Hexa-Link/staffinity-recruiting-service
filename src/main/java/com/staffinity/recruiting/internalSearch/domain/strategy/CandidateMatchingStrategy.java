package com.staffinity.recruiting.internalSearch.domain.strategy;

import com.staffinity.recruiting.candidates.domain.model.Candidate;
import com.staffinity.recruiting.vacancies.domain.model.Vacancy;
import org.springframework.stereotype.Component;

/**
 * Candidate matching strategy that calculates candidate-vacancy match scores based on:
 * - Application status (40 points)
 * - Candidate source quality (30 points)
 * - Profile completeness (30 points)
 */
@Component
public class CandidateMatchingStrategy {
    private static final int STATUS_WEIGHT = 40;
    private static final int SOURCE_WEIGHT = 30;
    private static final int PROFILE_WEIGHT = 30;
    private static final int MIN_MATCHING_SCORE = 50;

    public int calculateMatchScore(Vacancy vacancy, Candidate candidate) {
        if (candidate == null || candidate.getVacancyId() == null || vacancy == null || vacancy.getId() == null) {
            return 0;
        }
        
        // Only score candidates linked to this vacancy
        if (!vacancy.getId().equals(candidate.getVacancyId())) {
            return 0;
        }

        int score = 0;

        // Application status scoring (40 points)
        score += scoreByApplicationStatus(candidate);

        // Source quality scoring (30 points)
        score += scoreBySource(candidate);

        // Profile completeness scoring (30 points)
        score += scoreByProfileCompleteness(candidate);

        return score;
    }

    public int getMinMatchingScore() {
        return MIN_MATCHING_SCORE;
    }

    /**
     * Score based on application status progression.
     * More advanced stages indicate higher engagement and suitability.
     */
    private int scoreByApplicationStatus(Candidate candidate) {
        if (candidate.getApplicationStatus() == null) {
            return 0;
        }

        return switch (candidate.getApplicationStatus()) {
            case "INTERVIEWING" -> STATUS_WEIGHT;                 // 40 - highest engagement
            case "UNDER_REVIEW" -> (int) (STATUS_WEIGHT * 0.85);  // 34
            case "SUBMITTED" -> (int) (STATUS_WEIGHT * 0.7);      // 28
            case "HIRED" -> STATUS_WEIGHT;                        // 40 - best outcome
            case "REJECTED" -> (int) (STATUS_WEIGHT * 0.3);       // 12 - low but not zero
            default -> (int) (STATUS_WEIGHT * 0.5);               // 20 - unknown status
        };
    }

    /**
     * Score based on candidate source quality.
     * Some sources tend to yield higher quality candidates.
     */
    private int scoreBySource(Candidate candidate) {
        if (candidate.getSource() == null) {
            return 0;
        }

        return switch (candidate.getSource()) {
            case "REFERRAL" -> SOURCE_WEIGHT;                         // 30 - highest quality
            case "LINKEDIN" -> (int) (SOURCE_WEIGHT * 0.9);           // 27
            case "UNIVERSITY_PARTNERSHIP" -> (int) (SOURCE_WEIGHT * 0.85); // 25
            case "COMPANY_WEBSITE" -> (int) (SOURCE_WEIGHT * 0.75);   // 22
            case "JOB_BOARD" -> (int) (SOURCE_WEIGHT * 0.6);          // 18
            case "RECRUITER" -> (int) (SOURCE_WEIGHT * 0.8);          // 24
            default -> (int) (SOURCE_WEIGHT * 0.5);                   // 15 - other
        };
    }

    /**
     * Score based on profile completeness.
     * More complete profiles indicate serious candidates.
     */
    private int scoreByProfileCompleteness(Candidate candidate) {
        int completenessScore = 0;
        int maxFields = 3; // LinkedIn, Resume, Portfolio

        if (candidate.getLinkedinUrl() != null && !candidate.getLinkedinUrl().isBlank()) {
            completenessScore++;
        }
        if (candidate.getResumeUrl() != null && !candidate.getResumeUrl().isBlank()) {
            completenessScore++;
        }
        if (candidate.getPortfolioUrl() != null && !candidate.getPortfolioUrl().isBlank()) {
            completenessScore++;
        }

        // Scale to PROFILE_WEIGHT
        return (int) ((double) completenessScore / maxFields * PROFILE_WEIGHT);
    }
}
