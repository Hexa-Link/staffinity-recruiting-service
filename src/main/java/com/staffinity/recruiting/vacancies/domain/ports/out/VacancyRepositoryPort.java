package com.staffinity.recruiting.vacancies.domain.ports.out;

import com.staffinity.recruiting.vacancies.domain.model.Vacancy;
import java.util.List;

public interface VacancyRepositoryPort {
    Vacancy save(Vacancy vacancy);
    List<Vacancy> findAll();
}

