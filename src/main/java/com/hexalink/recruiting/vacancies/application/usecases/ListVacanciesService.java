package com.hexalink.recruiting.vacancies.application.usecases;

import com.hexalink.recruiting.vacancies.domain.ports.in.ListVacanciesUseCase;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ListVacanciesService implements ListVacanciesUseCase {
    @Override
    public List<?> listVacancies() {
        // implementation
        return null;
    }
}
