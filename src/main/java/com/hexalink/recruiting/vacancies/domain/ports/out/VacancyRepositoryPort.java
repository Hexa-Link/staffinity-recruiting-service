package com.hexalink.recruiting.vacancies.domain.ports.out;

import com.hexalink.recruiting.vacancies.domain.model.Vacancy;
import java.util.List;

public interface VacancyRepositoryPort {
    Vacancy save(Vacancy vacancy);
    List<Vacancy> findAll();
}
