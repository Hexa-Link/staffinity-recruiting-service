package com.staffinity.recruiting.candidates.domain.model;

import java.util.UUID;

public class Candidate {
    private UUID id;
    private UUID vacancyId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String resumeUrl;
    private String linkedinUrl;
    private String portfolioUrl;
    private String applicationStatus;
    private String source;
    private String rejectionReason;

    public Candidate(UUID id, UUID vacancyId, String firstName, String lastName, String email,
                     String phoneNumber, String resumeUrl, String linkedinUrl, String portfolioUrl,
                     String applicationStatus, String source, String rejectionReason) {
        this.id = id;
        this.vacancyId = vacancyId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.resumeUrl = resumeUrl;
        this.linkedinUrl = linkedinUrl;
        this.portfolioUrl = portfolioUrl;
        this.applicationStatus = applicationStatus;
        this.source = source;
        this.rejectionReason = rejectionReason;
    }

    // Getters and setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getVacancyId() { return vacancyId; }
    public void setVacancyId(UUID vacancyId) { this.vacancyId = vacancyId; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getResumeUrl() { return resumeUrl; }
    public void setResumeUrl(String resumeUrl) { this.resumeUrl = resumeUrl; }
    public String getLinkedinUrl() { return linkedinUrl; }
    public void setLinkedinUrl(String linkedinUrl) { this.linkedinUrl = linkedinUrl; }
    public String getPortfolioUrl() { return portfolioUrl; }
    public void setPortfolioUrl(String portfolioUrl) { this.portfolioUrl = portfolioUrl; }
    public String getApplicationStatus() { return applicationStatus; }
    public void setApplicationStatus(String applicationStatus) { this.applicationStatus = applicationStatus; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public String getRejectionReason() { return rejectionReason; }
    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }
}

