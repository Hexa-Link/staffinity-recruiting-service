package com.staffinity.recruiting.vacancies.domain.model;

import java.util.UUID;
import java.math.BigDecimal;

public class Vacancy {
    private UUID id;
    private UUID hiringManagerId;
    private UUID recruiterId;
    private String title;
    private String description;
    private String requirements;
    private String location;
    private boolean remoteAllowed;
    private String seniority;
    private String status;
    private BigDecimal salaryMin;
    private BigDecimal salaryMax;
    private String currency;
    private java.time.Instant closedAt;

    public Vacancy(UUID id, UUID hiringManagerId, UUID recruiterId, String title, String description, String requirements, String location, boolean remoteAllowed, String seniority, String status, BigDecimal salaryMin, BigDecimal salaryMax, String currency, java.time.Instant closedAt) {
        this.id = id;
        this.hiringManagerId = hiringManagerId;
        this.recruiterId = recruiterId;
        this.title = title;
        this.description = description;
        this.requirements = requirements;
        this.location = location;
        this.remoteAllowed = remoteAllowed;
        this.seniority = seniority;
        this.status = status;
        this.salaryMin = salaryMin;
        this.salaryMax = salaryMax;
        this.currency = currency;
        this.closedAt = closedAt;
    }

    // Getters and setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getHiringManagerId() { return hiringManagerId; }
    public void setHiringManagerId(UUID hiringManagerId) { this.hiringManagerId = hiringManagerId; }
    public UUID getRecruiterId() { return recruiterId; }
    public void setRecruiterId(UUID recruiterId) { this.recruiterId = recruiterId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getRequirements() { return requirements; }
    public void setRequirements(String requirements) { this.requirements = requirements; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public boolean isRemoteAllowed() { return remoteAllowed; }
    public void setRemoteAllowed(boolean remoteAllowed) { this.remoteAllowed = remoteAllowed; }
    public String getSeniority() { return seniority; }
    public void setSeniority(String seniority) { this.seniority = seniority; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public BigDecimal getSalaryMin() { return salaryMin; }
    public void setSalaryMin(BigDecimal salaryMin) { this.salaryMin = salaryMin; }
    public BigDecimal getSalaryMax() { return salaryMax; }
    public void setSalaryMax(BigDecimal salaryMax) { this.salaryMax = salaryMax; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public java.time.Instant getClosedAt() { return closedAt; }
    public void setClosedAt(java.time.Instant closedAt) { this.closedAt = closedAt; }

    @Override
    public String toString() {
        return "Vacancy{" +
                "id=" + id +
                ", hiringManagerId=" + hiringManagerId +
                ", recruiterId=" + recruiterId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", requirements='" + requirements + '\'' +
                ", location='" + location + '\'' +
                ", remoteAllowed=" + remoteAllowed +
                ", seniority='" + seniority + '\'' +
                ", status='" + status + '\'' +
                ", salaryMin=" + salaryMin +
                ", salaryMax=" + salaryMax +
                ", currency='" + currency + '\'' +
                ", closedAt=" + closedAt +
                '}';
    }
}
