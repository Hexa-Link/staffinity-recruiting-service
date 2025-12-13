package com.staffinity.recruiting.internalSearch.application.dto;

import com.staffinity.recruiting.candidates.domain.model.Candidate;
import com.staffinity.recruiting.internalSearch.infrastructure.adapters.employees.Employee;
import com.staffinity.recruiting.vacancies.domain.model.Vacancy;

import java.util.List;

public class InternalSearchResponse {
    private Vacancy vacancy;
    private List<MatchItem> matches;

    public InternalSearchResponse(Vacancy vacancy, List<MatchItem> matches) {
        this.vacancy = vacancy;
        this.matches = matches;
    }

    public Vacancy getVacancy() { return vacancy; }
    public void setVacancy(Vacancy vacancy) { this.vacancy = vacancy; }
    public List<MatchItem> getMatches() { return matches; }
    public void setMatches(List<MatchItem> matches) { this.matches = matches; }

    public static class MatchItem {
        private Employee employee;
        private Candidate candidate;
        private int matchScore;
        private String source;

        public MatchItem(Employee employee, int matchScore) {
            this.employee = employee;
            this.matchScore = matchScore;
            this.source = "employees";
        }

        public MatchItem(Candidate candidate, int matchScore) {
            this.candidate = candidate;
            this.matchScore = matchScore;
            this.source = "candidates";
        }

        public Employee getEmployee() { return employee; }
        public void setEmployee(Employee employee) { this.employee = employee; }
        public Candidate getCandidate() { return candidate; }
        public void setCandidate(Candidate candidate) { this.candidate = candidate; }
        public int getMatchScore() { return matchScore; }
        public void setMatchScore(int matchScore) { this.matchScore = matchScore; }
        public String getSource() { return source; }
        public void setSource(String source) { this.source = source; }
    }

    public static class MatchItemDto {
        private com.staffinity.recruiting.vacancies.domain.model.Vacancy vacancy;
        private com.staffinity.recruiting.internalSearch.infrastructure.adapters.employees.Employee employee;
        private com.staffinity.recruiting.candidates.domain.model.Candidate candidate;
        private int matchScore;
        private String source;

        public MatchItemDto() {}

        public MatchItemDto(com.staffinity.recruiting.vacancies.domain.model.Vacancy vacancy,
                            com.staffinity.recruiting.internalSearch.infrastructure.adapters.employees.Employee employee,
                            int matchScore) {
            this.vacancy = vacancy;
            this.employee = employee;
            this.matchScore = matchScore;
            this.source = "employees";
        }

        public MatchItemDto(com.staffinity.recruiting.vacancies.domain.model.Vacancy vacancy,
                            com.staffinity.recruiting.candidates.domain.model.Candidate candidate,
                            int matchScore) {
            this.vacancy = vacancy;
            this.candidate = candidate;
            this.matchScore = matchScore;
            this.source = "candidates";
        }

        public com.staffinity.recruiting.vacancies.domain.model.Vacancy getVacancy() { return vacancy; }
        public void setVacancy(com.staffinity.recruiting.vacancies.domain.model.Vacancy vacancy) { this.vacancy = vacancy; }
        public com.staffinity.recruiting.internalSearch.infrastructure.adapters.employees.Employee getEmployee() { return employee; }
        public void setEmployee(com.staffinity.recruiting.internalSearch.infrastructure.adapters.employees.Employee employee) { this.employee = employee; }
        public com.staffinity.recruiting.candidates.domain.model.Candidate getCandidate() { return candidate; }
        public void setCandidate(com.staffinity.recruiting.candidates.domain.model.Candidate candidate) { this.candidate = candidate; }
        public int getMatchScore() { return matchScore; }
        public void setMatchScore(int matchScore) { this.matchScore = matchScore; }
        public String getSource() { return source; }
        public void setSource(String source) { this.source = source; }
    }
}
