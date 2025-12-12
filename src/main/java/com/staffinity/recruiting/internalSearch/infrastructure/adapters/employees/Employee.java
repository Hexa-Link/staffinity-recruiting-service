package com.staffinity.recruiting.internalSearch.infrastructure.adapters.employees;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public class Employee {
    private UUID id;
    private String code;
    private String name;
    private String email;
    private String passwordHash;
    private String phone;
    private LocalDate birthDate;
    private LocalDate hireDate;
    private UUID identificationTypeId;
    private String identificationNumber;
    private UUID managerId;
    private UUID headquartersId;
    private UUID genderId;
    private UUID statusId;
    private UUID accessLevelId;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private boolean isDeleted;

    // Constructors, getters, setters

    public Employee() {
    }

    public Employee(UUID id, String code, String name, String email, String passwordHash, String phone,
            LocalDate birthDate, LocalDate hireDate, UUID identificationTypeId, String identificationNumber,
            UUID managerId, UUID headquartersId, UUID genderId, UUID statusId, UUID accessLevelId,
            OffsetDateTime createdAt, OffsetDateTime updatedAt, boolean isDeleted) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.phone = phone;
        this.birthDate = birthDate;
        this.hireDate = hireDate;
        this.identificationTypeId = identificationTypeId;
        this.identificationNumber = identificationNumber;
        this.managerId = managerId;
        this.headquartersId = headquartersId;
        this.genderId = genderId;
        this.statusId = statusId;
        this.accessLevelId = accessLevelId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isDeleted = isDeleted;
    }

    // Getters and setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public UUID getIdentificationTypeId() {
        return identificationTypeId;
    }

    public void setIdentificationTypeId(UUID identificationTypeId) {
        this.identificationTypeId = identificationTypeId;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public UUID getManagerId() {
        return managerId;
    }

    public void setManagerId(UUID managerId) {
        this.managerId = managerId;
    }

    public UUID getHeadquartersId() {
        return headquartersId;
    }

    public void setHeadquartersId(UUID headquartersId) {
        this.headquartersId = headquartersId;
    }

    public UUID getGenderId() {
        return genderId;
    }

    public void setGenderId(UUID genderId) {
        this.genderId = genderId;
    }

    public UUID getStatusId() {
        return statusId;
    }

    public void setStatusId(UUID statusId) {
        this.statusId = statusId;
    }

    public UUID getAccessLevelId() {
        return accessLevelId;
    }

    public void setAccessLevelId(UUID accessLevelId) {
        this.accessLevelId = accessLevelId;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        this.isDeleted = deleted;
    }

    // --- Innovation: Skill Inference ---
    // Since we don't have a structured Skills DB yet, we "infer" them from the
    // employee's profile
    // This simulates an AI analyzing the employee's history.
    public java.util.Set<String> getInferredSkills() {
        java.util.Set<String> skills = new java.util.HashSet<>();

        if (name == null)
            return skills;
        String lowerName = name.toLowerCase(); // Using name/title proxy

        // Rule-based inference (The "Neural" approximation)
        if (lowerName.contains("developer") || lowerName.contains("engineer") || lowerName.contains("dev")) {
            skills.add("git");
            skills.add("agile");
            if (lowerName.contains("java") || lowerName.contains("backend")) {
                skills.add("java");
                skills.add("spring");
                skills.add("sql");
                skills.add("postgres");
            }
            if (lowerName.contains("net") || lowerName.contains("c#") || lowerName.contains("sharp")) {
                skills.add("c#");
                skills.add(".net");
                skills.add("sql");
                skills.add("azure");
            }
            if (lowerName.contains("front") || lowerName.contains("react") || lowerName.contains("js")) {
                skills.add("javascript");
                skills.add("typescript");
                skills.add("react");
                skills.add("html");
                skills.add("css");
            }
        }

        if (lowerName.contains("manager") || lowerName.contains("lead") || lowerName.contains("jefe")) {
            skills.add("leadership");
            skills.add("management");
            skills.add("communication");
        }

        if (lowerName.contains("analyst") || lowerName.contains("data")) {
            skills.add("sql");
            skills.add("python");
            skills.add("excel");
        }

        return skills;
    }
}
