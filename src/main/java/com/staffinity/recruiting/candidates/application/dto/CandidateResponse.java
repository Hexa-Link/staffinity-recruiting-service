package com.staffinity.recruiting.candidates.application.dto;

import java.util.UUID;
import java.time.Instant;

public class CandidateResponse {
    public UUID id;
    public UUID vacancyId;
    public String firstName;
    public String lastName;
    public String email;
    public String phoneNumber;
    public String resumeUrl;
    public String linkedinUrl;
    public String portfolioUrl;
    public String applicationStatus;
    public String source;
    public String rejectionReason;
    public Instant createdAt;
    public Instant updatedAt;
}

