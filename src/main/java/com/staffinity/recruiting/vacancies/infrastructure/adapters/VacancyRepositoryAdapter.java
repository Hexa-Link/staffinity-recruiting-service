package com.staffinity.recruiting.vacancies.infrastructure.adapters;

import com.staffinity.recruiting.vacancies.domain.model.Vacancy;
import com.staffinity.recruiting.vacancies.domain.ports.out.VacancyRepositoryPort;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class VacancyRepositoryAdapter implements VacancyRepositoryPort {
    // inject JpaVacancyRepository

    @Override
    public Vacancy save(Vacancy vacancy) {
        // convert and save
        return null;
    }

    @Override
    public List<Vacancy> findAll() {
        // convert and return
        return null;
    }
}
