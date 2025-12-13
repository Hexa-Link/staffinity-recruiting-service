CREATE TABLE vacancies (
    id UUID PRIMARY KEY,
    hiring_manager_id UUID,
    recruiter_id UUID,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    requirements TEXT,
    location VARCHAR(255),
    remote_allowed BOOLEAN DEFAULT FALSE,
    seniority VARCHAR(50),
    status VARCHAR(50) NOT NULL,
    salary_min DECIMAL(19, 2),
    salary_max DECIMAL(19, 2),
    currency VARCHAR(10),
    closed_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);
