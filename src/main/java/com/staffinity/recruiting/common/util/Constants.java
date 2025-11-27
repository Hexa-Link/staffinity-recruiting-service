package com.staffinity.recruiting.common.util;

public class Constants {
    // Status constants
    public static final String STATUS_ACTIVE = "ACTIVE";
    public static final String STATUS_INACTIVE = "INACTIVE";
    public static final String STATUS_PENDING = "PENDING";

    // Vacancy status
    public static final String VACANCY_STATUS_OPEN = "OPEN";
    public static final String VACANCY_STATUS_CLOSED = "CLOSED";
    public static final String VACANCY_STATUS_ON_HOLD = "ON_HOLD";

    // Candidate status
    public static final String CANDIDATE_STATUS_NEW = "NEW";
    public static final String CANDIDATE_STATUS_IN_REVIEW = "IN_REVIEW";
    public static final String CANDIDATE_STATUS_INTERVIEWED = "INTERVIEWED";
    public static final String CANDIDATE_STATUS_HIRED = "HIRED";
    public static final String CANDIDATE_STATUS_REJECTED = "REJECTED";

    // Pagination
    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final int MAX_PAGE_SIZE = 100;

    private Constants() {
        // Utility class
    }
}

