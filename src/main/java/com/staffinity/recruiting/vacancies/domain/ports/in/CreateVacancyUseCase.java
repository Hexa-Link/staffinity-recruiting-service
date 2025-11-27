package com.staffinity.recruiting.vacancies.domain.ports.in;

import com.staffinity.recruiting.vacancies.application.dto.CreateVacancyRequest;
import com.staffinity.recruiting.vacancies.domain.model.Vacancy;

public interface CreateVacancyUseCase {
    Vacancy createVacancy(CreateVacancyRequest request);
}
