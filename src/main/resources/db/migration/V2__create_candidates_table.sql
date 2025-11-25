CREATE TABLE candidates (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone_number VARCHAR(50),
    resume_url VARCHAR(500), -- URL to the resume file
    status VARCHAR(50) NOT NULL, -- e.g.: APPLIED, INTERVIEWING, HIRED, REJECTED
    vacancy_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_vacancy
        FOREIGN KEY (vacancy_id)
            REFERENCES vacancies (id)
            ON DELETE CASCADE -- If the vacancy is deleted, the candidates are deleted (optional)
);