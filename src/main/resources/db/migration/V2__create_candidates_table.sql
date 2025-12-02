CREATE TABLE candidates (
    id UUID PRIMARY KEY,
    vacancy_id UUID NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255),
    resume_url VARCHAR(500),
    linkedin_url VARCHAR(500),
    portfolio_url VARCHAR(500),
    application_status VARCHAR(50) NOT NULL,
    source VARCHAR(255),
    rejection_reason TEXT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_vacancy
        FOREIGN KEY (vacancy_id)
            REFERENCES vacancies (id)
            ON DELETE CASCADE
);