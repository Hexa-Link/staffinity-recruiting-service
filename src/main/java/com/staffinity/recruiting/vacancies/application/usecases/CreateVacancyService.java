package com.staffinity.recruiting.vacancies.application.usecases;

import com.staffinity.recruiting.vacancies.domain.ports.in.CreateVacancyUseCase;
import com.staffinity.recruiting.vacancies.domain.ports.out.VacancyRepositoryPort;
import com.staffinity.recruiting.vacancies.domain.model.Vacancy;
import com.staffinity.recruiting.vacancies.application.dto.CreateVacancyRequest;
import com.staffinity.recruiting.vacancies.infrastructure.web.mapper.VacancyMapper;
import org.springframework.stereotype.Service;

@Service
public class CreateVacancyService implements CreateVacancyUseCase {
    private final VacancyRepositoryPort vacancyRepository;
    private final VacancyMapper vacancyMapper;

    public CreateVacancyService(VacancyRepositoryPort vacancyRepository, VacancyMapper vacancyMapper) {
        this.vacancyRepository = vacancyRepository;
        this.vacancyMapper = vacancyMapper;
    }

    @Override
    public Vacancy createVacancy(CreateVacancyRequest request) {
        // Convert request to domain model
        Vacancy vacancy = vacancyMapper.fromRequest(request);

        // Save through the repository port
        return vacancyRepository.save(vacancy);
    }
}

