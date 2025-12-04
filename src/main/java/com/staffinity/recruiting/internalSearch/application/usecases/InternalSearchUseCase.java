package com.staffinity.recruiting.internalSearch.application.usecases;

import com.staffinity.recruiting.internalSearch.application.dto.InternalSearchResponse;

import java.util.List;
import java.util.UUID;

public interface InternalSearchUseCase {
    InternalSearchResponse searchInternalEmployees(UUID vacancyId);
    List<InternalSearchResponse.MatchItemDto> searchInternalEmployees();
}
