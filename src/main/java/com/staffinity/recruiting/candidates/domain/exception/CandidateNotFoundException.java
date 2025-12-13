package com.staffinity.recruiting.candidates.domain.exception;

import com.staffinity.recruiting.common.exception.NotFoundException;

public class CandidateNotFoundException extends NotFoundException {
    public CandidateNotFoundException(String message) {
        super(message);
    }
}

