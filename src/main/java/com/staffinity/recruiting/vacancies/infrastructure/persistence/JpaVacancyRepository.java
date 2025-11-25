package com.staffinity.recruiting.vacancies.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaVacancyRepository extends JpaRepository<VacancyEntity, Long> {
}
