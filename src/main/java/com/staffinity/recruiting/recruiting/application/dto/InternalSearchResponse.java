package com.staffinity.recruiting.recruiting.application.dto;

import com.staffinity.recruiting.candidates.infrastructure.adapters.Employee;
import com.staffinity.recruiting.vacancies.domain.model.Vacancy;

public class InternalSearchResponse {
    private Vacancy vacancy;
    private Employee employee;
    private int matchScore;

    public InternalSearchResponse(Vacancy vacancy, Employee employee) {
        this.vacancy = vacancy;
        this.employee = employee;
        this.matchScore = 0;
    }

    public InternalSearchResponse(Vacancy vacancy, Employee employee, int matchScore) {
        this.vacancy = vacancy;
        this.employee = employee;
        this.matchScore = matchScore;
    }

    public Vacancy getVacancy() { return vacancy; }
    public void setVacancy(Vacancy vacancy) { this.vacancy = vacancy; }
    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }
    public int getMatchScore() { return matchScore; }
    public void setMatchScore(int matchScore) { this.matchScore = matchScore; }
}
