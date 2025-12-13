package com.staffinity.recruiting.vacancies.domain.ports.in;

import com.staffinity.recruiting.vacancies.domain.model.Vacancy;
import java.util.List;

public interface ListVacanciesUseCase {
    List<Vacancy> listVacancies();
}

