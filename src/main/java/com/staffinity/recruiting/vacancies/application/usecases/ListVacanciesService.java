package com.staffinity.recruiting.vacancies.application.usecases;

import com.staffinity.recruiting.vacancies.domain.ports.in.ListVacanciesUseCase;
import com.staffinity.recruiting.vacancies.domain.ports.out.VacancyRepositoryPort;
import com.staffinity.recruiting.vacancies.domain.model.Vacancy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListVacanciesService implements ListVacanciesUseCase {
    private final VacancyRepositoryPort vacancyRepository;

    public ListVacanciesService(VacancyRepositoryPort vacancyRepository) {
        this.vacancyRepository = vacancyRepository;
    }

    @Override
    public List<Vacancy> listVacancies() {
        return vacancyRepository.findAll();
    }
}

