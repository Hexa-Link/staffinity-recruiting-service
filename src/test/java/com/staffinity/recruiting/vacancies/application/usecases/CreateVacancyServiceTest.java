package com.staffinity.recruiting.vacancies.application.usecases;

import com.staffinity.recruiting.vacancies.application.dto.CreateVacancyRequest;
import com.staffinity.recruiting.vacancies.domain.model.Vacancy;
import com.staffinity.recruiting.vacancies.domain.ports.out.VacancyRepositoryPort;
import com.staffinity.recruiting.vacancies.infrastructure.web.mapper.VacancyMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateVacancyServiceTest {

    @Mock
    private VacancyRepositoryPort vacancyRepository;

    @Mock
    private VacancyMapper vacancyMapper;

    @InjectMocks
    private CreateVacancyService createVacancyService;

    private CreateVacancyRequest request;
    private Vacancy vacancy;

    @BeforeEach
    void setUp() {
        request = new CreateVacancyRequest();
        request.hiringManagerId = UUID.randomUUID();
        request.recruiterId = UUID.randomUUID();
        request.title = "Senior Java Developer";
        request.description = "We are looking for an experienced Java developer";
        request.requirements = "5+ years of Java experience";
        request.location = "Remote";
        request.remoteAllowed = true;
        request.seniority = "Senior";
        request.status = "OPEN";
        request.salaryMin = new BigDecimal("80000.00");
        request.salaryMax = new BigDecimal("120000.00");
        request.currency = "USD";

        vacancy = new Vacancy(
                UUID.randomUUID(),
                request.hiringManagerId,
                request.recruiterId,
                request.title,
                request.description,
                request.requirements,
                request.location,
                request.remoteAllowed,
                request.seniority,
                request.status,
                request.salaryMin,
                request.salaryMax,
                request.currency,
                null
        );
    }

    @Test
    void shouldCreateVacancySuccessfully() {
        // Given
        when(vacancyMapper.fromRequest(request)).thenReturn(vacancy);
        when(vacancyRepository.save(any(Vacancy.class))).thenReturn(vacancy);

        // When
        Vacancy result = createVacancyService.createVacancy(request);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Senior Java Developer");
        assertThat(result.getStatus()).isEqualTo("OPEN");

        verify(vacancyMapper).fromRequest(request);
        verify(vacancyRepository).save(any(Vacancy.class));
    }

    @Test
    void shouldCallRepositorySaveWhenCreatingVacancy() {
        // Given
        when(vacancyMapper.fromRequest(request)).thenReturn(vacancy);
        when(vacancyRepository.save(vacancy)).thenReturn(vacancy);

        // When
        createVacancyService.createVacancy(request);

        // Then
        verify(vacancyRepository, times(1)).save(vacancy);
    }
}

