package com.staffinity.recruiting.vacancies.infrastructure.web.mapper;

import com.staffinity.recruiting.vacancies.application.dto.CreateVacancyRequest;
import com.staffinity.recruiting.vacancies.application.dto.VacancyResponse;
import com.staffinity.recruiting.vacancies.domain.model.Vacancy;
import com.staffinity.recruiting.vacancies.infrastructure.persistence.VacancyEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VacancyMapper {

    Vacancy toDomain(VacancyEntity entity);

    @Mapping(target = "id", expression = "java(domain.getId() != null ? domain.getId() : java.util.UUID.randomUUID())")
    VacancyEntity toEntity(Vacancy domain);

    @Mapping(target = "id", ignore = true)
    Vacancy fromRequest(CreateVacancyRequest request);

    VacancyResponse toResponse(Vacancy domain);

    List<VacancyResponse> toResponseList(List<Vacancy> vacancies);
}