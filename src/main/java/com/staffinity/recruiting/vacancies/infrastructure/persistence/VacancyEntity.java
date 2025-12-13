package com.staffinity.recruiting.vacancies.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "vacancies")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacancyEntity implements Persistable<UUID> {

    @Id
    private UUID id;

    @Column(name = "hiring_manager_id")
    private UUID hiringManagerId;

    @Column(name = "recruiter_id")
    private UUID recruiterId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String requirements;

    @Column
    private String location;

    @Column(name = "remote_allowed")
    private boolean remoteAllowed;

    @Column
    private String seniority;

    @Column(nullable = false)
    private String status;

    @Column(name = "salary_min")
    private BigDecimal salaryMin;

    @Column(name = "salary_max")
    private BigDecimal salaryMax;

    @Column
    private String currency;

    @Column(name = "closed_at")
    private Instant closedAt;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Override
    public UUID getId() {
        return id;
    }

    // Treat entity as new until createdAt is set by Hibernate
    @Override
    public boolean isNew() {
        return createdAt == null;
    }
}