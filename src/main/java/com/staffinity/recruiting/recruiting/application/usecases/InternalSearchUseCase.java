package com.staffinity.recruiting.recruiting.application.usecases;

import com.staffinity.recruiting.recruiting.application.dto.InternalSearchResponse;

import java.util.List;

public interface InternalSearchUseCase {
    List<InternalSearchResponse> searchInternalEmployees();
}
