package com.staffinity.recruiting.vacancies.application.dto;

import java.util.UUID;
import java.math.BigDecimal;
import java.time.Instant;

public class CreateVacancyRequest {
    public UUID hiringManagerId;
    public UUID recruiterId;
    public String title;
    public String description;
    public String requirements;
    public String location;
    public boolean remoteAllowed;
    public String seniority;
    public String status;
    public BigDecimal salaryMin;
    public BigDecimal salaryMax;
    public String currency;
    public Instant closedAt;
}

