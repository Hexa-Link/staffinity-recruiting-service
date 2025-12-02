CREATE TABLE vacancies (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    hiring_manager_id UUID,
    recruiter_id UUID,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    requirements TEXT,
    location VARCHAR(255),
    remote_allowed BOOLEAN DEFAULT FALSE,
    seniority VARCHAR(50),
    status VARCHAR(50) NOT NULL, -- Ej: OPEN, CLOSED, DRAFT
    salary_min DECIMAL(10, 2),
    salary_max DECIMAL(10, 2),
    currency VARCHAR(3) DEFAULT 'USD',
    closed_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
