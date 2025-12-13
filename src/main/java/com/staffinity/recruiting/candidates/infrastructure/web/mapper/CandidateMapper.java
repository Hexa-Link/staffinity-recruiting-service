package com.staffinity.recruiting.candidates.infrastructure.web.mapper;

import com.staffinity.recruiting.candidates.application.dto.CandidateRequest;
import com.staffinity.recruiting.candidates.application.dto.CandidateResponse;
import com.staffinity.recruiting.candidates.domain.model.Candidate;
import com.staffinity.recruiting.candidates.infrastructure.persistence.CandidateEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CandidateMapper {

    Candidate toDomain(CandidateEntity entity);

    CandidateEntity toEntity(Candidate domain);

    @Mapping(target = "id", ignore = true)
    Candidate fromRequest(CandidateRequest request);

    CandidateResponse toResponse(Candidate domain);

    List<CandidateResponse> toResponseList(List<Candidate> candidates);

    List<Candidate> toDomainList(List<CandidateEntity> candidates);
}

