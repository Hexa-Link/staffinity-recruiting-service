package com.staffinity.recruiting.vacancies.infrastructure.adapters;

import com.staffinity.recruiting.vacancies.domain.model.Vacancy;
import com.staffinity.recruiting.vacancies.domain.ports.out.VacancyRepositoryPort;
import com.staffinity.recruiting.vacancies.infrastructure.persistence.JpaVacancyRepository;
import com.staffinity.recruiting.vacancies.infrastructure.persistence.VacancyEntity;
import com.staffinity.recruiting.vacancies.infrastructure.web.mapper.VacancyMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VacancyRepositoryAdapter implements VacancyRepositoryPort {

    private final JpaVacancyRepository jpaVacancyRepository;
    private final VacancyMapper vacancyMapper;

    public VacancyRepositoryAdapter(JpaVacancyRepository jpaVacancyRepository, VacancyMapper vacancyMapper) {
        this.jpaVacancyRepository = jpaVacancyRepository;
        this.vacancyMapper = vacancyMapper;
    }

    @Override
    public Vacancy save(Vacancy vacancy) {
        VacancyEntity entity = vacancyMapper.toEntity(vacancy);
        VacancyEntity savedEntity = jpaVacancyRepository.save(entity);
        return vacancyMapper.toDomain(savedEntity);
    }

    @Override
    public List<Vacancy> findAll() {
        return jpaVacancyRepository.findAll()
                .stream()
                .map(vacancyMapper::toDomain)
                .collect(Collectors.toList());
    }
}
