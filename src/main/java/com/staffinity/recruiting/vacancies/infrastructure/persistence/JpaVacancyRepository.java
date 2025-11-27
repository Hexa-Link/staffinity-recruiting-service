package com.staffinity.recruiting.vacancies.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaVacancyRepository extends JpaRepository<VacancyEntity, UUID> {
}
